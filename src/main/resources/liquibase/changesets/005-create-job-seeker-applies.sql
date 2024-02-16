CREATE TABLE job_seeker_applies
(
    id               SERIAL PRIMARY KEY,
    apply_date       TIMESTAMPTZ,
    cover_letter     VARCHAR(255),
    post_activity_id INT,
    job_seeker_id    INT,
    UNIQUE (job_seeker_id, post_activity_id),
    FOREIGN KEY (post_activity_id) REFERENCES post_activities (id),
    FOREIGN KEY (job_seeker_id) REFERENCES job_seekers (job_seeker_id)
);
