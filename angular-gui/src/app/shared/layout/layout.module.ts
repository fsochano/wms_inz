import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WithSidePanelLayoutComponent } from './with-side-panel-layout/with-side-panel-layout.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { WithHeaderLayoutComponent } from './with-header-layout/with-header-layout.component';

@NgModule({
  declarations: [
    WithHeaderLayoutComponent,
    WithSidePanelLayoutComponent,
  ],
  exports: [
    WithHeaderLayoutComponent,
    WithSidePanelLayoutComponent,
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
  ]
})
export class LayoutModule { }
