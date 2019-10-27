package com.assignment.testermatching.repository;

import com.assignment.testermatching.model.csv.BugCSV;
import lombok.Getter;

import java.util.List;

@Getter
public class BugRepository {

    private final List<BugCSV> bugList;

    public BugRepository(final List<BugCSV> bugList) {
        this.bugList = bugList;
    }
}
