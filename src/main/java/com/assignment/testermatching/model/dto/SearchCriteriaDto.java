package com.assignment.testermatching.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchCriteriaDto {

    private List<String> specificCountries;
    private List<String> specificDevices;
}
