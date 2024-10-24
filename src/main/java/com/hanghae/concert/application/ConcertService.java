package com.hanghae.concert.application;

import com.hanghae.concert.application.dto.*;
import com.hanghae.concert.domain.concert.*;
import com.hanghae.concert.domain.concert.schedule.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConcertService {

    private final ConcertCommandService concertCommandService;
    private final ConcertScheduleCommandService concertScheduleCommandService;

    public void createConcert(CreateConcertDto dto) {

        Concert concert = saveConcert(dto);

        saveConcertSchedule(dto, concert);
    }

    private Concert saveConcert(CreateConcertDto dto) {

        return concertCommandService.save(
                new Concert(
                        null,
                        dto.title(),
                        dto.capacity(),
                        dto.seatPrice(),
                        dto.concertStatus()
                )
        );
    }

    private void saveConcertSchedule(CreateConcertDto dto, Concert concert) {

        concertScheduleCommandService.save(
                new ConcertSchedule(
                        null,
                        concert.getId(),
                        concert.getCapacity(),
                        dto.startAt()
                )
        );
    }
}
