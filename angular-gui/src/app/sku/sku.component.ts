import { ColumnSchema } from './../shared/column-schema.model';
import { SkuService } from './sku.service';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { SkusActions } from './store/sku.actions';
import { Sku } from './store/sku.model';
import { SkusSelectors } from './store/sku.selector';

@Component({
  selector: 'app-sku',
  templateUrl: './sku.component.html',
  styleUrls: ['./sku.component.scss']
})
export class SkuComponent implements OnInit {

  readonly columnSchema: ColumnSchema<Sku>[] = [
    { name: 'Id', param: 'id' },
    { name: 'Description', param: 'description' },
    { name: 'Name', param: 'name' },
  ];

  readonly displayedColumns = [...this.columnSchema.map(s => s.param), 'bt-remove'];

  skus$: Observable<Sku[]> = this.store.select(SkusSelectors.selectAllSkus);

  constructor(
    private readonly store: Store<{}>,
    private readonly service: SkuService,
  ) { }

  ngOnInit() {
    this.service.loadSkus()
      .subscribe(skus => this.store.dispatch(SkusActions.skusLoaded({ skus })));
  }

  removeSku(sku: Sku) {
    this.service.removeSku(sku)
      .subscribe(() => this.store.dispatch(SkusActions.skuRemoved({ sku })),
        error => alert(error.error.message),
      );
  }

}
