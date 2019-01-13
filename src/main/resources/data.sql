DELETE FROM user_role;
DELETE FROM roles;
DELETE FROM users;
DELETE FROM vehicles;

INSERT INTO roles (id, role_name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO users (id, username, password, full_name, enabled) VALUES
(1, 'admin@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin', true ),
(2, 'user@gmail.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'User', true);

INSERT INTO user_role(user_id, role_id) VALUES (1,1),(1,2),(2,2);

INSERT INTO vehicles(id, make, model) VALUES (1, 'Skoda', 'Felicia');