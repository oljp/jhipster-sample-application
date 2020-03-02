import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDistinctEvent } from 'app/shared/model/distinct-event.model';

type EntityResponseType = HttpResponse<IDistinctEvent>;
type EntityArrayResponseType = HttpResponse<IDistinctEvent[]>;

@Injectable({ providedIn: 'root' })
export class DistinctEventService {
  public resourceUrl = SERVER_API_URL + 'api/distinct-events';

  constructor(protected http: HttpClient) {}

  create(distinctEvent: IDistinctEvent): Observable<EntityResponseType> {
    return this.http.post<IDistinctEvent>(this.resourceUrl, distinctEvent, { observe: 'response' });
  }

  update(distinctEvent: IDistinctEvent): Observable<EntityResponseType> {
    return this.http.put<IDistinctEvent>(this.resourceUrl, distinctEvent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistinctEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistinctEvent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
