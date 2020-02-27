import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DistinctEventDetailComponent } from 'app/entities/distinct-event/distinct-event-detail.component';
import { DistinctEvent } from 'app/shared/model/distinct-event.model';

describe('Component Tests', () => {
  describe('DistinctEvent Management Detail Component', () => {
    let comp: DistinctEventDetailComponent;
    let fixture: ComponentFixture<DistinctEventDetailComponent>;
    const route = ({ data: of({ distinctEvent: new DistinctEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DistinctEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DistinctEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DistinctEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load distinctEvent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.distinctEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
