package com.example.repository;

import com.example.entity.CourseEntity;
import com.example.entity.StudentEntity;
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

public interface CourseRepository extends CrudRepository<CourseEntity, Integer>,
        PagingAndSortingRepository<CourseEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into course(name,price,duration,created_date) " +
            " values (:name,:price,:duration,:createdDate)", nativeQuery = true)
    void saveQuery(@Param("name") String name, @Param("price") Double price, @Param("duration")
    Integer duration, @Param("createdDate") LocalDateTime createdDate);

    @Query("from CourseEntity where id=:id")
    Optional<CourseEntity> getById(@Param("id") Integer id);

    @Query("from CourseEntity ")
    List<CourseEntity> getAll();

    @Transactional
    @Modifying
    @Query("update CourseEntity set name=:name,price=:price,duration=:duration where id=:id")
    void update(@Param("id") Integer id, @Param("name") String name, @Param("price") Double price,
                @Param("duration") Integer duration);

    @Transactional
    @Modifying
    @Query("delete from CourseEntity where id=:id")
    void delete(@Param("id") Integer id);

    @Query("from CourseEntity where name=:name")
    List<CourseEntity> getByName(@Param("name") String name);

    @Query("from CourseEntity where price=:price")
    List<CourseEntity> getByPrice(@Param("price") Double price);

    @Query("from CourseEntity where duration=:duration")
    List<CourseEntity> getByDuration(@Param("duration") Integer duration);

    @Query("from CourseEntity where price between :price1 and :price2")
    List<CourseEntity> getByPriceBetween(@Param("price1") Double price1, @Param("price2") Double prise2);

    @Query("from CourseEntity where createdDate between :fromDate and :toDate")
    List<CourseEntity> getByCreatedDateBetween(@Param("fromDate") LocalDateTime fromDate,
                                               @Param("toDate") LocalDateTime toDate);

    Page<CourseEntity> findByOrderByCreatedDate(Pageable pageable);

    Page<CourseEntity> findAllByPrice(Pageable pageable, Double price);

    Page<CourseEntity> findAllByPriceBetween(Pageable pageable, Double from, Double to);


}
