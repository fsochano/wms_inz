import { Component, OnInit, Optional, OnDestroy, Input, ViewChild } from '@angular/core';
import { MatTable, MatColumnDef, MatHeaderCellDef, MatCellDef } from '@angular/material/table';
import { ColumnSchema } from '../column-schema.model';

@Component({
  selector: 'app-simple-schema-column',
  templateUrl: './simple-schema-column.component.html',
  styleUrls: ['./simple-schema-column.component.scss']
})
export class SimpleSchemaColumnComponent implements OnInit, OnDestroy {

  @Input() column!: ColumnSchema<{}>;

  @ViewChild(MatColumnDef, {static: true}) columnDef!: MatColumnDef;
  @ViewChild(MatHeaderCellDef, {static: true}) headerCellDef!: MatHeaderCellDef;
  @ViewChild(MatCellDef, {static: true}) cellDef!: MatCellDef;

  constructor(
    @Optional() private table: MatTable<{}>,
  ) { }

  ngOnInit(): void {
    if (this.table) {
      this.columnDef.name = this.column.key;
      this.columnDef.headerCell = this.headerCellDef;
      this.columnDef.cell = this.cellDef;
      this.table.addColumnDef(this.columnDef);
    }
  }

  ngOnDestroy(): void {
    if (this.table) {
      this.table.removeColumnDef(this.columnDef);
    }
  }

}
