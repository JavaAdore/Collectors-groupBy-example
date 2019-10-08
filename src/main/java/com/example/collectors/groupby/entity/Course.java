package com.example.collectors.groupby.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity(name = "Course")
@Table(name = "course")
public class Course implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="course_id_pk_seq")
    @SequenceGenerator(name="course_id_pk_seq" , sequenceName="course_id_pk_seq",allocationSize=1)
    private Long id;

    private String name;


    public Course(String name) {
        this.name = name;
    }
}
