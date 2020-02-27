import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { DistinctEventComponent } from './distinct-event.component';
import { DistinctEventDetailComponent } from './distinct-event-detail.component';
import { DistinctEventUpdateComponent } from './distinct-event-update.component';
import { DistinctEventDeleteDialogComponent } from './distinct-event-delete-dialog.component';
import { distinctEventRoute } from './distinct-event.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(distinctEventRoute)],
  declarations: [DistinctEventComponent, DistinctEventDetailComponent, DistinctEventUpdateComponent, DistinctEventDeleteDialogComponent],
  entryComponents: [DistinctEventDeleteDialogComponent]
})
export class JhipsterSampleApplicationDistinctEventModule {}
