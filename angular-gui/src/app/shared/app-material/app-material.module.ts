import { NgModule } from '@angular/core';
import {
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatExpansionModule,
    MatListModule,
    MatSidenavModule,
    MatTabsModule,
    MatSelectModule,
    MatTableModule
} from '@angular/material';

const materialModules = [
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatExpansionModule,
    MatListModule,
    MatSidenavModule,
    MatTabsModule,
    MatSelectModule,
    MatTableModule,
];

@NgModule({
    imports: materialModules,
    exports: materialModules,
})
export class AppMaterialModule { }
