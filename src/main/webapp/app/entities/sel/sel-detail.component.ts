import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISel } from 'app/shared/model/sel.model';

@Component({
  selector: 'jhi-sel-detail',
  templateUrl: './sel-detail.component.html'
})
export class SelDetailComponent implements OnInit {
  sel: ISel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sel }) => (this.sel = sel));
  }

  previousState(): void {
    window.history.back();
  }
}
