package com.example.repository;

import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.mapper.CourseAverageMark;
import com.example.mapper.StudentAverageMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StudentCourseMarkRepository extends CrudRepository<StudentCourseMarkEntity, Integer>,
        PagingAndSortingRepository<StudentCourseMarkEntity, Integer> {
    List<StudentCourseMarkEntity> findByStudentAndCreatedDateBetween(StudentEntity student, LocalDateTime from, LocalDateTime to);

    List<StudentCourseMarkEntity> findByIdOrderByCreatedDateAsc(Integer id);

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
