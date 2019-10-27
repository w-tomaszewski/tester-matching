package com.assignment.testermatching.repository;

import com.assignment.testermatching.model.csv.TesterDeviceCSV;
import lombok.Getter;

import java.util.List;

@Getter
public class TesterDeviceRepository {

    private final List<TesterDeviceCSV> testDeviceList;

    public TesterDeviceRepository(final List<TesterDeviceCSV> testDeviceList) {
        this.testDeviceList = testDeviceList;
    }
}
