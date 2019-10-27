import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatOption } from '@angular/material/core';

const DEFAULT_PLACEHOLDER = "Select";
const SELECT_ALL_REF = "selectAllRef";
const SELECT_ALL_VALUE = 0;

@Component({
  selector: 'app-search-criteria',
  templateUrl: './search-criteria.component.html',
  styleUrls: ['./search-criteria.component.scss']
})
export class SearchCriteriaComponent implements OnInit {

  optionsControl = new FormControl();
  currentSelection: Array<string>;

  @Input() placeholder = DEFAULT_PLACEHOLDER;
  @Input() data: Array<string>;
  @Output() onSelect = new EventEmitter<Array<string>>();
  @ViewChild(SELECT_ALL_REF, {static: false}) private selectAllRef: MatOption;

  ngOnInit(): void {
    this.optionsControl.valueChanges.subscribe((optionsSelected: Array<any>) => {
      this.filterToRemoveSelectAllValue(optionsSelected);
      this.uncheckSelectAllOnChange();
      this.onSelect.emit(this.currentSelection.slice());
    });
  }

  selectAllOptionClicked() {
    this.isAllOptionsSelected() ? this.deselectAll() : this.selectAll();
  }

  private selectAll() {
    this.optionsControl.patchValue([...this.data, SELECT_ALL_VALUE]);
    this.currentSelection = this.data.slice();
  }

  private isAllOptionsSelected() {
    const a1 = this.currentSelection.slice();
    const a2 = this.data.slice();
    return a1.length === a2.length && a1.every(function(value, index) { return value === a2[index]})
  }

  private uncheckSelectAllOnChange() {
    if (this.selectAllRef.selected && !this.isAllOptionsSelected()) {
      this.selectAllRef.deselect();
    }
  }

  private filterToRemoveSelectAllValue(optionsSelected: any[]) {
    this.currentSelection = optionsSelected.filter(function (value) {
      return value != SELECT_ALL_VALUE;
    });
  }

  private deselectAll() {
    this.currentSelection = [];
    this.optionsControl.patchValue([]);
  }
}
