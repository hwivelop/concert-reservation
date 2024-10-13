package com.hanghae.concert.domain.concert.seat;

import com.hanghae.concert.domain.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "concert_seat")
@Entity
public class ConcertSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "concert_schedule_id", nullable = false)
    private Long concertScheduleId;

    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @Column(name = "is_availabe", nullable = false)
    private Boolean isAvailable;
}
