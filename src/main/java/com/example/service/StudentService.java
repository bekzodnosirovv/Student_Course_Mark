package com.example.service;

import com.example.dto.FilterResultDTO;
import com.example.dto.StudentDTO;
import com.example.dto.StudentFilterDTO;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.FilterRepository;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FilterRepository filterRepository;

    public void save(StudentDTO dto) {
        check(dto);
        studentRepository.saveQuery(dto.getName(), dto.getSurname(), dto.getLevel(), dto.getAge(),
                dto.getGender().toString(), LocalDateTime.now());
    }

    public List<StudentDTO> getAll() {
        List<StudentEntity> entityList = studentRepository.getAll();
        List<StudentDTO> list = new LinkedList<>();
        entityList.forEach(item -> {
            list.add(toDTO(item));
        });
        if (list.isEmpty()) throw new ItemNotFoundException("Student not found");
        return list;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.getById(id);
        return optional.map(this::toDTO).orElseThrow(() -> new ItemNotFoundException("Student not found"));

    }

    public void update(Integer id, StudentDTO dto) {
        Optional<StudentEntity> optional = studentRepository.getById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Student not found");
        }
        studentRepository.update(id, dto.getName(), dto.getSurname(), dto.getLevel(), dto.getAge());
    }

    public void delete(Integer id) {
        getById(id);
        studentRepository.delete(id);
    }

    public List<StudentDTO> getByName(String name) {
        return toLIST(studentRepository.getByName(name));
    }

    public List<StudentDTO> getBySurname(String surname) {
        return toLIST(studentRepository.getBySurname(surname));
    }

    public List<StudentDTO> getByLevel(Integer level) {
        return toLIST(studentRepository.getByLevel(level));
    }

    public List<StudentDTO> getByAge(Integer age) {
        return toLIST(studentRepository.getByAge(age));
    }

    public List<StudentDTO> getByGender(Gender gender) {
        return toLIST(studentRepository.getByGender(gender));
    }

    public List<StudentDTO> getByDate(LocalDate date) {
        return toLIST(studentRepository.getByCreatedDateBetween(LocalDateTime.of(date,
                LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX)));
    }


    public List<StudentDTO> getByBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return toLIST(studentRepository.getByCreatedDateBetween(LocalDateTime.of(fromDate,
                LocalTime.MIN), LocalDateTime.of(toDate, LocalTime.MAX)));
    }

    public PageImpl<StudentDTO> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentEntity> entityPage = studentRepository.findAll(pageable);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());
    }

    public Page<StudentDTO> getPaginationByLevel(int page, int size, Integer level) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<StudentEntity> entityPage = studentRepository.findAllByLevel(pageable, level);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());

    }


    public PageImpl<StudentDTO> getPaginationByGender(int page, int size, Gender gender) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdDate").descending());
        Page<StudentEntity> entityPage = studentRepository.findAllByGender(pageable, gender);
        return new PageImpl<>(entityPage.getContent().stream().map(this::toDTO).toList(), pageable, entityPage.getTotalElements());

    }

    public PageImpl<?> getPaginationFilter(int page, int size, StudentFilterDTO filter) {
        FilterResultDTO<StudentEntity> filterDTO = (FilterResultDTO<StudentEntity>) filterRepository.filterStudent(filter, page, size);
        return new PageImpl<>(filterDTO.getList().stream().map(this::toDTO).toList(), PageRequest.of(page, size),
                filterDTO.getTotalCount());
    }


    private List<StudentDTO> toLIST(List<StudentEntity> list) {
        if (list.isEmpty()) throw new ItemNotFoundException("Student not found");
        List<StudentDTO> dtoList = new LinkedList<>();
        list.forEach(item -> {
            dtoList.add(toDTO(item));
        });
        return dtoList;
    }

    private StudentDTO toDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setLevel(entity.getLevel());
        dto.setGender(entity.getGender());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private void check(StudentDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new AppBadRequestException("No name ?");
        }
        if (dto.getSurname() == null || dto.getSurname().isBlank()) {
            throw new AppBadRequestException("No surname ?");
        }
        if (dto.getAge() == null) {
            throw new AppBadRequestException("No age ?");
        }
        if (dto.getGender() == null || !dto.getGender().equals(Gender.MALE) && !dto.getGender().equals(Gender.FEMALE)) {
            throw new AppBadRequestException("No gender !");
        }
        if (dto.getLevel() == null) {
            throw new AppBadRequestException("No level ?");
        }
    }
}
