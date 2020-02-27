import { IDistinctEvent } from 'app/shared/model/distinct-event.model';

export interface ISel {
  id?: number;
  name?: string;
  rhel?: string;
  initiatingEvents?: IDistinctEvent[];
  associatedEvents?: IDistinctEvent[];
  distinctEvents?: IDistinctEvent[];
}

export class Sel implements ISel {
  constructor(
    public id?: number,
    public name?: string,
    public rhel?: string,
    public initiatingEvents?: IDistinctEvent[],
    public associatedEvents?: IDistinctEvent[],
    public distinctEvents?: IDistinctEvent[]
  ) {}
}
