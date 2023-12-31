package com.example.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FilterResultDTO<T> {

    private List<T> list;
    private Long totalCount;

    public FilterResultDTO(List<T> list, Long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }
}
