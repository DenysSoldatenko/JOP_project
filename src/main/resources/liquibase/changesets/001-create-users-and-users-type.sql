CREATE TABLE IF NOT EXISTS users_type
(
    user_type_id   SERIAL PRIMARY KEY,
    user_type_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id                SERIAL PRIMARY KEY,
    email             VARCHAR(255) UNIQUE,
    is_active         BOOLEAN,
    password          VARCHAR(255),
    registration_date TIMESTAMPTZ,
    user_type_id      INT,
    FOREIGN KEY (user_type_id) REFERENCES users_type (user_type_id)
);

INSERT INTO users_type (user_type_id, user_type_name)
VALUES (1, 'Recruiter'),
       (2, 'Job Seeker');