package com.example.collectors.groupby.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity(name="StudentCourse")
@Table(name="student_course")
public class StudentCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_course_id_pk_seq")
    @SequenceGenerator(name = "student_course_id_pk_seq", sequenceName = "student_course_id_pk_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;


    public StudentCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
