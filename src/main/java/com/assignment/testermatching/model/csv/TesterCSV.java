package com.assignment.testermatching.model.csv;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class TesterCSV {

    private int testerId;
    private String firstName;
    private String lastName;
    private String country;
    private String lastLogin;


}
