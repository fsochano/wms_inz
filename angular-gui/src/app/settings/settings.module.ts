import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SettingsComponent } from '../settings/settings.component';
import { RouterModule } from '@angular/router';
import { SkuModule } from '../sku/sku.module';
import { LocationModule } from '../location/location.module';
import { ContainerModule } from '../container/container.module';
import { SharedModule } from '../shared/shared.module';
import { SettingsRoutingModule } from './settings-routing.module';



@NgModule({
  declarations: [SettingsComponent],
  imports: [
    CommonModule,
    SharedModule,
    SkuModule,
    LocationModule,
    ContainerModule,
    RouterModule,
    SettingsRoutingModule,
  ]
})
export class SettingsModule { }
