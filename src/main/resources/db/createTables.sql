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
    text TEXT
);

INSERT INTO review (doctor_id, score, text) VALUES
    (3, 5, 'Dr. is an excellent surgeon. Highly recommended!'),
    (4, 4, 'Dr. was very attentive and caring.'),
    (5, 5, 'Dr. provided outstanding treatment and advice.');

CREATE TABLE appointment
(
    id SERIAL PRIMARY KEY,
    date DATE,
    slot INT,
    doctor_id INT,
    CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id)
)