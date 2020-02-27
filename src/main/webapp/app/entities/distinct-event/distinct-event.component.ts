import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistinctEvent } from 'app/shared/model/distinct-event.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DistinctEventService } from './distinct-event.service';
import { DistinctEventDeleteDialogComponent } from './distinct-event-delete-dialog.component';

@Component({
  selector: 'jhi-distinct-event',
  templateUrl: './distinct-event.component.html'
})
export class DistinctEventComponent implements OnInit, OnDestroy {
  distinctEvents?: IDistinctEvent[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected distinctEventService: DistinctEventService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.distinctEventService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDistinctEvent[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInDistinctEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDistinctEvent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDistinctEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('distinctEventListModification', () => this.loadPage());
  }

  delete(distinctEvent: IDistinctEvent): void {
    const modalRef = this.modalService.open(DistinctEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.distinctEvent = distinctEvent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDistinctEvent[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/distinct-event'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.distinctEvents = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
