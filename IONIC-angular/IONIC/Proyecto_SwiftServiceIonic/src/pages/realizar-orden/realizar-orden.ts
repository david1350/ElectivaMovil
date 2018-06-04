import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GlobalProvider } from '../../providers/global/global';
import { AlertController } from 'ionic-angular';
/**
 * Generated class for the RealizarOrdenPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-realizar-orden',
  templateUrl: 'realizar-orden.html',
})
export class RealizarOrdenPage {

	date = new Date();
  costoPedido:number;

  constructor(public navCtrl: NavController, public navParams: NavParams,
  	public global: GlobalProvider, public alerta: AlertController) {
  	console.log(this.global.listaOrden.length);
    this.costoPedido = 0;
  }


   puedeHacerPedido (){
    if (this.global.listaOrden.length>=1){
      return true;
    }
    return false;
  }


  removerPlatillo(i:number){

    const confirmar = this.alerta.create({
      title: 'Eliminar Plato',
      message: '¿Desea eliminar plato de la orden?',
      buttons: [
      {
        text: 'Atrás',
        handler: ()=>{
        }
      },
      {
        text: 'Remover',
        handler: ()=>{
           this.global.listaOrden.splice(i,1);
        }

      }]
    });
      confirmar.present();
  }


}
