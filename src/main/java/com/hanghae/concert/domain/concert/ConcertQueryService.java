package com.hanghae.concert.domain.concert;

import com.hanghae.concert.domain.concert.dto.*;
import com.hanghae.concert.domain.concert.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ConcertQueryService {

    private final ConcertRepository concertRepository;


    public ConcertDto getConcertById(Long concertId) {

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(ConcertNotFoundException::new);

        return ConcertDto.of(concert);
    }


    public Boolean existsById(Long concertId) {

        return concertRepository.existsById(concertId);
    }
}
