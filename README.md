# [STEP 5]
* [마일스톤](https://github.com/users/hwivelop/projects/3)
* [Sequence Diagram](https://github.com/hwivelop/concert-reservation/blob/develop/docs/SEQUENCE.md)
* [FlowChart](https://github.com/hwivelop/concert-reservation/blob/develop/docs/FLOWCHART.md)

<details>
  <summary>[비즈니스 로직 예상 및 분석]</summary>

`콘서트 시나리오`의 경우, 대기열과 레디스를 사용할 수 있어 선정하였습니다.

요구사항 분석

#### 유저 토큰 발급 API
    - DB 의 부하를 줄이기 위해 대기열을 도입
    - 최초 1회 토큰 발급이 필수
    - 모든 API를 호출하기 전, 유저의 대기열 토큰 상태를 확인하여 API 호출 가능 여부를 판단
    - 토큰 응답 시에는 유저 UUID/ 대기 순서/ 남은 대기 시간을 포함
    - 대기열의 유효 시간을 설정하여 일정 시간이 지나면 토큰이 만료되도록 처리

#### 내 대기번호를 조회하는 폴링용 API
    - n분마다 클라이언트가 폴링 방식으로 해당 API를 호출하여 자신의 대기번호를 확인
    - 순번, 대기 순서, 남은 대기 시간 반환
    - 대기 상태가 PROGRESS로 변경되면 API 호출 가능

#### 예약 가능 날짜 / 좌석 API
    - 대기열의 토큰 상태가 PROGRESS 경우만, 호출 가능
    - 예약 가능한 날짜 목록을 조회하여 사용자가 선택할 수 있도록 반환
    - 특정 날짜를 입력받아 해당 날짜의 예약 가능한 좌석 정보를 조회, 좌석 번호와 상태(예약 가능 여부)를 응답
    - 좌석 번호는 1번부터 50번까지 관리되며, 사용자는 이를 기준으로 예약 가능 여부를 확인

#### 좌석 예약 요청 API
    - 대기열의 토큰 상태가 PROGRESS 경우만, 호출 가능
    - 날짜와 좌석 번호를 입력받아 좌석 예약(5분 동안 임시 예약)
    - 임시 예약된 좌석은 다른 사람이 예약 및 결제 할 수 없음
    - 5분 후 임시 배정이 해제되면 다른 사람이 예약 가능

#### 잔액 충전 / 조회 API
    - 유저의 충전 금액을 입력받아 잔액을 충전
    - 유저의 잔액을 조회

#### 결제 API
    - 좌석 예약 시 임시 배정된 좌석에 대해 결제 처리
    - 결제 완료 후 토큰을 만료 처리, 사용 히스토리 저장

</details>

# [STEP 6]
* [ERD](https://github.com/hwivelop/concert-reservation/blob/develop/docs/ERD.md)
* [DDL](https://github.com/hwivelop/concert-reservation/blob/develop/docs/DDL.sql)
* [API 명세서](https://github.com/hwivelop/concert-reservation/blob/develop/docs/APISPEC.yml)

<details>
  <summary>[패키지 구조 및 기술 스택 선정]</summary>

```text
ConcertReservationApplication.java
├── api
│   ├── concert
│   │   ├── ConcertController.java
│   │   └── dto
│   │       ├── request
│   │       │   └── ReservationSeatRequest.java
│   │       └── response
│   │           ├── ConcertAvailableDateResponse.java
│   │           ├── ConcertAvailableSeatResponse.java
│   │           └── ReservationPayResponse.java
│   ├── config
│   │   └── SwaggerConfig.java
│   ├── member
│   │   ├── MemberController.java
│   │   └── dto
│   │       ├── request
│   │       │   └── MemberChargeRequest.java
│   │       └── response
│   │           └── MemberResponse.java
│   ├── payment
│   │   ├── PaymentHistoryController.java
│   │   └── dto
│   │       ├── request
│   │       └── response
│   │           └── PaymentHistoryResponse.java
│   └── queue
│       ├── MemberQueueController.java
│       └── dto
│           ├── request
│           └── response
│               ├── MemberQueueCreateResponse.java
│               └── MemberQueueMyTurnResponse.java
└── domain
├── BaseCreateEntity.java
├── BaseEntity.java
├── concert
│   ├── Concert.java
│   ├── ConcertStatus.java
│   ├── schedule
│   │   └── ConcertSchedule.java
│   └── seat
│       └── ConcertSeat.java
├── member
│   ├── Member.java
│   └── queue
│       ├── MemberQueue.java
│       └── TokenStatus.java
├── payment
│   ├── PaymentHistory.java
│   └── PaymentType.java
└── reservation
├── Reservation.java
└── ReservationStatus.java
```

## 기술 스택

#### Architecture
    - Layered Architecture Based
#### DB ORM
    - JPA
    - MySQL(임시 H2)
#### Test
    - JUnit + AssertJ

## 개발 환경

#### java
    - 17
#### spring boot
    - 3.3.4

</details>

