package com.hanghae.concert.domain.concert.seat;

import com.hanghae.concert.domain.concert.seat.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ConcertSeatCommandService {

    private final ConcertSeatRepository concertSeatRepository;


    public ConcertSeat saveConcertSeat(SaveConcertSeatDto dto) {

        return concertSeatRepository.save(
                new ConcertSeat(
                        null,
                        dto.concertScheduleId(),
                        dto.seatNumber(),
                        true
                )
        );
    }
}
