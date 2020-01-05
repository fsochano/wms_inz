import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderLayoutComponent } from './header-layout/header-layout.component';



@NgModule({
  declarations: [HeaderLayoutComponent],
  exports: [HeaderLayoutComponent],
  imports: [
    CommonModule
  ]
})
export class SharedModule { }
