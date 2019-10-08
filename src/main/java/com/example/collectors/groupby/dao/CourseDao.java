package com.example.collectors.groupby.dao;

import com.example.collectors.groupby.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao  extends CrudRepository<Course,Long> {
}
