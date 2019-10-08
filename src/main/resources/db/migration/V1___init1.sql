CREATE SCHEMA IF NOT EXISTS test;



CREATE TABLE test.student
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT student_pkey PRIMARY KEY (id)
);

CREATE TABLE test.course
(
    id bigint NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT course_pkey PRIMARY KEY (id)
);


CREATE TABLE test.student_course
(
    student_id bigint,
    course_id bigint,
    id bigint
);



CREATE SEQUENCE test.course_id_pk_seq;
CREATE SEQUENCE test.student_course_id_pk_seq;
CREATE SEQUENCE test.student_id_pk_seq;


-- I'm lazy to create foreign keys :)