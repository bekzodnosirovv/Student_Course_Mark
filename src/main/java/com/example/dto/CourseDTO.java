package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class CourseDTO {

    private Integer id;
    private String name;
    private Double price;
    private Integer duration;
    private LocalDateTime createdDate;



}
