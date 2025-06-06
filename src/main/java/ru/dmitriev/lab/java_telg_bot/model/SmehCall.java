package ru.dmitriev.lab.java_telg_bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "smeh_calls")
@Table(name = "smeh_calls")
public class SmehCall {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "smeh_calls_seq_gen")
    @SequenceGenerator(name = "smeh_calls_seq_gen", sequenceName = "smeh_calls_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "call_time", nullable = false)
    private LocalDateTime callTime;

    @ManyToOne
    @JoinColumn(name = "smeh_id", nullable = false)
    private Smehs smeh;

    public SmehCall(Long userId, LocalDateTime callTime, Smehs smeh) {
        this.userId = userId;
        this.callTime = callTime;
        this.smeh = smeh;
    }
}

