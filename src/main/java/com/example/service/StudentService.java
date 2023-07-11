package com.example.service;

import com.example.dto.StudentDTO;
import com.example.entity.StudentEntity;
import com.example.enums.Gender;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public StudentDTO save(StudentDTO dto) {
        check(dto);
        StudentEntity entity = new StudentEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setLevel(dto.getLevel());
        entity.setCreatedDate(LocalDateTime.now());
        studentRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> list = new LinkedList<>();
        iterable.forEach(item -> {
            list.add(toDTO(item));
        });
        if (list.isEmpty()) throw new ItemNotFoundException("Student not found");
        return list;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        return optional.map(this::toDTO).orElseThrow(() -> new ItemNotFoundException("Student not found"));

    }

    public void update(Integer id, StudentDTO dto) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Student not found");
        }
        StudentEntity entity = optional.get();
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getSurname() != null) entity.setSurname(dto.getSurname());
        if (dto.getAge() != null) entity.setAge(dto.getAge());
        if (dto.getLevel() != null) entity.setLevel(dto.getLevel());
        if (dto.getGender() != null) entity.setGender(dto.getGender());
        studentRepository.save(entity);

    }

    public void delete(Integer id) {
        getById(id);
        studentRepository.deleteById(id);
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
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        return toLIST(studentRepository.getByCreatedDateBetween(from, to));
    }

    public List<StudentDTO> getByBetweenDate(LocalDate fromDate, LocalDate toDate) {
        return toLIST(studentRepository.getByCreatedDateBetween(LocalDateTime.of(fromDate,
                LocalTime.MIN), LocalDateTime.of(toDate, LocalTime.MAX)));
    }

    public Page<StudentEntity> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findAll(pageable);
    }

    public Page<StudentEntity> getPaginationByLevel(int page, Integer level) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByLevelOrderByIdDesc(pageable, level);
    }


    public Page<StudentEntity> getPaginationByGender(int page, Gender gender) {
        Pageable pageable = PageRequest.of(page, 10);
        return studentRepository.findAllByGenderOrderByIdDesc(pageable, gender);
    }


//    public Page<StudentEntity> getPaginationFilter(int page, Gender gender) {
//        Pageable pageable = PageRequest.of(page, 10);
//        return studentRepository.findAllByGenderOrderByIdDesc(pageable, gender);
//    }








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
