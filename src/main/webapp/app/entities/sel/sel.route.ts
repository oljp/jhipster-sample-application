import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISel, Sel } from 'app/shared/model/sel.model';
import { SelService } from './sel.service';
import { SelComponent } from './sel.component';
import { SelDetailComponent } from './sel-detail.component';
import { SelUpdateComponent } from './sel-update.component';

@Injectable({ providedIn: 'root' })
export class SelResolve implements Resolve<ISel> {
  constructor(private service: SelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sel: HttpResponse<Sel>) => {
          if (sel.body) {
            return of(sel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sel());
  }
}

export const selRoute: Routes = [
  {
    path: '',
    component: SelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SelDetailComponent,
    resolve: {
      sel: SelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SelUpdateComponent,
    resolve: {
      sel: SelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sels'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SelUpdateComponent,
    resolve: {
      sel: SelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sels'
    },
    canActivate: [UserRouteAccessService]
  }
];
