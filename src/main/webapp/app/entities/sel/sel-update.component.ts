import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISel, Sel } from 'app/shared/model/sel.model';
import { SelService } from './sel.service';
import { IDistinctEvent } from 'app/shared/model/distinct-event.model';
import { DistinctEventService } from 'app/entities/distinct-event/distinct-event.service';

@Component({
  selector: 'jhi-sel-update',
  templateUrl: './sel-update.component.html'
})
export class SelUpdateComponent implements OnInit {
  isSaving = false;
  distinctevents: IDistinctEvent[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    rhel: [],
    distinctEvents: [],
    distinctEvents: []
  });

  constructor(
    protected selService: SelService,
    protected distinctEventService: DistinctEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sel }) => {
      this.updateForm(sel);

      this.distinctEventService.query().subscribe((res: HttpResponse<IDistinctEvent[]>) => (this.distinctevents = res.body || []));
    });
  }

  updateForm(sel: ISel): void {
    this.editForm.patchValue({
      id: sel.id,
      name: sel.name,
      rhel: sel.rhel,
      distinctEvents: sel.distinctEvents,
      distinctEvents: sel.distinctEvents
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sel = this.createFromForm();
    if (sel.id !== undefined) {
      this.subscribeToSaveResponse(this.selService.update(sel));
    } else {
      this.subscribeToSaveResponse(this.selService.create(sel));
    }
  }

  private createFromForm(): ISel {
    return {
      ...new Sel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      rhel: this.editForm.get(['rhel'])!.value,
      distinctEvents: this.editForm.get(['distinctEvents'])!.value,
      distinctEvents: this.editForm.get(['distinctEvents'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISel>>): void {
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

  trackById(index: number, item: IDistinctEvent): any {
    return item.id;
  }

  getSelected(selectedVals: IDistinctEvent[], option: IDistinctEvent): IDistinctEvent {
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
