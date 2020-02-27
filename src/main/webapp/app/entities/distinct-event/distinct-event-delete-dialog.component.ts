import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistinctEvent } from 'app/shared/model/distinct-event.model';
import { DistinctEventService } from './distinct-event.service';

@Component({
  templateUrl: './distinct-event-delete-dialog.component.html'
})
export class DistinctEventDeleteDialogComponent {
  distinctEvent?: IDistinctEvent;

  constructor(
    protected distinctEventService: DistinctEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.distinctEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('distinctEventListModification');
      this.activeModal.close();
    });
  }
}
