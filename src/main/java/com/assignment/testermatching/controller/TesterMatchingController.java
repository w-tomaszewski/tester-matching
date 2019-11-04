package com.assignment.testermatching.controller;

import com.assignment.testermatching.converter.SearchCriteriaConverter;
import com.assignment.testermatching.model.dto.CountryOptionsDto;
import com.assignment.testermatching.model.dto.DeviceOptionsDto;
import com.assignment.testermatching.model.dto.TestersMatchedResultDto;
import com.assignment.testermatching.service.TesterMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/tester")
@CrossOrigin(origins = "http://localhost:4200")
public class TesterMatchingController {

    private final TesterMatchingService testerMatchingService;
    private final SearchCriteriaConverter searchCriteriaConverter;

    @Autowired
    public TesterMatchingController(final TesterMatchingService testerMatchingService, final SearchCriteriaConverter searchCriteriaConverter) {
        this.testerMatchingService = testerMatchingService;
        this.searchCriteriaConverter = searchCriteriaConverter;
    }

    @GetMapping("/matching/experience")
    public ResponseEntity<TestersMatchedResultDto> getTestersWithExperienceBySearchCriteria(final @RequestParam Map<String, String> searchCriteria) {
        return new ResponseEntity<>(testerMatchingService.matchExperiencedTestersBySearchCriteria(searchCriteriaConverter.convert(searchCriteria)), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries", method = RequestMethod.GET)
    public ResponseEntity<CountryOptionsDto> getCountryOptions() {
        return new ResponseEntity<>(testerMatchingService.getCountryOptions(), HttpStatus.OK);
    }

    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public ResponseEntity<DeviceOptionsDto> getDeviceOptions() {
        return new ResponseEntity<>(testerMatchingService.getDeviceOptions(), HttpStatus.OK);
    }
}
