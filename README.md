# [STEP 6]
* [ERD](https://github.com/hwivelop/concert-reservation/blob/step6/docs/ERD.md)
* [DDL](https://github.com/hwivelop/concert-reservation/blob/step6/docs/DDL.sql)
* [API 명세서](https://github.com/hwivelop/concert-reservation/blob/step6/docs/APISPEC.yml)

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
