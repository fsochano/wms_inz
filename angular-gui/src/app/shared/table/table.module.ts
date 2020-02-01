import { ParamExtractorPipe } from './param-extractor.pipe';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SimpleSchemaColumnComponent } from './simple-schema-column/simple-schema-column.component';
import { MatTableModule } from '@angular/material';

@NgModule({
  declarations: [
    ParamExtractorPipe,
    SimpleSchemaColumnComponent,
  ],
  exports: [
    SimpleSchemaColumnComponent,
    ParamExtractorPipe,
  ],
  imports: [
    CommonModule,
    MatTableModule
  ]
})
export class AppTableModule { }
