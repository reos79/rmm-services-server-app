INSERT INTO customers (name) VALUES ('customer1');
INSERT INTO customers (name) VALUES ('customer2');
INSERT INTO customers (name) VALUES ('customer3');
INSERT INTO customers (name) VALUES ('customer4');
INSERT INTO customers (name) VALUES ('customer5');
INSERT INTO customers (name) VALUES ('customer6');

INSERT INTO users (username, customer_id, password) VALUES ('user1', 1, '$2a$10$QAAWcT/NnxMI/tANlu1nVOKc1NFRCxFmzwPFUbm2lNUjF1E1KUdk2');
INSERT INTO users (username, customer_id, password) VALUES ('user2', 2, '$2a$10$A8diAZ6YuIv52hmWtSLm0uq2wyhrWlpVMFSdnb5IRd6C.de2KpPLG');
INSERT INTO users (username, customer_id, password) VALUES ('user3', 3, '$2a$10$euY3q775pZRRrgqMqO0N3uK/aby4GAd5m2JjXtp1e/zZgBX4DH312');
INSERT INTO users (username, customer_id, password) VALUES ('user4', 4, '$2a$10$lIRMRe2HWm.lMW3btmm2.esf3aHUTnASxYAP54jM/np5.wEfNTv4m');
INSERT INTO users (username, customer_id, password) VALUES ('user5', 5, '$2a$10$2CgD/fdoDOJhxPpOndFlvuI0L.q80HvC5gdSrdNocwmbb3QMX8w06');
INSERT INTO users (username, customer_id, password) VALUES ('user6', 6, '$2a$10$X/zdcGBsYtvHsgL/VvPD9.rB1D3Q0zeNYcywdpx6h0AGJpiV8BYUG');

INSERT INTO services (name) VALUES ('Antivirus');
INSERT INTO services (name) VALUES ('Cloudberry');
INSERT INTO services (name) VALUES ('Teamviewer');
INSERT INTO services (name) VALUES ('PSA');

INSERT INTO services_price (service_id, device_type, price) VALUES (1,'WINDOWS_WORKSTATION',5);
INSERT INTO services_price (service_id, device_type, price) VALUES (1,'WINDOWS_SERVER',5);
INSERT INTO services_price (service_id, device_type, price) VALUES (1,'MAC',7);
INSERT INTO services_price (service_id, device_type, price) VALUES (2,'WINDOWS_WORKSTATION',3);
INSERT INTO services_price (service_id, device_type, price) VALUES (2,'WINDOWS_SERVER',3);
INSERT INTO services_price (service_id, device_type, price) VALUES (2,'MAC',3);
INSERT INTO services_price (service_id, device_type, price) VALUES (3,'WINDOWS_WORKSTATION',1);
INSERT INTO services_price (service_id, device_type, price) VALUES (3,'WINDOWS_SERVER',1);
INSERT INTO services_price (service_id, device_type, price) VALUES (3,'MAC',1);
INSERT INTO services_price (service_id, device_type, price) VALUES (4,'WINDOWS_WORKSTATION',2);
INSERT INTO services_price (service_id, device_type, price) VALUES (4,'WINDOWS_SERVER',2);
INSERT INTO services_price (service_id, device_type, price) VALUES (4,'MAC',2);

--Temp data
INSERT INTO devices (name, type, customer_id) VALUES ('Device1', 'MAC', 1);
INSERT INTO devices (name, type, customer_id) VALUES ('Device2', 'MAC', 1);
INSERT INTO devices (name, type, customer_id) VALUES ('Device3', 'MAC', 1);
INSERT INTO devices (name, type, customer_id) VALUES ('Device4', 'WINDOWS_WORKSTATION', 1);
INSERT INTO devices (name, type, customer_id) VALUES ('Device5', 'WINDOWS_SERVER', 1);

INSERT INTO customers_services (customer_id, service_id) VALUES (1, 1);
INSERT INTO customers_services (customer_id, service_id) VALUES (1, 2);
INSERT INTO customers_services (customer_id, service_id) VALUES (1, 3);