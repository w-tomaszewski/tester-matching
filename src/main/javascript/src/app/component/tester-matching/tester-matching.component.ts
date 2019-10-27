import { Component, OnInit } from '@angular/core';
import { MatchingTesterService } from 'src/app/service/matching-tester.service';
import { DeviceOptions } from 'src/app/model/DeviceOptions';
import { CountryOptions } from 'src/app/model/CountryOptions';
import { MatchingTesters } from 'src/app/model/MatchingTesters';

@Component({
  selector: 'app-tester-matching',
  templateUrl: './tester-matching.component.html',
  styleUrls: ['./tester-matching.component.scss']
})
export class TesterMatchingComponent implements OnInit {

  deviceOptions: Array<string>;
  countryOptions: Array<string>;
  currentDeviceSelection: Array<string> = new Array();
  currentCountrySelection: Array<string> = new Array();
  matchingTesterResults: MatchingTesters;
  shouldBtnDisabled: boolean = true;

  constructor(private matchingTesterService : MatchingTesterService) {
  }

  ngOnInit() {
    this.matchingTesterService.getDevices().subscribe((deviceOptions: DeviceOptions) => {
      this.deviceOptions = deviceOptions.deviceOptions;
    });

    this.matchingTesterService.getCountries().subscribe((countryOptions: CountryOptions) => {
      this.countryOptions = countryOptions.countryOptions;
    });
  }

  onTesterMatchingClick() {
    this.matchingTesterService.getTesterMatching(this.currentDeviceSelection, this.currentCountrySelection)
    .subscribe((testerMatchListResult: MatchingTesters) => {
        this.matchingTesterResults = testerMatchListResult;
        this.calculateBtnVisibility();
    });
  }

  onCountrySelect(selectionResult: Array<string>) {
    this.currentCountrySelection = selectionResult;
    this.calculateBtnVisibility();
    this.matchingTesterResults = null;
  }

  onDeviceSelect(selectionResult: Array<string>) {
    this.currentDeviceSelection = selectionResult;
    this.calculateBtnVisibility();
    this.matchingTesterResults = null;
  }

  private calculateBtnVisibility() {
    this.shouldBtnDisabled = !(this.currentDeviceSelection.length > 0 && this.currentCountrySelection.length > 0);
  }

}
