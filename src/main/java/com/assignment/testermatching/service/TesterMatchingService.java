package com.assignment.testermatching.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TesterMatchingService {

    private static final String SPACE = " ";

    private final BugRepository bugRepository;
    private final DeviceRepository deviceRepository;
    private final TesterRepository testerRepository;

    @Autowired
    public TesterMatchingService(final BugRepository bugRepository, final DeviceRepository deviceRepository, final TesterRepository testerRepository) {
        this.bugRepository = bugRepository;
        this.deviceRepository = deviceRepository;
        this.testerRepository = testerRepository;
    }

    public TestersMatchedResultDto matchExperiencedTestersBySearchCriteria(final SearchCriteriaDto searchCriteriaDto) {
        final List<TesterCSV> testersCriteria = getTestersBasedOnCriteria(searchCriteriaDto);
        final List<DeviceCSV> devicesCriteria = getDevicesBasedOnCriteria(searchCriteriaDto);
        final List<TestersWithExperienceDto> testerExperienceSummary = calculateNumberOfApproachAndBugFindings(testersCriteria, devicesCriteria);
        final Comparator<TestersWithExperienceDto> experienceComparator = Comparator.comparingLong(TestersWithExperienceDto::getExperience).reversed();
        testerExperienceSummary.sort(experienceComparator);
        return new TestersMatchedResultDto(testerExperienceSummary);
    }

    public CountryOptionsDto getCountryOptions() {
        return new CountryOptionsDto(testerRepository.getTesterList().stream().map(TesterCSV::getCountry).distinct().collect(Collectors.toList()));
    }

    public DeviceOptionsDto getDeviceOptions() {
        return new DeviceOptionsDto(deviceRepository.getDeviceList().stream().map(DeviceCSV::getDescription).distinct().collect(Collectors.toList()));
    }

    private List<TestersWithExperienceDto> calculateNumberOfApproachAndBugFindings(final List<TesterCSV> testersCriteria, final List<DeviceCSV> devicesCriteria) {
        final List<TestersWithExperienceDto> testerExperienceSummary = new ArrayList<>();
        testersCriteria.forEach(testerCSV ->
                testerExperienceSummary.add(TestersWithExperienceDto.builder().testerName(getTesterName(testerCSV)).experience(calculateTesterExperience(devicesCriteria, testerCSV)).build())
        );
        return testerExperienceSummary;
    }

    private String getTesterName(final TesterCSV testerCSV) {
        return testerCSV.getFirstName() + SPACE + testerCSV.getLastName();
    }

    private Long calculateTesterExperience(final List<DeviceCSV> devicesCriteria, final TesterCSV testerCSV) {
        return devicesCriteria.stream().reduce(0L, (prevSum, deviceCSV) ->
                prevSum + numberOfFoundedBugsToDevice(testerCSV, deviceCSV), Long::sum);
    }

    private List<DeviceCSV> getDevicesBasedOnCriteria(final SearchCriteriaDto searchCriteriaDto) {
        return deviceRepository.getDeviceList().stream().filter(deviceCSV -> searchCriteriaDto.getSpecificDevices().contains(deviceCSV.getDescription())).collect(Collectors.toList());
    }

    private List<TesterCSV> getTestersBasedOnCriteria(final SearchCriteriaDto searchCriteriaDto) {
        return testerRepository.getTesterList().stream().filter(testerCSV -> searchCriteriaDto.getSpecificCountries().contains(testerCSV.getCountry())).collect(Collectors.toList());
    }

    private long numberOfFoundedBugsToDevice(final TesterCSV testerCSV, final DeviceCSV deviceCSV) {
        return bugRepository.getBugList().stream()
                .filter(testerDeviceCSV -> testerDeviceCSV.getDeviceId() == deviceCSV.getDeviceId())
                .filter(testerDeviceCSV -> testerDeviceCSV.getTesterId() == testerCSV.getTesterId())
                .count();
    }
}
