import { SkuService } from './sku.service';
import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../app.reducer';
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

  skus$: Observable<Sku[]> = this.store.select(SkusSelectors.selectAllSkus);

  constructor(
    private readonly store: Store<AppState>,
    private readonly service: SkuService,
  ) { }

  ngOnInit() {
    this.service.loadSkus()
    .subscribe(skus => this.store.dispatch(SkusActions.skusLoaded({ skus })));
  }

}
