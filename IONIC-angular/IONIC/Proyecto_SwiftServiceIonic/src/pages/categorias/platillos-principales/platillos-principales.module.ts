import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PlatillosPrincipalesPage } from './platillos-principales';

@NgModule({
  declarations: [
    PlatillosPrincipalesPage,
  ],
  imports: [
    IonicPageModule.forChild(PlatillosPrincipalesPage),
  ],
})
export class PlatillosPrincipalesPageModule {}
