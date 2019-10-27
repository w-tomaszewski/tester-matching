package com.assignment.testermatching.repository;

import com.assignment.testermatching.model.csv.TesterCSV;
import lombok.Getter;

import java.util.List;

@Getter
public class TesterRepository {

    private final List<TesterCSV> testerList;

    public TesterRepository(final List<TesterCSV> testerList) {
        this.testerList = testerList;
    }

}
