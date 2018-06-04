import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GlobalProvider } from '../../../providers/global/global';
import { AlertController } from 'ionic-angular';
/**
 * Generated class for the PlatillosPrincipalesPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-platillos-principales',
  templateUrl: 'platillos-principales.html',
})
export class PlatillosPrincipalesPage {

  platillosPrincipales:any;

  constructor(public navCtrl: NavController, public navParams: NavParams,
  public global: GlobalProvider , public alerta: AlertController) {
  }
  

  anadirPlatillo(platillo:any){

    const confirmar = this.alerta.create({
      title: 'Confirmación Plato',
      message: '¿Desea añadir plato a la orden?',
      buttons: [
      {
        text: 'Cancelar',
        handler: ()=>{

        }
      },
      {
        text: 'Confirmar',
        handler: ()=>{
           this.global.listaOrden.push(platillo);
        }
      }]
    });
      confirmar.present();
  }


}
