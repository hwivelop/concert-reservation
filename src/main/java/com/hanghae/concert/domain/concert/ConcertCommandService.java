package com.hanghae.concert.domain.concert;

import com.hanghae.concert.domain.concert.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ConcertCommandService {

    private final ConcertRepository concertRepository;


    public ConcertDto saveConcert(SaveConcertDto dto) {

        Concert concert = concertRepository.save(
                new Concert(
                        null,
                        dto.title(),
                        dto.capacity(),
                        dto.seatPrice(),
                        dto.concertStatus()
                )
        );

        return ConcertDto.of(concert);
    }
}
