package com.example.collectors.groupby;

import com.example.collectors.groupby.dao.CourseDao;
import com.example.collectors.groupby.dao.StudentCourseDao;
import com.example.collectors.groupby.dao.StudentDao;
import com.example.collectors.groupby.entity.Course;
import com.example.collectors.groupby.entity.Student;
import com.example.collectors.groupby.entity.StudentCourse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@SpringBootApplication
public class HibernateMapResultApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateMapResultApplication.class, args);
    }

    StudentDao studentDao;
    CourseDao courseDao;
    StudentCourseDao studentCourseDao;


    @Bean
    public CommandLineRunner commandLineRunner(StudentDao studentDao, CourseDao courseDao, StudentCourseDao studentCourseDao) {

        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.studentCourseDao=studentCourseDao;


        return args -> {

            resetStudentCoursesTables();

            generateTestData();

            List<Student> students  = studentDao.getRandomStudents( PageRequest.of(0,5000));

            Map<Long,List<Course>> studentCoureseMap = getStudentsCoursesMap(students);


            System.out.println();
        };

    }

    private void resetStudentCoursesTables() {

        studentCourseDao.deleteAll();
        studentDao.deleteAll();
        courseDao.deleteAll();;
    }

    private Map<Long, List<Course>> getStudentsCoursesMap(List<Student> students) {
        List<StudentCourse> result = studentCourseDao.getStudentsCourses(students);
        Map<Long, List<Course>> res = result.stream().collect(
                Collectors.groupingBy(
                        // key student Id for each student
                        // student object impossible to be null as I fetch using it
                        (StudentCourse o) -> o.getStudent().getId(),

                        // value
                        Collectors.mapping(StudentCourse::getCourse, Collectors.toList())
                )
        );
        return res;
    }

    private void generateTestData() {
        // creating courses
        List<Course> courses = persistRandomCourses();

        List<Student> students = persistRandomStudents();

        associateRandomStudentsWithRandomCoures(students,courses);
    }

    private void associateRandomStudentsWithRandomCoures(List<Student> students, List<Course> courses) {

        Random random = new Random();

        courses.stream().forEach(course -> {

            int numberRepresents50PercentOfStudentList  = (int) (students.size() * 0.5);
            int randomNumberRepresentsNumberOfCourseCandidates =  numberRepresents50PercentOfStudentList +  random.nextInt( students.size() - numberRepresents50PercentOfStudentList);
            Set<Integer> suggestedIndices= prepareRandomIndecies(randomNumberRepresentsNumberOfCourseCandidates , students.size()-1);
            suggestedIndices.forEach(index ->{
              Student selectedStudent=  students.get(index);
                studentCourseDao.save(new StudentCourse(selectedStudent, course));
            });
        });




    }

    private Set<Integer> prepareRandomIndecies(int numberOfDesiredIndecies, int totalNumberOfIndecies) {

        Set<Integer> selectedIndices = new HashSet<>();
        Random random = new Random();
        while(selectedIndices.size()<numberOfDesiredIndecies)
        {
            selectedIndices.add(random.nextInt(totalNumberOfIndecies+1));
        }
        return selectedIndices;
    }

    private List<Student> persistRandomStudents() {

        final List<Student> students = new ArrayList<>();
        Stream.iterate(1, i -> i + 1).limit(5000).forEach(
                i ->
                {
                    String studentName = generateStudentName();
                    Student student = studentDao.save(new Student(studentName));
                    students.add(student);
                }
        );
        return students;
    }

    private List<Course> persistRandomCourses() {
        String[] coursesNames = {"java", "oracle", "c++", "scala", "ruby", "python", "c#"};

        List<Course> courses = new ArrayList<>();
        Arrays.asList(coursesNames).stream().forEach(courseName -> {
            Course course = courseDao.save(new Course(courseName));
            courses.add(course);
        });
        return courses;
    }

    private String generateStudentName() {
        Random random = new Random();
        int nameLength = 3 + random.nextInt(8);
        StringBuilder generatedNameStringBuilder = new StringBuilder();
        Stream.iterate(0, i -> i + 1).limit(nameLength).forEach(
                i -> {
                    char suggestedChar = (char) (97 + random.nextInt(27));
                    generatedNameStringBuilder.append(suggestedChar);
                }
        );

        String studentName = generatedNameStringBuilder.toString();

        // clear string builder
        generatedNameStringBuilder.setLength(0);
        return studentName;

    }

    private Map<Long, List<Course>> populateUsersCoursesMap(List<StudentCourse> result) {
        Map<Long, List<Course>> studentCourses = new HashMap<>();
        // sort
        result.stream()
                .sorted((sc1, sc2) -> sc1.getStudent().getId().compareTo(sc2.getStudent().getId()))
                .forEach(sc -> {
                    List<Course> courses = studentCourses.get(sc.getStudent().getId());
                    if (null == courses) {
                        courses = new ArrayList<>();
                        studentCourses.put(sc.getId(), courses);
                    }
                    courses.add(sc.getCourse());
                });

        return studentCourses;
    }
}



