package com.hanghae.concert.domain.concert;

import com.hanghae.concert.domain.concert.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ConcertQueryService {

    private final ConcertRepository concertRepository;


    public Concert getConcertById(Long concertId) {

        return concertRepository.findById(concertId)
                .orElseThrow(ConcertNotFoundException::new);
    }


    public Boolean existsById(Long concertId) {

        return concertRepository.existsById(concertId);
    }
}
