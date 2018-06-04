import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ListarPlatillosPage } from './listar-platillos';

@NgModule({
  declarations: [
    ListarPlatillosPage,
  ],
  imports: [
    IonicPageModule.forChild(ListarPlatillosPage),
  ],
})
export class ListarPlatillosPageModule {}
