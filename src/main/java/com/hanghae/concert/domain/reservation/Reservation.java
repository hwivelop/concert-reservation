package com.hanghae.concert.domain.reservation;

import com.hanghae.concert.domain.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "concert_seat_id", nullable = false)
    private Long concertSeatId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus reservationStatus;

    @Column(name = "reservation_price", nullable = false)
    private Integer reservationPrice;

    public boolean isExpired() {
        return reservationStatus == ReservationStatus.EXPIRED;
    }

    public void changeStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
