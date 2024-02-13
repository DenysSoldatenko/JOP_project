CREATE TABLE post_activities
(
    id                 SERIAL PRIMARY KEY,
    description_of_job VARCHAR(10000),
    job_title          VARCHAR(255),
    job_type           VARCHAR(255),
    posted_date        TIMESTAMPTZ,
    remote             VARCHAR(255),
    salary             VARCHAR(255),
    company_id         INT,
    location_id        INT,
    user_id            INT,
    FOREIGN KEY (company_id) REFERENCES companies (id),
    FOREIGN KEY (location_id) REFERENCES locations (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);