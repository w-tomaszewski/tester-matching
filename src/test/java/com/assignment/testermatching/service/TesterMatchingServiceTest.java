package com.assignment.testermatching.service;

import com.assignment.testermatching.model.csv.BugCSV;
import com.assignment.testermatching.model.csv.DeviceCSV;
import com.assignment.testermatching.model.csv.TesterCSV;
import com.assignment.testermatching.model.dto.CountryOptionsDto;
import com.assignment.testermatching.model.dto.DeviceOptionsDto;
import com.assignment.testermatching.model.dto.SearchCriteriaDto;
import com.assignment.testermatching.model.dto.TestersMatchedResultDto;
import com.assignment.testermatching.model.dto.TestersWithExperienceDto;
import com.assignment.testermatching.repository.BugRepository;
import com.assignment.testermatching.repository.DeviceRepository;
import com.assignment.testermatching.repository.TesterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TesterMatchingServiceTest {

    @Mock
    private BugRepository bugRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private TesterRepository testerRepository;

    @InjectMocks
    private TesterMatchingService testerMatchingService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(TesterMatchingService.class);
    }

    @Test
    public void shouldMatchExperiencedTestersBySearchCriteria() {
        final List<TesterCSV> testers = new ArrayList<>(Arrays.asList(
                TesterCSV.builder().testerId(1).firstName("John").lastName("Smith").country("US").build(),
                TesterCSV.builder().testerId(2).firstName("Nick").lastName("Owen").country("GB").build()));
        final List<DeviceCSV> devices = new ArrayList<>(Arrays.asList(
                DeviceCSV.builder().deviceId(1).description("iPhone 4").build(),
                DeviceCSV.builder().deviceId(2).description("iPhone 6").build()));
        final List<BugCSV> bugs = new ArrayList<>(Arrays.asList(
                BugCSV.builder().bugId(1).deviceId(1).testerId(1).build(),
                BugCSV.builder().bugId(2).deviceId(2).testerId(1).build(),
                BugCSV.builder().bugId(3).deviceId(2).testerId(2).build()));
        final SearchCriteriaDto searchCriteriaDto = SearchCriteriaDto.builder()
                .specificCountries(Arrays.asList("US", "GB"))
                .specificDevices(Arrays.asList("iPhone 4", "iPhone 6")).build();

        when(testerRepository.getTesterList()).thenReturn(testers);
        when(deviceRepository.getDeviceList()).thenReturn(devices);
        when(bugRepository.getBugList()).thenReturn(bugs);

        final TestersMatchedResultDto testersMatchedResultDto = testerMatchingService.matchExperiencedTestersBySearchCriteria(searchCriteriaDto);

        assertThat(testersMatchedResultDto.getTestersWithExperienceList()).extracting(TestersWithExperienceDto::getTesterName).containsExactly("John Smith", "Nick Owen");
        assertThat(testersMatchedResultDto.getTestersWithExperienceList()).extracting(TestersWithExperienceDto::getExperience).containsExactly(2L, 1L);
        verify(testerRepository, times(1)).getTesterList();
        verify(deviceRepository, times(1)).getDeviceList();
        verify(bugRepository, times(4)).getBugList();
    }

    @Test
    public void shouldGetCountryOptions() {
        final List<TesterCSV> testers = new ArrayList<>(Arrays.asList(TesterCSV.builder().country("US").build(), TesterCSV.builder().country("JP").build()));

        when(testerRepository.getTesterList()).thenReturn(testers);

        final CountryOptionsDto countryOptionsDto = testerMatchingService.getCountryOptions();

        assertThat(countryOptionsDto.getCountryOptions()).contains("US", "JP");
        verify(testerRepository, times(1)).getTesterList();
    }

    @Test
    public void shouldGetDeviceOptions() {
        final List<DeviceCSV> devices = new ArrayList<>(Arrays.asList(DeviceCSV.builder().description("iPhone 4").build(), DeviceCSV.builder().description("iPhone 6").build()));

        when(deviceRepository.getDeviceList()).thenReturn(devices);

        final DeviceOptionsDto deviceOptionsDto = testerMatchingService.getDeviceOptions();

        assertThat(deviceOptionsDto.getDeviceOptions()).contains("iPhone 4", "iPhone 6");
        verify(deviceRepository, times(1)).getDeviceList();
    }
}