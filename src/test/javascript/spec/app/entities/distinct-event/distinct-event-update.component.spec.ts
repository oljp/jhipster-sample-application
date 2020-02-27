import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DistinctEventUpdateComponent } from 'app/entities/distinct-event/distinct-event-update.component';
import { DistinctEventService } from 'app/entities/distinct-event/distinct-event.service';
import { DistinctEvent } from 'app/shared/model/distinct-event.model';

describe('Component Tests', () => {
  describe('DistinctEvent Management Update Component', () => {
    let comp: DistinctEventUpdateComponent;
    let fixture: ComponentFixture<DistinctEventUpdateComponent>;
    let service: DistinctEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DistinctEventUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DistinctEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DistinctEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DistinctEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DistinctEvent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DistinctEvent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
