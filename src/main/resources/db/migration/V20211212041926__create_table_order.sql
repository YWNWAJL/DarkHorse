CREATE TABLE restaurant_order
(
    id VARCHAR(64) NOT NULL,
    amount DECIMAL(20, 2),
    restaurant_pay_account VARCHAR(255),
    cancel_status VARCHAR(16),
    delivery_status VARCHAR(16),
    restaurant_address VARCHAR(255),
    customer_address VARCHAR(255),
    constraint pk_order_id primary key (id)
);