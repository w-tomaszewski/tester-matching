package com.assignment.testermatching.loader;

import com.assignment.testermatching.common.CSVReader;
import com.assignment.testermatching.model.csv.BugCSV;
import com.assignment.testermatching.model.csv.DeviceCSV;
import com.assignment.testermatching.model.csv.TesterCSV;
import com.assignment.testermatching.model.csv.TesterDeviceCSV;
import com.assignment.testermatching.repository.BugRepository;
import com.assignment.testermatching.repository.DeviceRepository;
import com.assignment.testermatching.repository.TesterDeviceRepository;
import com.assignment.testermatching.repository.TesterRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataSourceCSVLoader {

    public static final String NOT_VALID_FILE_EXCEPTION_MESSAGE = "File is not valid to load.";
    public static final String NOT_FOUND_FILE_EXCEPTION_MESSAGE = "File not found.";
    private static final String DEVICES_CSV_HEADER = "\"deviceId\",\"description\"";
    private static final String TESTERS_CSV_HEADER = "\"testerId\",\"firstName\",\"lastName\",\"country\",\"lastLogin\"";
    private static final String BUGS_CSV_HEADER = "\"bugId\",\"deviceId\",\"testerId\"";
    private static final String TESTER_DEVICE_CSV_HEADER = "\"testerId\",\"deviceId\"";
    private static final String CSV_DELIMETER = ",";
    private static final String QUOTE = "\"";
    private static final int AFTER_HEADER_INDEX = 1;

    public DeviceRepository loadDeviceRepository(final String csvLocation) {
        try {
            final List<String> deviceCSVLines = CSVReader.readCSVLines(csvLocation);
            throwExceptionWhenWrongHeader(deviceCSVLines.get(0), DEVICES_CSV_HEADER);
            final List<String> linesWithoutHeader = deviceCSVLines.subList(AFTER_HEADER_INDEX, deviceCSVLines.size());
            return new DeviceRepository(linesWithoutHeader.stream()
                    .map(this::removeQuotes)
                    .map(this::convertLineToDeviceCSV)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw new RuntimeException(NOT_FOUND_FILE_EXCEPTION_MESSAGE);
            }
            throw new RuntimeException(NOT_VALID_FILE_EXCEPTION_MESSAGE);
        }
    }

    public TesterRepository loadTesterRepository(final String csvLocation) {
        try {
            final List<String> testerCSVLines = CSVReader.readCSVLines(csvLocation);
            throwExceptionWhenWrongHeader(testerCSVLines.get(0), TESTERS_CSV_HEADER);
            final List<String> linesWithoutHeader = testerCSVLines.subList(AFTER_HEADER_INDEX, testerCSVLines.size());
            final List<TesterCSV> testerList = linesWithoutHeader.stream()
                    .map(this::removeQuotes)
                    .map(this::convertLineToTesterCSV)
                    .collect(Collectors.toList());
            return new TesterRepository(testerList);
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw new RuntimeException(NOT_FOUND_FILE_EXCEPTION_MESSAGE);
            }
            throw new RuntimeException(NOT_VALID_FILE_EXCEPTION_MESSAGE);
        }
    }

    public BugRepository loadBugRepository(final String csvLocation) {
        try {
            final List<String> bugCSVLines = CSVReader.readCSVLines(csvLocation);
            throwExceptionWhenWrongHeader(bugCSVLines.get(0), BUGS_CSV_HEADER);
            final List<String> linesWithoutHeader = bugCSVLines.subList(AFTER_HEADER_INDEX, bugCSVLines.size());
            return new BugRepository(linesWithoutHeader.stream()
                    .map(this::removeQuotes)
                    .map(this::convertLineToBugCSV)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw new RuntimeException(NOT_FOUND_FILE_EXCEPTION_MESSAGE);
            }
            throw new RuntimeException(NOT_VALID_FILE_EXCEPTION_MESSAGE);
        }
    }

    public TesterDeviceRepository loadTesterDeviceRepository(final String csvLocation) {
        try {
            final List<String> testerDeviceCSVLines = CSVReader.readCSVLines(csvLocation);
            throwExceptionWhenWrongHeader(testerDeviceCSVLines.get(0), TESTER_DEVICE_CSV_HEADER);
            final List<String> linesWithoutHeader = testerDeviceCSVLines.subList(AFTER_HEADER_INDEX, testerDeviceCSVLines.size());
            return new TesterDeviceRepository(linesWithoutHeader.stream()
                    .map(this::removeQuotes)
                    .map(this::convertLineToTesterDeviceCSV)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            if (e instanceof IOException) {
                throw new RuntimeException(NOT_FOUND_FILE_EXCEPTION_MESSAGE);
            }
            throw new RuntimeException(NOT_VALID_FILE_EXCEPTION_MESSAGE);
        }
    }

    private DeviceCSV convertLineToDeviceCSV(final String line) {
        final String[] record = line.split(CSV_DELIMETER);
        return DeviceCSV.builder()
                .deviceId(Integer.valueOf(record[0]))
                .description(record[1])
                .build();
    }

    private TesterCSV convertLineToTesterCSV(final String line) {
        final String[] record = line.split(CSV_DELIMETER);
        return TesterCSV.builder()
                .testerId(Integer.valueOf(record[0]))
                .firstName(record[1])
                .lastName(record[2])
                .country(record[3])
                .lastLogin(record[4])
                .build();
    }

    private BugCSV convertLineToBugCSV(final String line) {
        final String[] record = line.split(CSV_DELIMETER);
        return BugCSV.builder()
                .bugId(Integer.valueOf(record[0]))
                .deviceId(Integer.valueOf(record[1]))
                .testerId(Integer.valueOf(record[2]))
                .build();
    }

    private TesterDeviceCSV convertLineToTesterDeviceCSV(final String line) {
        final String[] record = line.split(CSV_DELIMETER);
        return TesterDeviceCSV.builder()
                .testerId(Integer.valueOf(record[0]))
                .deviceId(Integer.valueOf(record[1]))
                .build();
    }

    private String removeQuotes(final String line) {
        return line.replaceAll(QUOTE, "");
    }

    private void throwExceptionWhenWrongHeader(final String headerLine, final String expectedHeaderLine) {
        if (!headerLine.equals(expectedHeaderLine)) {
            throw new RuntimeException(NOT_VALID_FILE_EXCEPTION_MESSAGE);
        }
    }
}
