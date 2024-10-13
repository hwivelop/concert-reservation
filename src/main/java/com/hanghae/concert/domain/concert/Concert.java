package com.hanghae.concert.domain.concert;

import com.hanghae.concert.domain.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "concert")
@Entity
public class Concert extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "seatPrice", nullable = false)
    private Integer seatPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConcertStatus concertStatus;
}
