package ru.dmitriev.lab.java_telg_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.dmitriev.lab.java_telg_bot.model.Smehs;

import java.util.List;

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
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                if (messageText.equals("/start")) {
                    telegramBot.execute(new SendMessage(chatId,
                            "Доступные команды:\n/random_smeh - случайный анекдот\n/all_smehs - все анекдоты"));
                }

                if (update.message().text().equals("/random_smeh")) {
                    List<Smehs> smehs = smehsService.getAllSmehs(null);

                    if (!smehs.isEmpty()) {
                        Smehs smeh = smehs.get((int) (Math.random() * smehs.size()));
                        String text = '"' + smeh.getTitle() + '"' +  "\n" + smeh.getContent();
                        telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                    }else {telegramBot.execute(new SendMessage(update.message().chat().id(), "В настоящий момент отсутствуют анекдоты"));}
                }
                if (update.message().text().equals("/all_Smehs")) {
                    List<Smehs> smehs = smehsService.getAllSmehs(null);
                    String text = "";
                    if (!smehs.isEmpty()) {
                        for (int i = 0; i < smehs.size(); i++) {
                            text = '"' + smehs.get(i).getTitle() + '"'  + "\n" + smehs.get(i).getContent()+ "\n\n";
                            telegramBot.execute(new SendMessage(update.message().chat().id(), text));
                        }
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
