package com.example.repository;

import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.mapper.CourseAverageMark;
import com.example.mapper.StudentAverageMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>,
        PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into student_course_mark(student_id,course_id,mark,created_date) values(:s_id,:c_id,:mark,:c_date)", nativeQuery = true)
    void saveQuery(@Param("s_id") Integer s_id, @Param("c_id") Integer c_id, @Param("mark") Integer mark, @Param("c_date") LocalDateTime createdDate);

    @Transactional
    @Modifying
    @Query("update StudentCourseMarkEntity set student=:student, course=:course, mark=:mark where id=:id")
    void update(@Param("id") Integer id, @Param("student") StudentEntity student, @Param("course") CourseEntity course, @Param("mark") Integer mark);

    @Query("from StudentCourseMarkEntity where id=:id")
    Optional<StudentCourseMarkEntity> getById(@Param("id") Integer id);

    @Query("delete from StudentCourseMarkEntity where id=:id")
    void deleteById(@Param("id") Integer id);

    @Query("from StudentCourseMarkEntity")
    Iterable<StudentCourseMarkEntity> getAll();

    @Query("from StudentCourseMarkEntity where student.id=:id and createdDate>=:from and createdDate<=:to")
    List<StudentCourseMarkEntity> findByStudentAndCreatedDateBetween(@Param("id") Integer id, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("from StudentCourseMarkEntity where student.id=:id order by createdDate desc ")
    List<StudentCourseMarkEntity> findByIdOrderByCreatedDateAsc(@Param("id") Integer id);

    @Query("from StudentCourseMarkEntity where student.id=:studentId and course.id=:courseId order by createdDate desc ")
    List<StudentCourseMarkEntity> findByStudentIdAndCourseIdSortDate(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    Optional<StudentCourseMarkEntity> findFirstByIdOrderByCreatedDateAsc(Integer id);

    List<StudentCourseMarkEntity> findTop3ByStudentOrderByMarkDesc(StudentEntity student);

    Optional<StudentCourseMarkEntity> findFirstByStudentOrderByCreatedDateAsc(StudentEntity student);

    Optional<StudentCourseMarkEntity> findFirstByStudentAndCourseOrderByCreatedDateAsc(StudentEntity student, CourseEntity course);

    Optional<StudentCourseMarkEntity> findFirstByStudentAndCourseOrderByMarkAsc(StudentEntity student, CourseEntity course);

    @Query("select new com.example.mapper.StudentAverageMark(s.course.id,avg(s.mark)) from StudentCourseMarkEntity as s " +
            "where s.student.id=:student group by s.course")
    List<StudentAverageMark> findAverageMark(@Param("student") Integer student);

    @Query("select new com.example.mapper.StudentAverageMark(s.course.id,avg(s.mark)) from StudentCourseMarkEntity as s " +
            "where s.student.id=:student and s.course.id=:course")
    Optional<StudentAverageMark> findAverageMarkByCourse(@Param("student") Integer student, @Param("course") Integer course);

    Long countByStudentAndMarkGreaterThan(StudentEntity student, Integer mark);

    Optional<StudentCourseMarkEntity> findTopByCourseOrderByMarkAsc(CourseEntity course);

    @Query("select new com.example.mapper.CourseAverageMark(s.course.id,avg(s.mark)) from StudentCourseMarkEntity as s " +
            "where s.course.id=:course")
    Optional<CourseAverageMark> findByCourseAverageMark(Integer course);

    Long countByCourse(CourseEntity course);

    Page<StudentCourseMarkEntity> findByStudent(StudentEntity entity, Pageable pageable);

    Page<StudentCourseMarkEntity> findByCourse(CourseEntity entity, Pageable pageable);
}
