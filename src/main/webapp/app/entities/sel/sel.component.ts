import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISel } from 'app/shared/model/sel.model';
import { SelService } from './sel.service';
import { SelDeleteDialogComponent } from './sel-delete-dialog.component';

@Component({
  selector: 'jhi-sel',
  templateUrl: './sel.component.html'
})
export class SelComponent implements OnInit, OnDestroy {
  sels?: ISel[];
  eventSubscriber?: Subscription;

  constructor(protected selService: SelService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.selService.query().subscribe((res: HttpResponse<ISel[]>) => (this.sels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSels(): void {
    this.eventSubscriber = this.eventManager.subscribe('selListModification', () => this.loadAll());
  }

  delete(sel: ISel): void {
    const modalRef = this.modalService.open(SelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sel = sel;
  }
}
