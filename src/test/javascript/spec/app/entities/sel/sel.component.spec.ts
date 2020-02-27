import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SelComponent } from 'app/entities/sel/sel.component';
import { SelService } from 'app/entities/sel/sel.service';
import { Sel } from 'app/shared/model/sel.model';

describe('Component Tests', () => {
  describe('Sel Management Component', () => {
    let comp: SelComponent;
    let fixture: ComponentFixture<SelComponent>;
    let service: SelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SelComponent]
      })
        .overrideTemplate(SelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sels && comp.sels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
