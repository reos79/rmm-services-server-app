-- customers of the system
CREATE TABLE customers (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

-- devices of the system
CREATE TABLE devices (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    customer_id   INTEGER      NOT NULL,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

-- services of the system
CREATE TABLE services (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

-- services of the system
CREATE TABLE services_price (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    service_id   INTEGER      NOT NULL,
    device_type VARCHAR(30) NOT NULL,
    price DECIMAL NOT NULL,
    PRIMARY KEY (id)
);

-- services of the system
CREATE TABLE customers_services (
    customer_id   INTEGER      NOT NULL,
    service_id   INTEGER      NOT NULL,
    PRIMARY KEY (customer_id, service_id)
);

-- Users of the system
CREATE TABLE users (
    username   VARCHAR(30)     NOT NULL,
    password VARCHAR(128) NOT NULL,
    customer_id   INTEGER      NOT NULL,
    PRIMARY KEY (username)
);