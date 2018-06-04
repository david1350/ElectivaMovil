import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';

/**
 * Generated class for the Pagina2Page page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-pagina2',
  templateUrl: 'pagina2.html',
})
export class Pagina2Page {

	nombre:string ="";

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  	this.nombre = this.navParams.get("nombre");
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad Pagina2Page');
  }


 irAtras(){
 	this.navCtrl.pop();
 }

 irRoot(){
 	this.navCtrl.popToRoot();
 }


}
