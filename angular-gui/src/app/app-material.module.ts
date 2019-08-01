import { NgModule } from '@angular/core';
import { MatCardModule, MatInputModule, MatButtonModule } from '@angular/material';

const materialModules = [
    MatCardModule,
    MatInputModule,
    MatButtonModule,
]

@NgModule({
    imports: materialModules,
    exports: materialModules,
  })
  export class AppMaterialModule { }
