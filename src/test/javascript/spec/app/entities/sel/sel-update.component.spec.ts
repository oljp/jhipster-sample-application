import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SelUpdateComponent } from 'app/entities/sel/sel-update.component';
import { SelService } from 'app/entities/sel/sel.service';
import { Sel } from 'app/shared/model/sel.model';

describe('Component Tests', () => {
  describe('Sel Management Update Component', () => {
    let comp: SelUpdateComponent;
    let fixture: ComponentFixture<SelUpdateComponent>;
    let service: SelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sel(123);
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
        const entity = new Sel();
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
