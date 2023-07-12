package com.example.service;

import com.example.dto.StudentCourseMarkDto;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.StudentCourseMapper;
import com.example.mapper.StudentMarkDTO;
import com.example.repository.CourseRepository;
import com.example.repository.StudentCourseMarkRepository;
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
import java.util.stream.Collectors;

@Service
public class StudentCourseMarkService {

    @Autowired
    private StudentCourseMarkRepository repository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    public void save(StudentCourseMarkDto dto) {
        check(dto);
        studentRepository.getById(dto.getStudentId());
        courseRepository.getById(dto.getCourseId());
        repository.saveQuery(dto.getStudentId(), dto.getCourseId(), dto.getMark(), LocalDateTime.now());

    }

    public void update(StudentCourseMarkDto dto, Integer id) {
        Optional<StudentCourseMarkEntity> optional = repository.getById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        StudentEntity student = new StudentEntity();
        CourseEntity course = new CourseEntity();
        Integer mark;

        if (dto.getStudentId() != null) student.setId(dto.getStudentId());
        else student.setId(optional.get().getStudent().getId());
        if (dto.getCourseId() != null) course.setId(dto.getCourseId());
        else course.setId(optional.get().getCourse().getId());
        if (dto.getMark() != null) mark = dto.getMark();
        else mark = optional.get().getMark();

        repository.update(id, student, course, mark);

    }

    public StudentCourseMarkDto getById(Integer id) {
        Optional<StudentCourseMarkEntity> optional = repository.getById(id);
        return optional.map(this::toDTO).orElseThrow(() -> new ItemNotFoundException("StudentCourseMark not found"));
    }

