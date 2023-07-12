package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudentCourseMapper {
    private Integer id;
    private Integer studentId;
    private String studentName;
    private String studentSurname;
    private Integer courseId;
    private String courseName;
    private Integer mark;
    private LocalDateTime createdDate;
}
