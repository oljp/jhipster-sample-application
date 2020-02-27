import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISel } from 'app/shared/model/sel.model';

type EntityResponseType = HttpResponse<ISel>;
type EntityArrayResponseType = HttpResponse<ISel[]>;

@Injectable({ providedIn: 'root' })
export class SelService {
  public resourceUrl = SERVER_API_URL + 'api/sels';

  constructor(protected http: HttpClient) {}

  create(sel: ISel): Observable<EntityResponseType> {
    return this.http.post<ISel>(this.resourceUrl, sel, { observe: 'response' });
  }

  update(sel: ISel): Observable<EntityResponseType> {
    return this.http.put<ISel>(this.resourceUrl, sel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
