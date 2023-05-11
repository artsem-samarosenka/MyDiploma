create table `user` (
   id bigint not null auto_increment,
    is_enabled bit not null,
    password varchar(255),
    role varchar(255),
    username varchar(255),
    primary key (id)
);

create table admin (
    id bigint not null auto_increment,
    name varchar(255) not null,
    patronymic varchar(255) not null,
    surname varchar(255) not null,
    user_id bigint not null,
    primary key (id),
    foreign key (user_id) references user (id)
);

create table student (
   id bigint not null auto_increment,
    course integer not null,
    faculty varchar(255),
    name varchar(255) not null,
    patronymic varchar(255) not null,
    surname varchar(255) not null,
    account_id varchar(255) not null,
    user_id bigint not null,
    primary key (id),
    foreign key (user_id) references user (id)
);

create table teacher (
   id bigint not null auto_increment,
    faculty varchar(255),
    name varchar(255) not null,
    patronymic varchar(255) not null,
    surname varchar(255) not null,
    user_id bigint not null,
    primary key (id),
    foreign key (user_id) references user (id)
);

create table floor (
   id bigint not null auto_increment,
    description varchar(255),
    number integer not null,
    primary key (id)
);

create table room (
   id bigint not null auto_increment,
    capacity integer not null,
    number integer not null,
    floor_id bigint not null,
    primary key (id),
    foreign key (floor_id) references floor (id)
);

create table place (
   id bigint not null auto_increment,
    number integer not null,
    place_status varchar(255) not null,
    room_id bigint not null,
    primary key (id),
    foreign key (room_id) references room (id)
);

create table attendance (
    id bigint not null auto_increment,
    attendance_status varchar(255) not null,
    date_time datetime(6) not null,
    is_open bit not null,
    place_id bigint not null,
    student_id bigint not null,
    primary key (id),
    foreign key (student_id) references student (id),
    foreign key (place_id) references place (id)
);
