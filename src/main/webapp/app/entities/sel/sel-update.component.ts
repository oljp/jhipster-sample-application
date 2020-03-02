import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISel, Sel } from 'app/shared/model/sel.model';
import { SelService } from './sel.service';

@Component({
  selector: 'jhi-sel-update',
  templateUrl: './sel-update.component.html'
})
export class SelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    rhel: []
  });

  constructor(protected selService: SelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sel }) => {
      this.updateForm(sel);
    });
  }

  updateForm(sel: ISel): void {
    this.editForm.patchValue({
      id: sel.id,
      name: sel.name,
      rhel: sel.rhel
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
      rhel: this.editForm.get(['rhel'])!.value
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
}
