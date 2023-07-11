package com.example.repository;

import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>,
        PagingAndSortingRepository<StudentEntity, Integer> {
    @Query("")
    Long add(@Param("entity") StudentEntity entity);

    List<StudentEntity> getByName(String name);

    List<StudentEntity> getBySurname(String surname);

    List<StudentEntity> getByAge(Integer age);

    List<StudentEntity> getByLevel(Integer level);

    List<StudentEntity> getByGender(Gender gender);

    List<StudentEntity> getByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<StudentEntity> findAllByLevelOrderByIdDesc(Pageable pageable, Integer level);

    Page<StudentEntity> findAllByGenderOrderByIdDesc(Pageable pageable, Gender gender);


}
