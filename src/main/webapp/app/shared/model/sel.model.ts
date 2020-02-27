import { IDistinctEvent } from 'app/shared/model/distinct-event.model';

export interface ISel {
  id?: number;
  name?: string;
  rhel?: string;
  distinctEvents?: IDistinctEvent[];
  distinctEvents?: IDistinctEvent[];
  distinctEvents?: IDistinctEvent[];
}

export class Sel implements ISel {
  constructor(
    public id?: number,
    public name?: string,
    public rhel?: string,
    public distinctEvents?: IDistinctEvent[],
    public distinctEvents?: IDistinctEvent[],
    public distinctEvents?: IDistinctEvent[]
  ) {}
}
