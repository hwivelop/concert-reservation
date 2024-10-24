package com.hanghae.concert.domain.concert.seat;

import com.hanghae.concert.domain.concert.schedule.*;
import com.hanghae.concert.domain.concert.schedule.dto.*;
import com.hanghae.concert.domain.concert.seat.dto.*;
import com.hanghae.concert.domain.concert.seat.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ConcertSeatQueryService {

    private final ConcertSeatRepository concertSeatRepository;

    /**
     * review point<br>
     * 콘서트 정보 생성 시, 좌석까지 일괄 생성하게 되면 데이터 비효율이 발생할 것으로 생각<br>
     * 그래서 예약된 좌석을 올려서, 프론트에서 가능한 좌석만 보여 주는 것으로 설계
     */
    public List<ConcertSeatDto> getReservedSeats(Long concertScheduleId) {

        List<ConcertSeat> concertSeats = concertSeatRepository.findAllByConcertScheduleId(concertScheduleId);

        return concertSeats.stream()
                .filter(ConcertSeat::getIsReserved)
                .map(ConcertSeatDto::of)
                .collect(Collectors.toList());
    }

    public Optional<ConcertSeat> getConcertSeat(Long concertScheduleId, int seatNumber) {

        return concertSeatRepository.findByConcertScheduleIdAndSeatNumberWithLock(concertScheduleId, seatNumber);
    }

    public ConcertSeat getConcertSeatById(Long concertSeatId) {

        return concertSeatRepository.findById(concertSeatId)
                .orElseThrow(ConcertSeatNotFoundException::new);
    }
}
