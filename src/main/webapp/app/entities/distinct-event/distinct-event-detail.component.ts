import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistinctEvent } from 'app/shared/model/distinct-event.model';

@Component({
  selector: 'jhi-distinct-event-detail',
  templateUrl: './distinct-event-detail.component.html'
})
export class DistinctEventDetailComponent implements OnInit {
  distinctEvent: IDistinctEvent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distinctEvent }) => (this.distinctEvent = distinctEvent));
  }

  previousState(): void {
    window.history.back();
  }
}
