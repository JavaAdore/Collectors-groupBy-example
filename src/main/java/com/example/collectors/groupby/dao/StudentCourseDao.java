package com.example.collectors.groupby.dao;

import com.example.collectors.groupby.entity.Student;
import com.example.collectors.groupby.entity.StudentCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseDao extends CrudRepository<StudentCourse,Long> {

    @Query("select sc from StudentCourse sc LEFT JOIN FETCH sc.course where sc.student IN :students")
    public List<StudentCourse> getStudentsCourses(@Param("students") List<Student> students);
}
