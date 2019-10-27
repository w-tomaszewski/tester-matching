package com.assignment.testermatching.repository;

import com.assignment.testermatching.model.csv.DeviceCSV;
import lombok.Getter;

import java.util.List;

@Getter
public class DeviceRepository {

    private final List<DeviceCSV> deviceList;

    public DeviceRepository(final List<DeviceCSV> deviceList) {
        this.deviceList = deviceList;
    }
}
