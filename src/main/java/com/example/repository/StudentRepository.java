package com.example.repository;

import com.example.entity.StudentEntity;
import com.example.enums.Gender;
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

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>,
        PagingAndSortingRepository<StudentEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "insert into student(name,surname,level,age,gender,created_date) " +
            " values (:name,:surname,:level,:age,:gender,:createdDate)", nativeQuery = true)
    void saveQuery(@Param("name") String name, @Param("surname") String surname, @Param("level")
    Integer level, @Param("age") Integer age, @Param("gender") String gender, @Param("createdDate") LocalDateTime dateTime);

    @Query("from StudentEntity ")
    List<StudentEntity> getAll();

    @Query("from StudentEntity where id=:id")
    Optional<StudentEntity> getById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update StudentEntity set name=:name,surname=:surname,level=:level,age=:age where id=:id")
    void update(@Param("id") Integer id, @Param("name") String name, @Param("surname") String surname,
                @Param("level") Integer level, @Param("age") Integer age);

    @Transactional
    @Modifying
    @Query("delete from StudentEntity where id=:id")
    void delete(@Param("id") Integer id);

    @Query("from StudentEntity where name=:name")
    List<StudentEntity> getByName(@Param("name") String name);

    @Query("from StudentEntity where surname=:surname")
    List<StudentEntity> getBySurname(@Param("surname") String surname);

    @Query("from StudentEntity where age=:age")
    List<StudentEntity> getByAge(@Param("age") Integer age);

    @Query("from StudentEntity where level=:level")
    List<StudentEntity> getByLevel(@Param("level") Integer level);

    @Query("from StudentEntity where gender=:gender")
    List<StudentEntity> getByGender(@Param("gender") Gender gender);

    @Query("from StudentEntity where createdDate between :fromDate and :toDate")
    List<StudentEntity> getByCreatedDateBetween(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);

    Page<StudentEntity> findAllByLevel(Pageable pageable, Integer level);

    Page<StudentEntity> findAllByGender(Pageable pageable, Gender gender);


}
