import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EnsaladasPage } from './ensaladas';

@NgModule({
  declarations: [
    EnsaladasPage,
  ],
  imports: [
    IonicPageModule.forChild(EnsaladasPage),
  ],
})
export class EnsaladasPageModule {}
