package com.example.collectors.groupby.dao;

import com.example.collectors.groupby.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends CrudRepository<Student,Long> {


    @Query(value = "select * from test.student s order by random() " , nativeQuery = true)
    public List<Student>  getRandomStudents(Pageable pageable);
}
