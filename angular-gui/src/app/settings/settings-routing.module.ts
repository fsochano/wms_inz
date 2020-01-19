import { RouterModule } from '@angular/router';
import { SettingsComponent } from './settings.component';
import { AuthGuard } from '../auth/auth.guard';
import { NgModule } from '@angular/core';
import { SkuComponent } from '../sku/sku.component';
import { SkuDetailsComponent } from '../sku/sku-details/sku-details.component';
import { LocationComponent } from '../location/location.component';
import { LocationDetailsComponent } from '../location/location-details/location-details.component';
import { SettingsGuard } from './settings.guard';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'settings',
                component: SettingsComponent,
                canActivate: [AuthGuard, SettingsGuard],
                children: [
                    {
                        path: '',
                        redirectTo: 'sku',
                        pathMatch: 'full',
                    },
                    {
                        path: 'sku',
                        component: SkuComponent,
                        canActivate: [AuthGuard],
                    },
                    {
                        path: 'sku/:skuId',
                        component: SkuDetailsComponent,
                        canActivate: [AuthGuard],
                    },
                    {
                        path: 'location',
                        component: LocationComponent,
                        canActivate: [AuthGuard],
                    },
                    {
                        path: 'location/:locationId',
                        component: LocationDetailsComponent,
                        canActivate: [AuthGuard],
                    },
                ]
            },
        ]),
    ]
})
export class SettingsRoutingModule { }