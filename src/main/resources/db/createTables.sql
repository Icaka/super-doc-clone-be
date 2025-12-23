CREATE TABLE doctors(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    picture VARCHAR(1000),
    rating REAL,
    date_of_birth DATE,
    description TEXT
);

INSERT INTO doctors (first_name, last_name, picture, rating, date_of_birth, description) VALUES
     ('John', 'Doe', 'https://example.com/john_doe.jpg', 4.8, '1980-05-15', 'An experienced cardiologist with over 20 years in the field.'),
     ('Emily', 'Smith', 'https://example.com/emily_smith.jpg', 4.5, '1975-09-22', 'Specialist in neurology with a passion for research and patient care.'),
     ('Michael', 'Brown', 'https://example.com/michael_brown.jpg', 4.9, '1988-03-10', 'An orthopedic surgeon known for his innovative techniques in joint replacement.'),
     ('Sarah', 'Johnson', 'https://example.com/sarah_johnson.jpg', 4.7, '1990-07-08', 'A pediatrician dedicated to improving child healthcare in underserved communities.'),
     ('David', 'Wilson', 'https://example.com/david_wilson.jpg', 4.6, '1983-11-30', 'An ENT specialist with expertise in complex sinus surgeries.');

CREATE TABLE review
(
    id SERIAL PRIMARY KEY,
    doctor_id INT,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    score INT,
    text    TEXT,
    user_id INT,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO review (doctor_id, score, text, user_id)
VALUES (3, 5, 'Dr. is an excellent surgeon. Highly recommended!', 5),
       (4, 4, 'Dr. was very attentive and caring.', 5),
       (5, 5, 'Dr. provided outstanding treatment and advice.', 5),
       (5, 4, 'Cool', 4),
       (5, 4, 'Yes', 4);

CREATE TABLE appointment
(
    id SERIAL PRIMARY KEY,
    date DATE,
    slot INT,
    doctor_id INT,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE schedule
(
    id        SERIAL PRIMARY KEY,
    count     INT,
    length    INT,
    workStart TIME,
    date      DATE,
    doctor_id INT,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);

INSERT INTO appointment (date, slot, doctor_id)
VALUES ('2025-03-23', 6, 4),
       ('2025-03-23', 5, 4),
       ('2025-03-23', 4, 4),
       ('2025-03-23', 3, 4),
       ('2025-11-23', 6, 4),
       ('2025-11-23', 5, 4),
       ('2025-07-15', 8, 4),
       ('2025-11-10', 3, 2),
       ('2025-11-10', 4, 2),
       ('2025-11-10', 5, 2);

CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(32),
    last_name     VARCHAR(32),
    date_of_birth DATE
);

INSERT INTO users(first_name, last_name, date_of_birth)
VALUES ('John', 'Persy', '1990-05-15'),
       ('Patrick', 'Star', '1995-10-10'),
       ('Tat', 'Star', '1995-10-10'),
       ('Connor', 'Star', '1995-10-10'),
       ('Jeff', 'Star', '1995-10-10');