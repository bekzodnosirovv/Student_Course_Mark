package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentAverageMarkDTO {
    private String studentName;
    private String courseName;
    private Double mark;

    public StudentAverageMarkDTO(String studentName, String courseName, Double mark) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.mark = mark;
    }
}
