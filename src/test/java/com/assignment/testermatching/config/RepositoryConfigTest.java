package com.assignment.testermatching.config;

import com.assignment.testermatching.loader.DataSourceCSVLoader;
import com.assignment.testermatching.repository.BugRepository;
import com.assignment.testermatching.repository.DeviceRepository;
import com.assignment.testermatching.repository.TesterDeviceRepository;
import com.assignment.testermatching.repository.TesterRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryConfigTest {

    private String DEVICES_CSV_FILE = "static/csv/devices.csv";
    private String TESTERS_CSV_FILE = "static/csv/testers.csv";
    private String BUGS_CSV_FILE = "static/csv/bugs.csv";
    private String TESTER_DEVICE_CSV_FILE = "static/csv/tester_device.csv";
    private RepositoryConfig repositoryConfig = new RepositoryConfig(new DataSourceCSVLoader(), DEVICES_CSV_FILE, TESTERS_CSV_FILE, BUGS_CSV_FILE, TESTER_DEVICE_CSV_FILE);

    @Test
    void shouldCreateBugRepository() {
        BugRepository bugRepository = repositoryConfig.bugRepository();

        assertThat(bugRepository.getBugList()).isNotEmpty();
    }

    @Test
    void shouldCreateDeviceRepository() {
        DeviceRepository deviceRepository = repositoryConfig.deviceRepository();

        assertThat(deviceRepository.getDeviceList()).isNotEmpty();
    }

    @Test
    void shouldCreateTesterRepository() {
        TesterRepository testerRepository = repositoryConfig.testerRepository();

        assertThat(testerRepository.getTesterList()).isNotEmpty();
    }

    @Test
    void shouldCreateTesterDeviceRepository() {
        TesterDeviceRepository testerDeviceRepository = repositoryConfig.testerDeviceRepository();

        assertThat(testerDeviceRepository.getTestDeviceList()).isNotEmpty();
    }
}