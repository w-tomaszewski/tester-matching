package com.assignment.testermatching.loader;

import com.assignment.testermatching.model.csv.BugCSV;
import com.assignment.testermatching.repository.BugRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DataSourceCSVLoaderTest {

    private String VALID_CSV_FILE = "csv_test/valid.csv";
    private String INVALID_CSV_FILE = "csv_test/invalid.csv";

    private DataSourceCSVLoader dataSourceCSVLoader = new DataSourceCSVLoader();

    @Test
    public void shouldSuccessLoadBugsRepository() {
        final BugRepository bugRepository = dataSourceCSVLoader.loadBugRepository(VALID_CSV_FILE);
        assertThat(bugRepository.getBugList()).hasSize(5);
        assertThat(bugRepository.getBugList()).extracting(BugCSV::getBugId).isNotEmpty();
        assertThat(bugRepository.getBugList()).extracting(BugCSV::getDeviceId).isNotEmpty();
        assertThat(bugRepository.getBugList()).extracting(BugCSV::getTesterId).isNotEmpty();
    }

    @Test
    public void shouldThrowExceptionWhenInvalidFile() {
        assertThatThrownBy(() -> dataSourceCSVLoader.loadBugRepository(INVALID_CSV_FILE)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining(DataSourceCSVLoader.NOT_VALID_FILE_EXCEPTION_MESSAGE);
        assertThatThrownBy(() -> dataSourceCSVLoader.loadTesterRepository(INVALID_CSV_FILE)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining(DataSourceCSVLoader.NOT_VALID_FILE_EXCEPTION_MESSAGE);
        assertThatThrownBy(() -> dataSourceCSVLoader.loadDeviceRepository(INVALID_CSV_FILE)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining(DataSourceCSVLoader.NOT_VALID_FILE_EXCEPTION_MESSAGE);
        assertThatThrownBy(() -> dataSourceCSVLoader.loadTesterDeviceRepository(INVALID_CSV_FILE)).isInstanceOf(RuntimeException.class)
                .hasMessageContaining(DataSourceCSVLoader.NOT_VALID_FILE_EXCEPTION_MESSAGE);
        assertThatThrownBy(() -> dataSourceCSVLoader.loadTesterDeviceRepository("123.csv")).isInstanceOf(RuntimeException.class)
                .hasMessageContaining(DataSourceCSVLoader.NOT_FOUND_FILE_EXCEPTION_MESSAGE);

    }

}