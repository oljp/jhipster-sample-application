import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'distinct-event',
        loadChildren: () => import('./distinct-event/distinct-event.module').then(m => m.JhipsterSampleApplicationDistinctEventModule)
      },
      {
        path: 'sel',
        loadChildren: () => import('./sel/sel.module').then(m => m.JhipsterSampleApplicationSelModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterSampleApplicationEntityModule {}
