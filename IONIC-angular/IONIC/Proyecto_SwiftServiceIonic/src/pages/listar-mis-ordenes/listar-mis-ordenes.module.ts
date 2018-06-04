import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ListarMisOrdenesPage } from './listar-mis-ordenes';

@NgModule({
  declarations: [
    ListarMisOrdenesPage,
  ],
  imports: [
    IonicPageModule.forChild(ListarMisOrdenesPage),
  ],
})
export class ListarMisOrdenesPageModule {}
