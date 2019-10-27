package com.assignment.testermatching.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestersMatchedResultDto {

    private List<TestersWithExperienceDto> testersWithExperienceList;
}
