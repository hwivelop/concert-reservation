package com.hanghae.concert.domain.concert.schedule;

import com.hanghae.concert.domain.concert.schedule.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ConcertScheduleQueryService {

    private final ConcertScheduleRepository concertScheduleRepository;


    public List<ConcertScheduleDto> getAvailableConcertSchedules(Long concertId) {

        List<ConcertSchedule> concertSchedules = concertScheduleRepository.findAllByConcertId(concertId);

        return concertSchedules.stream()
                .filter(it -> it.getRemainingSeat() > 0)
                .map(ConcertScheduleDto::of)
                .collect(Collectors.toList());
    }
}
