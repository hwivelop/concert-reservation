package com.hanghae.concert.domain.member.queue;

import com.hanghae.concert.domain.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_queue")
@Entity
public class MemberQueue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    @Column(name = "token", length = 255, nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private TokenStatus tokenStatus;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
}
