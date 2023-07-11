package com.example.service;

import com.example.dto.CourseDTO;
import com.example.entity.CourseEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO save(CourseDTO dto) {
        check(dto);
        CourseEntity entity = new CourseEntity();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setCreatedDate(LocalDateTime.now());
        courseRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.findAll();
        List<CourseDTO> list = new LinkedList<>();
        iterable.forEach(item -> {
            list.add(toDTO(item));
        });
        if (list.isEmpty()) throw new ItemNotFoundException("Course not found.");
        return list;
    }

    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        return optional.map(this::toDTO).orElseThrow(() -> new ItemNotFoundException("Course not found"));
    }

    public void update(Integer id, CourseDTO dto) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Course not found");
        }
        CourseEntity entity = optional.get();
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getDuration() != null) entity.setDuration(dto.getDuration());
        courseRepository.save(entity);
    }

    public void delete(Integer id) {
        getById(id);
        courseRepository.deleteById(id);
    }

    public List<CourseDTO> getByName(String name) {
        return toLIST(courseRepository.getByName(name));
    }

    public List<CourseDTO> getByPrice(Double price) {
        return toLIST(courseRepository.getByPrice(price));
    }

    public List<CourseDTO> getByDuration(Integer duration) {
        return toLIST(courseRepository.getByDuration(duration));
    }

    public List<CourseDTO> getByBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return toLIST(courseRepository.getByCreatedDateBetween(fromDate.atStartOfDay(),
                toDate.plusDays(1).atStartOfDay()));
    }

    public List<CourseDTO> getByPriceBetween(Double price1, Double price2) {
        return toLIST(courseRepository.getByPriceBetween(price1, price2));
    }

    public Page<CourseEntity> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(pageable);
    }

    public Page<CourseEntity> getPaginationSortCreatedDate(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return courseRepository.findByOrderByCreatedDate(pageable);
    }


    public Page<CourseEntity> getPaginationByPrice(int page, Double price) {
        Pageable pageable = PageRequest.of(page, 10);
        return courseRepository.findAllByPriceOrderByCreatedDateDesc(pageable, price);
    }

    public Page<CourseEntity> getPaginationByPriceBetween(int page, Double from, Double to) {
        Pageable pageable = PageRequest.of(page, 10);
        return courseRepository.findAllByPriceBetweenOrderByCreatedDateDesc(pageable, from, to);
    }




    private List<CourseDTO> toLIST(List<CourseEntity> list) {
        if (list.isEmpty()) throw new ItemNotFoundException("Course not found");
        List<CourseDTO> dtoList = new LinkedList<>();
        list.forEach(item -> {
            dtoList.add(toDTO(item));
        });
        return dtoList;
    }

    private CourseDTO toDTO(CourseEntity entity) {
        CourseDTO dto = new CourseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private void check(CourseDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("No name ?");
        }
        if (dto.getPrice() == null) {
            throw new AppBadRequestException("No price ?");
        }
        if (dto.getDuration() == null) {
            throw new AppBadRequestException("No duration ?");
        }
    }


}
