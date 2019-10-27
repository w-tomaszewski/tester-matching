package com.assignment.testermatching.model.csv;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class BugCSV {

    private int bugId;
    private int deviceId;
    private int testerId;
}
