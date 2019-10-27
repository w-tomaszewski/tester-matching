import { Component, Input, OnInit } from '@angular/core';
import { MatchingTesters } from 'src/app/model/MatchingTesters';
import { TesterWithExperience } from 'src/app/model/TesterWithExperience';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-match-result',
  templateUrl: './match-result.component.html',
  styleUrls: ['./match-result.component.scss']
})
export class MatchResultComponent implements OnInit{

  dataSource: MatTableDataSource<TesterWithExperience>;
  displayedColumns: string[] = ['position', 'testerName', 'experience'];

  @Input() matchingResults: MatchingTesters; 

  constructor() { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource<TesterWithExperience>(this.matchingResults.testersWithExperienceList);
  }
}