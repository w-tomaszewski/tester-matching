package com.assignment.testermatching.config;

import com.assignment.testermatching.loader.DataSourceCSVLoader;
import com.assignment.testermatching.repository.BugRepository;
import com.assignment.testermatching.repository.DeviceRepository;
import com.assignment.testermatching.repository.TesterDeviceRepository;
import com.assignment.testermatching.repository.TesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    private final DataSourceCSVLoader dataSourceCSVLoader;
    private final String devicesFileLocation;
    private final String testersFileLocation;
    private final String bugsFileLocation;
    private final String testerDeviceFileLocation;

    @Autowired
    public RepositoryConfig(final DataSourceCSVLoader dataSourceCSVLoader,
                            final @Value("${csv.file.devices}") String devicesFileLocation,
                            final @Value("${csv.file.testers}") String testersFileLocation,
                            final @Value("${csv.file.bugs}") String bugsFileLocation,
                            final @Value("${csv.file.testerDevice}") String testerDeviceFileLocation) {
        this.dataSourceCSVLoader = dataSourceCSVLoader;
        this.devicesFileLocation = devicesFileLocation;
        this.testersFileLocation = testersFileLocation;
        this.bugsFileLocation = bugsFileLocation;
        this.testerDeviceFileLocation = testerDeviceFileLocation;
    }

    @Bean
    public DeviceRepository deviceRepository() {
        return dataSourceCSVLoader.loadDeviceRepository(devicesFileLocation);
    }

    @Bean
    public TesterRepository testerRepository() {
        return dataSourceCSVLoader.loadTesterRepository(testersFileLocation);
    }

    @Bean
    public BugRepository bugRepository() { return dataSourceCSVLoader.loadBugRepository(bugsFileLocation); }

    @Bean
    public TesterDeviceRepository testerDeviceRepository() { return dataSourceCSVLoader.loadTesterDeviceRepository(testerDeviceFileLocation); }
}
