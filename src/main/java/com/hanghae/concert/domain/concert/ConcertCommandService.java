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


    public Concert save(Concert concert) {

        return concertRepository.save(concert);
    }
}
