import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDistinctEvent, DistinctEvent } from 'app/shared/model/distinct-event.model';
import { DistinctEventService } from './distinct-event.service';
import { DistinctEventComponent } from './distinct-event.component';
import { DistinctEventDetailComponent } from './distinct-event-detail.component';
import { DistinctEventUpdateComponent } from './distinct-event-update.component';

@Injectable({ providedIn: 'root' })
export class DistinctEventResolve implements Resolve<IDistinctEvent> {
  constructor(private service: DistinctEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistinctEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((distinctEvent: HttpResponse<DistinctEvent>) => {
          if (distinctEvent.body) {
            return of(distinctEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DistinctEvent());
  }
}

export const distinctEventRoute: Routes = [
  {
    path: '',
    component: DistinctEventComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'DistinctEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DistinctEventDetailComponent,
    resolve: {
      distinctEvent: DistinctEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DistinctEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DistinctEventUpdateComponent,
    resolve: {
      distinctEvent: DistinctEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DistinctEvents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DistinctEventUpdateComponent,
    resolve: {
      distinctEvent: DistinctEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DistinctEvents'
    },
    canActivate: [UserRouteAccessService]
  }
];
