package com.assignment.testermatching.model.csv;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class TesterDeviceCSV {

    private int testerId;
    private int deviceId;
}
