import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { GlobalProvider } from '../../providers/global/global';
import { AlertController } from 'ionic-angular';
import {  PlatillosPrincipalesPage } from './platillos-principales/platillos-principales';
import { EnsaladasPage } from './ensaladas/ensaladas';
import {PostresPage } from './postres/postres';


/**
 * Generated class for the CategoriasPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-categorias',
  templateUrl: 'categorias.html',
})
export class CategoriasPage {

  platillosPrincpales = PlatillosPrincipalesPage;
  ensaladas = EnsaladasPage;
  postres = PostresPage;

  constructor(public navCtrl: NavController, public navParams: NavParams,
    public global: GlobalProvider , public alerta: AlertController) {
  }


}
