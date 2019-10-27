package com.assignment.testermatching.converter;

import com.assignment.testermatching.model.dto.SearchCriteriaDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SearchCriteriaConverter implements Converter<Map<String, String>, SearchCriteriaDto> {

    private static final String DEVICE_CRITERIA_PREFIX = "deviceCriteria";
    private static final String COUNTRY_CRITERIA_PREFIX = "countryCriteria";

    @Override
    public SearchCriteriaDto convert(final Map<String, String> searchCriteriaMap) {
        final List<String> deviceCriterias = new ArrayList<>();
        final List<String> countryCriterias = new ArrayList<>();
        searchCriteriaMap.forEach((key, value) -> {
            if (key.startsWith(DEVICE_CRITERIA_PREFIX)) {
                deviceCriterias.add(value);
            }
            if (key.startsWith(COUNTRY_CRITERIA_PREFIX)) {
                countryCriterias.add(value);
            }
        });
        return SearchCriteriaDto.builder().specificDevices(deviceCriterias).specificCountries(countryCriterias).build();
    }
}
