package com.example.service;

import com.example.dto.CourseDTO;
import com.example.dto.CourseFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.CourseEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CourseRepository;
import com.example.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    @Autowired
    private FilterRepository filterRepository;

    public void save(CourseDTO dto) {
        check(dto);
        courseRepository.saveQuery(dto.getName(), dto.getPrice(), dto.getDuration(), LocalDateTime.now());
    }

    public List<CourseDTO> getAll() {
        Iterable<CourseEntity> iterable = courseRepository.getAll();
        List<CourseDTO> list = new LinkedList<>();
        iterable.forEach(item -> {
            list.add(toDTO(item));
        });
        if (list.isEmpty()) throw new ItemNotFoundException("Course not found.");
        return list;
    }

    public CourseDTO getById(Integer id) {
        Optional<CourseEntity> optional = courseRepository.getById(id);
        return optional.map(this::toDTO).orElseThrow(() -> new ItemNotFoundException("Course not found"));
    }

    public void update(Integer id, CourseDTO dto) {
        Optional<CourseEntity> optional = courseRepository.getById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Course not found");
        }
        courseRepository.update(id, dto.getName(), dto.getPrice(), dto.getDuration());
    }

    public void delete(Integer id) {
        getById(id);
        courseRepository.delete(id);
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


    public List<CourseDTO> getByPriceBetween(Double price1, Double price2) {
        return toLIST(courseRepository.getByPriceBetween(price1, price2));
    }

    public List<CourseDTO> getByBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return toLIST(courseRepository.getByCreatedDateBetween(fromDate.atStartOfDay(),
                toDate.plusDays(1).atStartOfDay()));
    }

    public PageImpl<CourseDTO> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEntity> entityPage = courseRepository.findAll(pageable);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }

    public PageImpl<CourseDTO> getPaginationSortCreatedDate(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CourseEntity> entityPage = courseRepository.findAll(pageable);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }


    public PageImpl<CourseDTO> getPaginationByPrice(int page, int size, Double price) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CourseEntity> entityPage = courseRepository.findAllByPrice(pageable, price);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }

    public PageImpl<CourseDTO> getPaginationByPriceBetween(int page, int size, Double from, Double to) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CourseEntity> entityPage = courseRepository.findAllByPriceBetween(pageable, from, to);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }

    public PageImpl<?> getPaginationFilter(int page, int size, CourseFilterDTO filter) {
        FilterResultDTO<CourseEntity> filterDTO = (FilterResultDTO<CourseEntity>) filterRepository.filterCourse(filter, page, size);
        return new PageImpl<>(filterDTO.getList().stream().map(this::toDTO).toList(), PageRequest.of(page, size),
                filterDTO.getTotalCount());
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
