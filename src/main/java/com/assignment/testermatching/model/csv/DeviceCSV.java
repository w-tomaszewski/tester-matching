package com.assignment.testermatching.model.csv;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class DeviceCSV {

    private int deviceId;
    private String description;
}
