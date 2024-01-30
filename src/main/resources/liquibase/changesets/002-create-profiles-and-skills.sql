CREATE TABLE job_seekers
(
    job_seeker_id      SERIAL PRIMARY KEY,
    city               VARCHAR(255),
    country            VARCHAR(255),
    employment_type    VARCHAR(255),
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    profile_photo      VARCHAR(255),
    resume             VARCHAR(255),
    state              VARCHAR(255),
    work_authorization VARCHAR(255),
    FOREIGN KEY (job_seeker_id) REFERENCES users (id)
);

CREATE TABLE recruiters
(
    recruiter_id  SERIAL PRIMARY KEY,
    city          VARCHAR(255),
    company       VARCHAR(255),
    country       VARCHAR(255),
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    profile_photo VARCHAR(64),
    state         VARCHAR(255),
    FOREIGN KEY (recruiter_id) REFERENCES users (id)
);

CREATE TABLE skills
(
    id                  SERIAL PRIMARY KEY,
    experience_level    VARCHAR(255),
    name                VARCHAR(255),
    years_of_experience VARCHAR(255),
    job_seeker_id       INT,
    FOREIGN KEY (job_seeker_id) REFERENCES job_seekers (job_seeker_id)
);