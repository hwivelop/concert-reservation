package com.hanghae.concert.domain.concert;

import com.hanghae.concert.domain.concert.dto.*;
import com.hanghae.concert.domain.concert.exception.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertQueryServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertQueryService concertQueryService;


    @Test
    @DisplayName("concertId로 콘서트 정보를 조회한다.")
    void getConcertById() {

        //given
        Long concertId = 1L;
        when(concertRepository.findById(concertId))
                .thenReturn(
                        Optional.of(new Concert(concertId, "콘서트명", 50, 150000, ConcertStatus.AVAILABLE))
                );

        //when
        ConcertDto concertDto = ConcertDto.of(concertQueryService.getConcertById(concertId));

        //then
        assertThat(concertDto.concertId()).isEqualTo(concertId);
    }

    @Test
    @DisplayName("concertId로 콘서트 정보가 없으면 예외를 던진다.")
    void getConcertByIdException() {

        //given
        Long concertId = 1L;
        when(concertRepository.findById(concertId))
                .thenReturn(Optional.empty());

        //when
        ConcertNotFoundException exception = assertThrows(ConcertNotFoundException.class, () -> {
            concertQueryService.getConcertById(concertId);
        });

        //then
        assertEquals("콘서트 정보가 존재하지 않습니다", exception.getMessage());
    }

    @Test
    @DisplayName("concertId로 콘서트 정보가 있으면 true 로 반환한다.")
    void getConcertByIdTrue() {

        //given
        Long concertId = 1L;
        when(concertRepository.existsById(concertId))
                .thenReturn(true);

        //when
        Boolean exists = concertQueryService.existsById(concertId);

        //then
        assertThat(exists).isEqualTo(true);
    }

    @Test
    @DisplayName("concertId로 콘서트 정보가 없으면 false 로 반환한다.")
    void getConcertByIdFalse() {

        //given
        Long concertId = 1L;
        when(concertRepository.existsById(concertId))
                .thenReturn(false);

        //when
        Boolean exists = concertQueryService.existsById(concertId);

        //then
        assertThat(exists).isEqualTo(false);
    }
}