# API 목록
* 대기열
  * [토큰 발급 및 상태 변경](#토큰-발급-및-상태-변경)
  * [나의 대기번호 조회](#나의-대기번호-조회)
* 예약
  * [예약 가능 날짜 조회](#예약-가능-날짜-조회)
  * [예약 가능 좌석 조회](#예약-가능-좌석-조회)
  * [좌석 예약 요청](#좌석-예약-요청)
* 결제
  * [잔액 조회](#잔액-조회)
  * [잔액 충전](#잔액-충전)
  * [결제](#결제)
* 기타
  * [콘서트 정보 입력](#콘서트-정보-입력)

# 토큰 발급 및 상태 변경

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as 대기열 서비스
    participant queue as user_queue
%%  구현부
    member ->> api: 토큰 발급 요청
    activate member
    activate api
    api ->> queue: 토큰 발급 요청
    deactivate api
    activate queue
    alt 기존 발급 유저
       queue -->> api: 기존 토큰 반환
    deactivate queue
    else 신규 발급 유저
    queue ->> queue: concertId의 ACTIVE 토큰이 해당 정원을 초과하는지 여부 확인
    activate queue
    alt 초과한 경우
        queue ->> queue: 신규 토큰 저장<br>(status : 'WAIT', expiredAt : null)
    else 초과하지 않은 경우
        queue ->> queue: 신규 토큰 저장<br>(status : 'ACTIVE', expiredAt : +5분)
    end
    end
    queue -->> api: 토큰 반환
    deactivate queue
    activate api
    api -->> member: 토큰 반환
    deactivate api
    deactivate member
%%    놀이공원 방식의 상태 변경
    loop 1분마다
        api ->> queue: [Scheduler] 대기열 상태 변경 요청
        activate api
        activate queue
        queue ->> queue: ACTIVE 상태이면서, ExpiredAt이 만료된 인원 전부 <br>(status : 'ACTIVE' -> 'EXPIRED')
    end
    loop 5분마다
        queue ->> queue: 정원 내에서 가능한 수만큼 변경<br>(status : 'WAIT' -> 'ACTIVE')
        queue ->> api: 대기열 상태 변경 종료
        deactivate queue
        deactivate api
    end
```

# 나의 대기번호 조회

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant queue as user_queue
%%  구현부
    loop 5분마다
        member ->> api: 내 대기 순번 확인 요청
        activate member
        activate api
        api ->>+ queue: 토큰 검증 요청<br>(w.token)
        deactivate api
        activate queue

        alt [(성공 케이스)<br>status WAIT, ACTIVE]
            queue -->> api: 내 대기 순번 or 입장 가능 상태 반환
        else (실패 케이스)<br>status: EXPIRED
            queue -->> api: 만료된 토큰
            activate api
            deactivate queue
            api -->> member: 결과 응답
        end
        deactivate api
        deactivate member
    end
```

# 콘서트 정보 입력

```mermaid
sequenceDiagram
    autonumber
%%  선언부
  actor member as 고객
  participant api as API 서버
  participant concert as concert
  participant schedule as concert_schedule
%%  구현부
  member ->> api: 콘서트 정보 생성 요청
  activate member
  activate api
  api ->> concert: 콘서트 기본 정보 생성 요청
  deactivate api
  activate concert
  concert ->> schedule: 콘서트 상세 정보 생성 요청
  activate schedule
  schedule ->> concert: 콘서트 상세 정보 생성 결과 반환
  deactivate schedule
  concert ->> api: 콘서트 정보 생성 결과 반환
  deactivate concert
  activate api
  api ->> member: 콘서트 정보 생성 결과 응답
  deactivate api
  deactivate member
```

# 예약 가능 날짜 조회

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant queue as user_queue
    participant concert as concert <br>& concert_schedule
%%  구현부
    member ->> api: 예약 가능 날짜 요청<br>(w. concertId)
    activate member
    activate api
    api ->>+ queue: 토큰 검증 요청<br>(w.token)
    deactivate api
    activate queue
    queue -->> api: 토큰 검증 결과 반환
    deactivate queue
    activate api

    alt [(성공 케이스)<br>status ACTIVE]
        api ->> concert: 예약 가능 날짜 전체 조회
        deactivate api
        activate concert
        concert -->> api: 예약 가능 날짜 전체 반환
        deactivate concert
        activate api
        api -->> member: 예약 가능 날짜 전체 반환
        deactivate api
    else [(실패 케이스)<br>status WAIT, EXPIRED]
        queue -->> api: exception, 검증 실패
        activate api
        api ->> member: exception, 검증 실패
        deactivate api
        deactivate member
    end

```

# 예약 가능 좌석 조회

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant queue as user_queue
    participant seat as concert_seat
%%  구현부
    member ->> api: 예약 가능 좌석 조회<br>(w. concertId, startAt)
    activate member
    activate api
    api ->> queue: 토큰 검증 요청<br>(w.token)
    deactivate api
    activate queue
    queue ->> api: 토큰 검증 결과 반환
    deactivate queue
    activate api
    alt [(성공 케이스)<br>status ACTIVE]
        api ->> seat: 예약 가능 좌석 전체 조회
        deactivate api
        activate seat
        seat -->> api: 예약 가능 좌석 전체 반환
        deactivate seat
        activate api
        api -->> member: 예약 가능 좌석 전체 응답
        deactivate api
    else [(실패 케이스)<br>status WAIT, EXPIRED]
        queue -->> api: 검증 실패
        activate api
        api -->> member: 검증 실패 응답
        deactivate api
        deactivate member
    end
```

# 좌석 예약 요청

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant queue as user_queue
    participant concert as concert_seat
    participant reservation as reservation
    member ->> api: 좌석 예약 요청<br>(w. concertScheduleId, seatNum)
    activate member
    activate api
    api ->> queue: 토큰 검증 요청<br>(w.token)
    deactivate api
    activate queue
    queue -->> api: 토큰 검증 결과 반환
    deactivate queue
    activate api

    alt 토큰 검증 성공 > token status: ACTIVE
        api ->> concert: 요청 좌석이 예약 가능 상태인지 조회 <br> (Expired or 생성된 데이터 없음)
        deactivate api
        activate concert
        alt 좌석 예약 가능<br>(seat status: EXPIRED)
            concert -->> api: 예약 가능 반환
            deactivate concert
            activate api
            api ->> concert: 좌석 예약 요청
            activate concert
            concert ->> concert: 좌석 데이터 생성(업데이트)<br>(seat status: TEMP_RESERVE)
            api ->> reservation: 좌석 예약 데이터 생성(업데이트)
            deactivate api
            activate reservation
            reservation ->> reservation: 좌석 예약 데이터 생성(업데이트)<br>(seat status: TEMP_RESERVE)
            concert -->> api: 좌석 데이터 반환
            deactivate reservation
            deactivate concert
            activate api
            api -->> member: 좌석 예약 결과 응답
            deactivate api
        else 좌석 예약 불가<br>(seat status: TEMP_RESERVE, RESERVED)
            concert ->> api: 예약 불가 반환
            activate api
            api -->> member: 좌석 예약 결과 응답
            deactivate api
        end
    else 토큰 검증 실패 > token status: WAIT, EXPIRED
        api -->> member: 토큰 검증 실패 응답
    end
    deactivate member
```


# 잔액 조회

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant user as user
%%  구현부
    member ->> api: 고객 잔고 조회 요청<br>w.<br>token

    alt [(성공 케이스)]
        api ->>+ user: 잔액 조회 요청
        user ->> api: 잔액 반환
        api ->> member: 잔액 응답
    else [(실패 케이스)]
        user -->> api: Exception, 미확인 유저
        api ->> member: Exception, 미확인 유저
    end
```


# 잔액 충전

```mermaid
sequenceDiagram
    autonumber
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant user as user
    participant payment as payment_history
%%  구현부
    member ->> api: 고객 잔고 충전 요청<br>w.<br>token, amount

    alt [(성공 케이스)]
        api ->> user: 잔액 충전
        api ->> payment: 충전 히스토리 저장
        user -->> api: 충전 후 금액 반환
        api -->> member: 잔액 응답
    else [(실패 케이스)]
        user -->> api: Exception, 미확인 유저
        api ->> member: Exception, 미확인 유저 예외
        user -->> api: Exception, 0원 이하 금액일 경우
        api ->> member: Exception, 비정상 충전 예외
    end
```

# 결제

```mermaid
sequenceDiagram
    autonumber
%%  선언부
    actor member as 고객
    participant api as API 서버
    participant queue as user_queue
    participant user as user
%%    participant concert as concert<br> & concert_schedule<br> & concert_seat<br>
    participant reservation as reservation
    participant payment as payment_history
%%  구현부
    member ->> api: 결제 요청<br>w.<br>reservation_id
    api ->>+ queue: 토큰 검증 요청<br>(w.token)
    queue ->> queue: 토큰 검증

    alt [(성공 케이스) status ACTIVE]
        queue ->> api: 토큰 검증 성공 반환
        api ->> reservation: 예약 내용 확인 요청
        alt [(성공 케이스) 예약 내역 확인]
            reservation -->> api: 예약 내용 반환
            api ->> user: 잔액 검증
            alt [(성공케이스) 잔액 충분]
                user ->> user: 잔액 차감
                user ->> reservation: 예약 상태 변경
                user ->> payment: 잔액 차감 히스토리 저장
                user ->> queue: 토큰 상태 변경 (Expired)
                user ->> api: 결제 완료 응답
            else [(실패케이스) 잔액 부족]
                user -->> api: Exception, 잔액 부족
            end
            api ->> member: 결제 결과 응답
        else [실패케이스<br>만료된 예약]
            reservation -->> api: Exception, 예약 내역 없음
            api ->> member: Exception, 예약 내역 없음
        end
    else [(실패 케이스) status WAIT, EXPIRED]
        queue ->> member: 토큰 검증 실패 반환
    end
```