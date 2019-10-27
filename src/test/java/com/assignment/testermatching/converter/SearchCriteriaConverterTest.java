package com.assignment.testermatching.converter;

import com.assignment.testermatching.model.dto.SearchCriteriaDto;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchCriteriaConverterTest {

    private SearchCriteriaConverter searchCriteriaConverter = new SearchCriteriaConverter();

    @Test
    public void shouldConvertSearchCriteria() {
        final String deviceCriteriaKey1 = "deviceCriteria1";
        final String deviceCriteriaKey2 = "deviceCriteria2";
        final String deviceCriteriaValue1 = "iPhone 4";
        final String deviceCriteriaValue2 = "iPhone 6";

        final String countryCriteriaKey1 = "countryCriteria1";
        final String countryCriteriaKey2 = "countryCriteria2";
        final String countryCriteriaValue1 = "US";
        final String countryCriteriaValue2 = "DE";

        final Map<String, String> testParams = new HashMap<>();
        testParams.put(deviceCriteriaKey1, deviceCriteriaValue1);
        testParams.put(deviceCriteriaKey2, deviceCriteriaValue2);
        testParams.put(countryCriteriaKey1, countryCriteriaValue1);
        testParams.put(countryCriteriaKey2, countryCriteriaValue2);

        SearchCriteriaDto searchCriteriaDto = searchCriteriaConverter.convert(testParams);

        assertThat(searchCriteriaDto.getSpecificDevices()).contains(deviceCriteriaValue1, deviceCriteriaValue2);
        assertThat(searchCriteriaDto.getSpecificCountries()).contains(countryCriteriaValue1, countryCriteriaValue2);
    }
}