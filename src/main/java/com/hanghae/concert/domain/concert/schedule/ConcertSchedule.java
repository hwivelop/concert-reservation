package com.hanghae.concert.domain.concert.schedule;

import com.hanghae.concert.domain.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "concert_schedule")
@Entity
public class ConcertSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "remaining_seat", nullable = false)
    private Integer remainingSeat;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    public void changeRemainingSeat(boolean isReserved) {
        if (isReserved) {
            this.remainingSeat -= 1;
        } else {
            this.remainingSeat += 1;
        }
    }
}