    public StudentCourseMapper getByIdDetail(Integer id) {
        Optional<StudentCourseMarkEntity> optional = repository.getById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("StudentCourseMark not found");
        }
        StudentCourseMarkEntity entity = optional.get();
        StudentCourseMapper mapper = new StudentCourseMapper();
        mapper.setId(entity.getId());
        mapper.setStudentId(entity.getStudent().getId());
        mapper.setStudentName(entity.getStudent().getName());
        mapper.setStudentSurname(entity.getStudent().getSurname());
        mapper.setCourseId(entity.getCourse().getId());
        mapper.setMark(entity.getMark());
        mapper.setCourseName(entity.getCourse().getName());
        mapper.setCreatedDate(entity.getCreatedDate());
        return mapper;
    }

    public void delete(Integer id) {
        getById(id);
        repository.deleteById(id);
    }

    public List<StudentCourseMarkDto> getAll() {
        Iterable<StudentCourseMarkEntity> iterable = repository.getAll();
        List<StudentCourseMarkDto> list = new LinkedList<>();
        iterable.forEach(entity -> list.add(toDTO(entity)));
        if (list.isEmpty()) throw new ItemNotFoundException("StudentCourseMark not found.");
        return list;
    }

    public List<StudentCourseMarkDto> getByDateMarkBetween(Integer id, LocalDate from, LocalDate to) {
        LocalDateTime from1 = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime to1 = LocalDateTime.of(to, LocalTime.MAX);
        List<StudentCourseMarkEntity> entityList = repository.findByStudentAndCreatedDateBetween(id, from1, to1);
        if (entityList.isEmpty()) throw new ItemNotFoundException("StudentCourseMark not.");
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<StudentCourseMarkDto> getMarkSortDate(Integer id) {
        List<StudentCourseMarkEntity> list = repository.findByIdOrderByCreatedDateAsc(id);
        List<StudentCourseMarkDto> list1 = list.stream().map(this::toDTO).toList();
        if (list1.isEmpty()) throw new ItemNotFoundException("StudentCourseMark not found.");
        return list1;
    }

    public List<StudentCourseMarkDto> getByCourseMarkSortDate(Integer studentId, Integer courseId) {
        List<StudentCourseMarkEntity> list = repository.findByStudentIdAndCourseIdSortDate(studentId, courseId);
        List<StudentCourseMarkDto> list1 = list.stream().map(this::toDTO).toList();
        if (list1.isEmpty()) throw new ItemNotFoundException("StudentCourseMark not found.");
        return list1;
    }

    public StudentMarkDTO getLastMark(Integer id) {
        Optional<StudentCourseMarkEntity> optional = repository.findFirstByIdOrderByCreatedDateAsc(id);
        return optional.map(entity -> {
            StudentLastMark mark = new StudentLastMark();
            mark.setMark(entity.getMark());
            mark.setCourse(entity.getCourse().getName());
            return mark;
        }).orElseThrow(() -> new ItemNotFoundException("StudentCourseMark not found"));
    }

    public StudentLastMark getBigMark(Integer id) {
        StudentEntity entity = new StudentEntity();
        entity.setId(id);
        List<StudentCourseMarkEntity> list = repository.findTop3ByStudentOrderByMarkDesc(entity);
        if (list.isEmpty()) throw new ItemNotFoundException("Student Course Mark not found.");
        return (StudentLastMark) list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public StudentCourseMarkDto getFirstMark(Integer id) {
        StudentEntity entity = new StudentEntity();
        entity.setId(id);
        Optional<StudentCourseMarkEntity> optional = repository.findFirstByStudentOrderByCreatedDateAsc(entity);
        if (optional.isEmpty()) throw new ItemNotFoundException("The student has not received a grade yet");
        return toDTO(optional.get());
    }

    public StudentCourseMarkDto getByCourseFirstMark(Integer course, Integer id) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(id);
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(course);
        Optional<StudentCourseMarkEntity> optional = repository.findFirstByStudentAndCourseOrderByCreatedDateAsc(studentEntity, courseEntity);
        if (optional.isEmpty()) throw new ItemNotFoundException("The student has not received a grade yet");
        return toDTO(optional.get());
    }

    public StudentCourseMarkDto getByCourseBigMark(Integer course, Integer id) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(id);
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(course);
        Optional<StudentCourseMarkEntity> optional = repository.findFirstByStudentAndCourseOrderByMarkAsc(studentEntity, courseEntity);
        if (optional.isEmpty()) throw new ItemNotFoundException("The student has not received a grade yet");
        return toDTO(optional.get());
    }

    public List<StudentAverageMark> getAverageMark(Integer id) {
        List<StudentAverageMark> list = repository.findAverageMark(id);
        if (list.isEmpty()) throw new ItemNotFoundException("no ratings yet");
        return list;
    }

    public StudentAverageMark getAverageMarkByCourse(Integer student, Integer course) {
        Optional<StudentAverageMark> optional = repository.findAverageMarkByCourse(student, course);
        if (optional.isEmpty()) throw new ItemNotFoundException("no ratings yet");
        return optional.get();

    }

    public Long getByMarkBigCount(Integer student, Integer mark) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(student);
        return repository.countByStudentAndMarkGreaterThan(studentEntity, mark);
    }

    public CourseMark getCourseBigMark(Integer course) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(course);
        Optional<StudentCourseMarkEntity> optional = repository.findTopByCourseOrderByMarkAsc(courseEntity);
        if (optional.isEmpty()) throw new ItemNotFoundException("Item not found");
        return new CourseMark(optional.get().getCourse().getId(), optional.get().getMark());
    }

    public CourseAverageMark getCourseAverageMark(Integer course) {
        Optional<CourseAverageMark> optional = repository.findByCourseAverageMark(course);
        if (optional.isEmpty()) throw new ItemNotFoundException("Item not found");
        return optional.get();
    }

    public Long getByCourseMarkCount(Integer course) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId(course);
        return repository.countByCourse(courseEntity);
    }

    public PageImpl<StudentCourseMarkDto> getPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentCourseMarkEntity> pageObj = repository.findAll(pageable);

        List<StudentCourseMarkDto> studentDTOList = new LinkedList<>();
        pageObj.getContent().forEach(entity -> {
            studentDTOList.add(toDTO(entity));
        });

        return new PageImpl<StudentCourseMarkDto>(studentDTOList, pageable, pageObj.getTotalElements());

    }

    public PageImpl<StudentCourseMarkDto> getPaginationByStudentId(Integer studentId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        StudentEntity entity = new StudentEntity();
        entity.setId(studentId);

        Page<StudentCourseMarkEntity> pageObj = repository.findByStudent(entity, pageable);

        List<StudentCourseMarkDto> studentDTOList = new LinkedList<>();
        pageObj.getContent().forEach(item -> {
            studentDTOList.add(toDTO(item));
        });

        return new PageImpl<StudentCourseMarkDto>(studentDTOList, pageable, pageObj.getTotalElements());
    }

    public PageImpl<StudentCourseMarkDto> getPaginationByCourseId(Integer courseId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        CourseEntity entity = new CourseEntity();
        entity.setId(courseId);

        Page<StudentCourseMarkEntity> pageObj = repository.findByCourse(entity, pageable);

        List<StudentCourseMarkDto> studentDTOList = new LinkedList<>();
        pageObj.getContent().forEach(item -> {
            studentDTOList.add(toDTO(item));
        });

        return new PageImpl<StudentCourseMarkDto>(studentDTOList, pageable, pageObj.getTotalElements());
    }

    private StudentCourseMarkDto toDTO(StudentCourseMarkEntity entity) {
        StudentCourseMarkDto dto = new StudentCourseMarkDto();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setCourseId(entity.getCourse().getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private void check(StudentCourseMarkDto dto) {
        if (dto.getStudentId() == null) {
            throw new AppBadRequestException("No student id ?");
        }
        if (dto.getCourseId() == null) {
            throw new AppBadRequestException("No course id ?");
        }
        if (dto.getMark() == null) {
            throw new AppBadRequestException("No mark ?");
        }
    }

}
