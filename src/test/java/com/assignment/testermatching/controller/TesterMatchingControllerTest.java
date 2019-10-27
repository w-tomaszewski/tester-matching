package com.assignment.testermatching.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TesterMatchingController.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.assignment.testermatching")
public class TesterMatchingControllerTest {

    private static final String TEST_DEVICE  = "iPhone 4";
    private static final String TEST_COUNTRY  = "US";
    private static final String TEST_MATCHING_TESTER  = "Taybin Rutkin";
    private static final String TEST_MATCHING_TESTER_EXPERIENCE  = "66";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldMatchExperiencedTestersBySearchCriteria() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(String.format("/testermatching/matching/experience?deviceCriteria1=%s&countryCriteria1=%s", TEST_DEVICE, TEST_COUNTRY))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(TEST_MATCHING_TESTER)))
                .andExpect(content().string(containsString(TEST_MATCHING_TESTER_EXPERIENCE)));
    }

    @Test
    public void shouldReturnCountryOptions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .options("/testermatching/countries")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(TEST_COUNTRY)));
    }

    @Test
    public void shouldReturnDeviceOptions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .options("/testermatching/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(TEST_DEVICE)));
    }
}