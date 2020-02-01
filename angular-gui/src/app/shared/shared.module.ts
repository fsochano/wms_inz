import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LayoutModule } from './layout/layout.module';
import { AppTableModule } from './table/table.module';
import { AppMaterialModule } from './app-material/app-material.module';


@NgModule({
  declarations: [],
  exports: [
    AppMaterialModule,
    ReactiveFormsModule,
    LayoutModule,
    AppTableModule,
    HttpClientModule,
  ],
  imports: [
    CommonModule,
    AppMaterialModule,
    ReactiveFormsModule,
    LayoutModule,
    AppTableModule,
    HttpClientModule,
  ]
})
export class SharedModule { }
