package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseAverageMarkDTO {
    private String courseName;
    private Double averageMark;

    public CourseAverageMarkDTO(String courseName, Double averageMark) {
        this.courseName = courseName;
        this.averageMark = averageMark;
    }
}
