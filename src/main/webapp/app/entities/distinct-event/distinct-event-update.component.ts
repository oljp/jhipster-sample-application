import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDistinctEvent, DistinctEvent } from 'app/shared/model/distinct-event.model';
import { DistinctEventService } from './distinct-event.service';
import { ISel } from 'app/shared/model/sel.model';
import { SelService } from 'app/entities/sel/sel.service';

@Component({
  selector: 'jhi-distinct-event-update',
  templateUrl: './distinct-event-update.component.html'
})
export class DistinctEventUpdateComponent implements OnInit {
  isSaving = false;
  sels: ISel[] = [];

  editForm = this.fb.group({
    id: [],
    provider: [],
    dataset: [],
    initiatingSels: [],
    associatedSels: []
  });

  constructor(
    protected distinctEventService: DistinctEventService,
    protected selService: SelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distinctEvent }) => {
      this.updateForm(distinctEvent);

      this.selService.query().subscribe((res: HttpResponse<ISel[]>) => (this.sels = res.body || []));
    });
  }

  updateForm(distinctEvent: IDistinctEvent): void {
    this.editForm.patchValue({
      id: distinctEvent.id,
      provider: distinctEvent.provider,
      dataset: distinctEvent.dataset,
      initiatingSels: distinctEvent.initiatingSels,
      associatedSels: distinctEvent.associatedSels
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const distinctEvent = this.createFromForm();
    if (distinctEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.distinctEventService.update(distinctEvent));
    } else {
      this.subscribeToSaveResponse(this.distinctEventService.create(distinctEvent));
    }
  }

  private createFromForm(): IDistinctEvent {
    return {
      ...new DistinctEvent(),
      id: this.editForm.get(['id'])!.value,
      provider: this.editForm.get(['provider'])!.value,
      dataset: this.editForm.get(['dataset'])!.value,
      initiatingSels: this.editForm.get(['initiatingSels'])!.value,
      associatedSels: this.editForm.get(['associatedSels'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistinctEvent>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISel): any {
    return item.id;
  }

  getSelected(selectedVals: ISel[], option: ISel): ISel {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
