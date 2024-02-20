CREATE TABLE saved_jobs
(
    id               SERIAL PRIMARY KEY,
    job_seeker_id    INT,
    post_activity_id INT,
    UNIQUE (job_seeker_id, post_activity_id),
    FOREIGN KEY (job_seeker_id) REFERENCES post_activities (id),
    FOREIGN KEY (post_activity_id) REFERENCES job_seekers (job_seeker_id)
);