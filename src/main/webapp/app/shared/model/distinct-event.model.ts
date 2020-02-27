import { ISel } from 'app/shared/model/sel.model';

export interface IDistinctEvent {
  id?: number;
  provider?: string;
  dataset?: string;
  sels?: ISel[];
  initiatingSels?: ISel[];
  associatedSels?: ISel[];
}

export class DistinctEvent implements IDistinctEvent {
  constructor(
    public id?: number,
    public provider?: string,
    public dataset?: string,
    public sels?: ISel[],
    public initiatingSels?: ISel[],
    public associatedSels?: ISel[]
  ) {}
}
