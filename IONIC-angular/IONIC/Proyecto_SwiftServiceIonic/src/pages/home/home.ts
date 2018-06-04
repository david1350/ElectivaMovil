import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { ServiceProvider } from '../../providers/service-provider';
import { GlobalProvider } from '../../providers/global/global';
import { LoadingController } from 'ionic-angular';


@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {


  constructor(public navCtrl: NavController, public global: GlobalProvider, public service: ServiceProvider,
  	public loadingCtrl: LoadingController) {

  	this.getListaPlatillos();
  }


//metodo que permitetraer toda la informacion de los platillos de la BD.

 getListaPlatillos(){

 	if (this.global.platillos==null){

 		this.cargarPlatillosLista();

 	   this.service.getListaPlatillos().subscribe(
       data=>this.global.platillos = data,
       err=>console.error(err)
       );
 	}

  }


 cargarPlatillosLista() {
  	let loading = this.loadingCtrl.create({
    spinner: "bubbles",
    content: "Cargando Platillos",
    duration: 3000
   });
     loading.present();
  }


}
