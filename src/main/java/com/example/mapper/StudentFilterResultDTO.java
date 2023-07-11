package com.example.mapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentFilterResultDTO<T> {

    private List<T> list;
    private Long totalCount;

    public StudentFilterResultDTO(List<T> list, Long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }
}
