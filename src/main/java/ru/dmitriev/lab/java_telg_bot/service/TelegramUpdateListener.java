package ru.dmitriev.lab.java_telg_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dmitriev.lab.java_telg_bot.model.Smehs;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.dmitriev.lab.java_telg_bot.model.SmehSaveDTO;

import java.util.List;

@Log4j2
@Component
public class TelegramUpdateListener implements UpdatesListener {

    private final SmehsService smehsService;
    private final TelegramBot telegramBot;

    public TelegramUpdateListener(SmehsService smehsService, TelegramBot telegramBot) {
        this.smehsService = smehsService;
        this.telegramBot = telegramBot;
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            log.info("Получен апдейт: {}", update);
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    telegramBot.execute(new SendMessage(chatId,
                            "Доступные команды:\n/random_smeh - случайный анекдот\n/all_smehs - все анекдоты\n /smehpage - страница анекдотов"));
                }

                if (update.message().text().equals("/random_smeh")) {
                    List<Smehs> smehs = smehsService.getAllSmehs(null);

                    if (!smehs.isEmpty()) {
                        Smehs smeh = smehs.get((int) (Math.random() * smehs.size()));
                        String text = '"' + smeh.getTitle() + '"' +  "\n" + smeh.getContent();
                        telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                    }else {telegramBot.execute(new SendMessage(update.message().chat().id(), "Мемов нет"));}
                }
                if (update.message().text().equals("/all_smehs")) {
                    List<Smehs> smehs = smehsService.getAllSmehs(null);
                    String text = "";
                    if (!smehs.isEmpty()) {
                        for (int i = 0; i < smehs.size(); i++) {
                            text = '"' + smehs.get(i).getTitle() + '"'  + "\n" + smehs.get(i).getContent()+ "\n\n";
                            telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                        }
                    }
                }
                if (messageText.startsWith("/smehpage")) {
                    String[] parts = messageText.split(" ");
                    int pageNumber = 0;
                    int pageSize = 5;

                    if (parts.length >= 2) {
                        try {
                            pageNumber = Integer.parseInt(parts[1]) - 1;
                            if (pageNumber < 0) pageNumber = 0;
                        } catch (NumberFormatException e) {
                            telegramBot.execute(new SendMessage(chatId, "Неверный номер страницы. Используйте: /smehpage 1"));
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }
                    }

                    Pageable pageable = PageRequest.of(pageNumber, pageSize);
                    Page<Smehs> smehPage = smehsService.getSmehsPage(null, pageable);

                    if (smehPage.hasContent()) {
                        StringBuilder builder = new StringBuilder("Анекдоты (страница " + (pageNumber + 1) + " из " + smehPage.getTotalPages() + "):\n\n");
                        for (Smehs smeh : smehPage.getContent()) {
                            builder.append("• ").append(smeh.getTitle()).append("\n")
                                    .append(smeh.getContent()).append("\n\n\n\n\n\n");
                        }
                        telegramBot.execute(new SendMessage(chatId, builder.toString()));
                    } else {
                        telegramBot.execute(new SendMessage(chatId, "Анекдотов не найдено на этой странице"));
                    }
                    if (messageText.equals("/top_smehs")) {
                        List<Smehs> topSmehs = smehsService.getTopSmehs(5);
                        StringBuilder builder = new StringBuilder("Топ-5 самых популярных анекдотов:\n");
                        for (int i = 0; i < topSmehs.size(); i++) {
                            Smehs smeh = topSmehs.get(i);
                            builder.append(i + 1)
                                    .append(". ")
                                    .append(smeh.getTitle())
                                    .append("\n");
                        }
                        telegramBot.execute(new SendMessage(chatId, builder.toString()));
                    }

                    if (update.message().text().startsWith("/add")) {
                        String[] parts1 = update.message().text().split(" ", 3);

                        if (parts1.length < 3) {
                            telegramBot.execute(new SendMessage(update.message().chat().id(), "Ошибка: недостаточно аргументов."));
                            return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        }

                        String command = parts1[0];
                        String title = parts1[1];
                        String content = parts1[2];

                        SmehSaveDTO smehs = new SmehSaveDTO();
                        smehs.setTitle(title);
                        smehs.setContent(content);
                        smehsService.addSmehs(smehs);
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Шутка добавлена!"));


                        System.out.println("Команда: " + command);
                        System.out.println("Тайтл: " + title);
                        System.out.println("Контент: " + content);
                    }

                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        telegramBot.setUpdatesListener(this);
    }
}