CREATE TABLE concert
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(255) NOT NULL COMMENT '콘서트 명',
    capacity   INT          NOT NULL COMMENT '좌석 수',
    seat_price INT          NOT NULL COMMENT '좌석 금액',
    status     VARCHAR(10)  NOT NULL COMMENT '예매 가능 상태(SOLD_OUT, AVAILABLE)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시'
);

CREATE TABLE concert_schedule
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    concert_id     BIGINT    NOT NULL,
    remaining_seat INT       NOT NULL COMMENT '잔여 좌석',
    start_at       TIMESTAMP NOT NULL COMMENT '콘서트 시작 시간',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
);

CREATE TABLE concert_seat
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    concert_schedule_id BIGINT  NOT NULL,
    seat_number         INT     NOT NULL COMMENT '좌석 번호',
    is_available        BOOLEAN NOT NULL DEFAULT TRUE COMMENT '예매 가능 여부',
    created_at          TIMESTAMP        DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at          TIMESTAMP        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
);

CREATE TABLE member
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    balance    BIGINT NOT NULL COMMENT '보유 금액',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시'
);

CREATE TABLE member_queue
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id  BIGINT       NOT NULL,
    token      VARCHAR(255) NOT NULL COMMENT '대기열 토큰',
    status     VARCHAR(10)  NOT NULL COMMENT '대기열 상태(WAITING, PROGRESS, DONE)',
    expired_at TIMESTAMP COMMENT '대기열 만료 시간',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
);

CREATE TABLE payment_history
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id      BIGINT      NOT NULL,
    reservation_id BIGINT      NOT NULL,
    type           VARCHAR(10) NOT NULL COMMENT '충전/사용(CHARGE, USE)',
    amount         INT         NOT NULL COMMENT '금액',
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
);

CREATE TABLE reservation
(
    id                  BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id           BIGINT      NOT NULL,
    concert_schedule_id BIGINT      NOT NULL,
    concert_seat_id     BIGINT      NOT NULL,
    price               BIGINT      NOT NULL,
    status              VARCHAR(15) NOT NULL COMMENT '예약 상태(TEMP_RESERVED, RESERVED, EXPIRED)',
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
);


