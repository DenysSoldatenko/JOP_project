CREATE TABLE companies
(
    id   SERIAL PRIMARY KEY,
    logo VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE locations
(
    id      SERIAL PRIMARY KEY,
    city    VARCHAR(255),
    country VARCHAR(255),
    state   VARCHAR(255)
);
