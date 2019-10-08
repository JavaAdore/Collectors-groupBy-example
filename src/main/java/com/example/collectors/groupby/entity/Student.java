package com.example.collectors.groupby.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity(name = "Student")
@Table(name = "student")
public class Student implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="student_id_pk_seq")
    @SequenceGenerator(name="student_id_pk_seq" , sequenceName="student_id_pk_seq",allocationSize=1)
    private Long id;

    private String name;

    public Student(String name) {
        this.name = name;
    }
}
