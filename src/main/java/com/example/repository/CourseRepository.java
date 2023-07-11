package com.example.repository;

import com.example.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends CrudRepository<CourseEntity, Integer>,
        PagingAndSortingRepository<CourseEntity, Integer> {
    List<CourseEntity> getByName(String name);

    List<CourseEntity> getByPrice(Double price);

    List<CourseEntity> getByDuration(Integer duration);

    List<CourseEntity> getByPriceBetween(Double price1, Double prise2);

    List<CourseEntity> getByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Page<CourseEntity> findByOrderByCreatedDate(Pageable pageable);
    Page<CourseEntity> findAllByPriceOrderByCreatedDateDesc(Pageable pageable,Double price);
    Page<CourseEntity> findAllByPriceBetweenOrderByCreatedDateDesc(Pageable pageable,Double from,Double to);


}
