package com.example.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentMarkDTO {
    private String studentName;
    private String courseName;
    private Integer mark;
}
