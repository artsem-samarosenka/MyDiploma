-- Inserting data into the `user` table
insert into user (is_enabled, password, role, username)
values (1, '$2a$10$UeIDIvjZ0iK/H9tvq6M.SeSoIvHedlCZZtVyJXVh.rdwafQFwT5f2', 'ADMIN', 'admin1'),
       (1, '$2a$10$n3uuR6i/CweapItNbH/6y.x.vJPj7CmYDj6iPT2CpUFfMCF.JQJJ6', 'STUDENT', 'student1'),
       (1, '$2a$10$vB4dSCqfIvC93qpnTYBJS.BsFECD48w8Niit2MCTA3g.XZOaL4wU6', 'TEACHER', 'teacher1'),
       (1, 'password4', 'STUDENT', 'student2'),
       (1, 'password5', 'ADMIN', 'admin2');

-- Inserting data into the `admin` table
insert into admin (name, patronymic, surname, user_id)
values ('john', 'doe', 'admin', 1),
       ('emily', 'smith', 'admin', 5);

-- Inserting data into the `student` table
insert into student (course, faculty, name, patronymic, surname, user_id, account_id)
values (3, 'computer science', 'alice', 'smith', 'student', 2, '1901290080'),
       (4, 'chemistry', 'emma', 'brown', 'student', 4, '1901290081');

-- Inserting data into the `teacher` table
insert into teacher (faculty, name, patronymic, surname, user_id)
values ('physics', 'carol', 'davis', 'teacher', 3);

-- Inserting data into the `floor` table
insert into floor (description, number)
values ('first floor', 1),
       ('second floor', 2),
       ('third floor', 3),
       ('fourth floor', 4),
       ('fifth floor', 5);

-- Inserting data into the `room` table
insert into `room` (`number`, `capacity`, `floor_id`)
values  ('101', '21', '1'),
        ('201', '21', '2'),
        ('301', '21', '3'),
        ('401', '21', '4'),
        ('501', '21', '5'),
        ('102', '21', '1'),
        ('202', '20', '2'),
        ('302', '20', '3'),
        ('402', '20', '4'),
        ('502', '20', '5');

-- Inserting data into the `place` table
insert into `place` (`number`, `place_status`, `room_id`)
values  ('1', 'FREE', '1'),
        ('1', 'FREE', '2'),
        ('1', 'FREE', '3'),
        ('1', 'FREE', '4'),
        ('1', 'FREE', '5'),
        ('1', 'FREE', '6'),
        ('1', 'FREE', '7'),
        ('1', 'FREE', '8'),
        ('1', 'FREE', '9'),
        ('1', 'FREE', '10');

-- Inserting data into the `attendance` table
insert into attendance (attendance_status, date_time, is_open, place_id, student_id)
values ('NOT_CONFIRMED', '2023-05-11 10:00:00', 1, 1, 1),
       ('CONFIRMED', '2023-05-11 10:00:00', 0, 2, 1),
       ('NOT_CONFIRMED', '2023-05-11 10:00:00', 1, 3, 2);
