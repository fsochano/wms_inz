import { AppMaterialModule } from './app-material.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout/layout.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [LayoutComponent],
  exports: [
    LayoutComponent,
    AppMaterialModule,
    ReactiveFormsModule,
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
    ReactiveFormsModule,
  ]
})
export class SharedModule { }
