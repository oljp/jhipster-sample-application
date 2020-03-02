import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISel } from 'app/shared/model/sel.model';
import { SelService } from './sel.service';

@Component({
  templateUrl: './sel-delete-dialog.component.html'
})
export class SelDeleteDialogComponent {
  sel?: ISel;

  constructor(protected selService: SelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.selService.delete(id).subscribe(() => {
      this.eventManager.broadcast('selListModification');
      this.activeModal.close();
    });
  }
}
