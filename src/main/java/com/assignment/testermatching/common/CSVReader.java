package com.assignment.testermatching.common;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {

    private CSVReader() {
        // This is a private constructor
    }

    public static List<String> readCSVLines(final String filePath) throws IOException {
        final ClassPathResource classPathResource = new ClassPathResource(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))) {
            return reader.lines().filter(lines -> !lines.isEmpty()).collect(Collectors.toList());
        }
    }
}
