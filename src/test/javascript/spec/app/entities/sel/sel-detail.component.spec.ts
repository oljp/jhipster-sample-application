import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SelDetailComponent } from 'app/entities/sel/sel-detail.component';
import { Sel } from 'app/shared/model/sel.model';

describe('Component Tests', () => {
  describe('Sel Management Detail Component', () => {
    let comp: SelDetailComponent;
    let fixture: ComponentFixture<SelDetailComponent>;
    const route = ({ data: of({ sel: new Sel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
