package com.hanghae.concert.domain.concert.schedule;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ConcertScheduleCommandService {

    private final ConcertScheduleRepository concertScheduleRepository;


    public ConcertSchedule save(ConcertSchedule concertSchedule) {

        return concertScheduleRepository.save(concertSchedule);
    }
}
