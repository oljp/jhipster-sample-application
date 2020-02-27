import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { SelComponent } from './sel.component';
import { SelDetailComponent } from './sel-detail.component';
import { SelUpdateComponent } from './sel-update.component';
import { SelDeleteDialogComponent } from './sel-delete-dialog.component';
import { selRoute } from './sel.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(selRoute)],
  declarations: [SelComponent, SelDetailComponent, SelUpdateComponent, SelDeleteDialogComponent],
  entryComponents: [SelDeleteDialogComponent]
})
export class JhipsterSampleApplicationSelModule {}
