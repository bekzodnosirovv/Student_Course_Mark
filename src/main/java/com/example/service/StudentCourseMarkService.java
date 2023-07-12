package com.example.service;

import com.example.dto.FilterResultDTO;
import com.example.dto.StudentCourseMarkDto;
import com.example.dto.StudentFilterDTO;
import com.example.entity.CourseEntity;
import com.example.entity.StudentCourseMarkEntity;
import com.example.entity.StudentEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.*;
import com.example.repository.CourseRepository;
import com.example.repository.FilterRepository;
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
    @Autowired
    private FilterRepository filterRepository;


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
        if (entityList.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<StudentCourseMarkDto> getMarkSortDate(Integer id) {
        List<StudentCourseMarkEntity> list = repository.findByIdOrderByCreatedDateAsc(id);
        List<StudentCourseMarkDto> list1 = list.stream().map(this::toDTO).toList();
        if (list1.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return list1;
    }

    public List<StudentCourseMarkDto> getByCourseMarkSortDate(Integer studentId, Integer courseId) {
        List<StudentCourseMarkEntity> list = repository.findByStudentIdAndCourseIdSortDate(studentId, courseId);
        List<StudentCourseMarkDto> list1 = list.stream().map(this::toDTO).toList();
        if (list1.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return list1;
    }

    public StudentMarkDTO getLastMark(Integer id) {
        Optional<StudentCourseMarkEntity> optional = repository.getLastMark(id);
        return optional.map(entity -> {
            StudentMarkDTO mark = new StudentMarkDTO();
            mark.setStudentName(entity.getStudent().getName());
            mark.setCourseName(entity.getCourse().getName());
            mark.setMark(entity.getMark());
            return mark;
        }).orElseThrow(() -> new ItemNotFoundException("This student has not yet received a grade."));
    }

    public List<StudentMarkDTO> getBigMark(Integer id) {
        List<StudentCourseMarkEntity> list = repository.getBig3Mark(id);
        if (list.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return list.stream().map(entity -> {
            StudentMarkDTO mark = new StudentMarkDTO();
            mark.setStudentName(entity.getStudent().getName());
            mark.setCourseName(entity.getCourse().getName());
            mark.setMark(entity.getMark());
            return mark;
        }).collect(Collectors.toList());
    }

    public StudentMarkDTO getFirstMark(Integer id) {
        Optional<StudentCourseMarkEntity> optional = repository.getFirstMark(id);
        if (optional.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        StudentMarkDTO studentMarkDTO = new StudentMarkDTO();
        studentMarkDTO.setStudentName(optional.get().getStudent().getName());
        studentMarkDTO.setCourseName(optional.get().getCourse().getName());
        studentMarkDTO.setMark(optional.get().getMark());
        return studentMarkDTO;
    }

    public StudentMarkDTO getByCourseFirstMark(Integer studentId, Integer courseId) {
        Optional<StudentCourseMarkEntity> optional = repository.getByCourseFirstMark(studentId, courseId);
        if (optional.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        StudentMarkDTO studentMarkDTO = new StudentMarkDTO();
        studentMarkDTO.setStudentName(optional.get().getStudent().getName());
        studentMarkDTO.setCourseName(optional.get().getCourse().getName());
        studentMarkDTO.setMark(optional.get().getMark());
        return studentMarkDTO;
    }

    public StudentMarkDTO getByCourseBigMark(Integer studentId, Integer courseId) {
        Optional<StudentCourseMarkEntity> optional = repository.getByCourseBigMark(studentId, courseId);
        if (optional.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        StudentMarkDTO studentMarkDTO = new StudentMarkDTO();
        studentMarkDTO.setStudentName(optional.get().getStudent().getName());
        studentMarkDTO.setCourseName(optional.get().getCourse().getName());
        studentMarkDTO.setMark(optional.get().getMark());
        return studentMarkDTO;
    }

    public List<StudentAverageMarkDTO> getAverageMark(Integer id) {
        List<StudentAverageMarkDTO> list = repository.getAverageMark(id);
        if (list.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return list;
    }

    public StudentAverageMarkDTO getAverageMarkByCourse(Integer student, Integer course) {
        Optional<StudentAverageMarkDTO> optional = repository.getByCourseAverageMark(student, course);
        if (optional.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return optional.get();

    }

    public int getByMarkBigCount(Integer student, Integer mark) {
        List<StudentCourseMarkEntity> list = repository.countMark(student, mark);
        if (list.isEmpty())
            throw new ItemNotFoundException("The student did not receive a higher grade than the given grade.");
        return list.size();
    }

    public CourseMarkDTO getCourseBigMark(Integer courseId) {
        Optional<StudentCourseMarkEntity> optional = repository.getByCourseBigMark(courseId);
        if (optional.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        CourseMarkDTO markDTO = new CourseMarkDTO();
        markDTO.setCourseName(optional.get().getCourse().getName());
        markDTO.setMark(optional.get().getMark());
        return markDTO;
    }

    public CourseAverageMarkDTO getCourseAverageMark(Integer course) {
        Optional<CourseAverageMarkDTO> optional = repository.getCourseAverageMark(course);
        if (optional.isEmpty()) throw new ItemNotFoundException("This student has not yet received a grade.");
        return optional.get();
    }

    public int getByCourseMarkCount(Integer course) {
        List<StudentCourseMarkEntity> list = repository.getByCourseCount(course);
        if (list.isEmpty()) throw new ItemNotFoundException("No grades have been taken from this course.");
        return list.size();
    }

    public PageImpl<StudentCourseMarkDto> getPagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentCourseMarkEntity> pageObj = repository.findAll(pageable);

        List<StudentCourseMarkDto> studentDTOList = new LinkedList<>();
        pageObj.getContent().forEach(entity -> {
            studentDTOList.add(toDTO(entity));
        });

        return new PageImpl<>(studentDTOList, pageable, pageObj.getTotalElements());

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

        return new PageImpl<>(studentDTOList, pageable, pageObj.getTotalElements());
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

        return new PageImpl<>(studentDTOList, pageable, pageObj.getTotalElements());
    }

    public FilterResultDTO<?> getPaginationFilter(int page, int size, StudentFilterDTO filter) {
        FilterResultDTO<StudentCourseMarkEntity> filterDTO = (FilterResultDTO<StudentCourseMarkEntity>) filterRepository.filterStudentCourseMark(filter, page, size);
        if (filterDTO.getTotalCount() == 0) throw new ItemNotFoundException("StudentCourseMark not found.");
        return new FilterResultDTO<>(filterDTO.getList().stream().map(this::toDTO).toList(), filterDTO.getTotalCount());
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
