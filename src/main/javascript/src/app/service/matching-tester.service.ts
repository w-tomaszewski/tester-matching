import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { DeviceOptions } from '../model/DeviceOptions';
import { CountryOptions } from '../model/CountryOptions';
import { Observable } from 'rxjs';
import { MatchingTesters } from '../model/MatchingTesters';

const DEVICE_CRITERIA_PREFIX = "deviceCriteria";
const COUNTRY_CRITERIA_PREFIX = "countryCriteria";

@Injectable({
  providedIn: 'root'
})
export class MatchingTesterService {

  constructor(private http: HttpClient) { }

  getDevices(): Observable<DeviceOptions> {
      const restURL = environment.apiServer + environment.apiMethods.optionDevices;
      return this.http.options<DeviceOptions>(restURL);
  }

  getCountries(): Observable<CountryOptions> {
    const restURL = environment.apiServer + environment.apiMethods.optionCountries;
    return this.http.options<CountryOptions>(restURL);
  }

  getTesterMatching(deviceCriteria: Array<string>, countryCriteria: Array<string>): Observable<MatchingTesters> {
    const searchCriteria = this.generateSearchCriteriaGetParams(countryCriteria, deviceCriteria);
    const restURL = environment.apiServer + environment.apiMethods.matchingMethod;
    return this.http.get<MatchingTesters>(restURL + searchCriteria);
  }

  private generateSearchCriteriaGetParams(countryCriteria: string[], deviceCriteria: string[]) {
    let searchCriteria: string;
    let itemCounter: number = 0;
    countryCriteria.forEach(item => {
      if (itemCounter === 0) {
        searchCriteria = "?";
      }
      else {
        searchCriteria += "&";
      }
      itemCounter++;
      searchCriteria += COUNTRY_CRITERIA_PREFIX + itemCounter + "=" + item;
    });
    itemCounter = 0;
    deviceCriteria.forEach(item => {
      searchCriteria += "&";
      itemCounter++;
      searchCriteria += DEVICE_CRITERIA_PREFIX + itemCounter + "=" + item;
    });
    return searchCriteria;
  }
}
