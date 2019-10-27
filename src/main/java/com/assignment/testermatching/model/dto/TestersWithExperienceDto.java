package com.assignment.testermatching.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestersWithExperienceDto {

    private String testerName;
    private long experience;
}
