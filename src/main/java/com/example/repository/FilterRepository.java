package com.example.repository;

import com.example.entity.StudentEntity;
import com.example.mapper.StudentFilterDTO;
import com.example.mapper.StudentFilterResultDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FilterRepository {
    @Autowired
    private EntityManager entityManager;

    public StudentFilterResultDTO<StudentEntity> filterStudent(StudentFilterDTO filterDTO, int page, int size) {

        StringBuilder selectQueryBuilder = new StringBuilder("select s from StudentEntity as s where 1=1");
        StringBuilder countQueryBuilder = new StringBuilder("select count(s) from StudentEntity as s where 1=1");
        StringBuilder whereQuery = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filterDTO.getName() != null) {
            whereQuery.append(" and s.name =:name");
            params.put("name", filterDTO.getName());
        }
        if (filterDTO.getSurname() != null) {
            whereQuery.append(" and s.surname =:surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getAge() != null) {
            whereQuery.append(" and s.age =:age");
            params.put("age", filterDTO.getAge());
        }
        if (filterDTO.getLevel() != null) {
            whereQuery.append(" and s.level =:level");
            params.put("level", filterDTO.getLevel());
        }
        if (filterDTO.getGender() != null) {
            whereQuery.append(" and s.gender =:gender");
            params.put("gender", filterDTO.getGender());
        }
        if (filterDTO.getCreatedDateFrom() != null) {
            whereQuery.append(" and s.createdDate >=:fromDate");
            params.put("fromDate", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        Query selectQuery = entityManager.createQuery(selectQueryBuilder.append(whereQuery).toString());
        selectQuery.setFirstResult(page * size);
        selectQuery.setMaxResults(size);

        Query countQuery = entityManager.createQuery(countQueryBuilder.append(whereQuery).toString());


        if (filterDTO.getCreatedDateTo() != null) {
            whereQuery.append(" and s.createdDate <=:ToDate");
            params.put("ToDate", LocalDateTime.of(filterDTO.getCreatedDateTo(), LocalTime.MAX));
        }


        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<StudentEntity> entityList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new StudentFilterResultDTO<>(entityList,totalCount);
    }
}
