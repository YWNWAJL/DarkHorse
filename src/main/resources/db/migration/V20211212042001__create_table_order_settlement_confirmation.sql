CREATE TABLE `order_settlement_confirmation`
(
    id VARCHAR(64) NOT NULL,
    order_id VARCHAR(64) NOT NULL,
    constraint pk_order_settlement_confirmation_id primary key (id)
);