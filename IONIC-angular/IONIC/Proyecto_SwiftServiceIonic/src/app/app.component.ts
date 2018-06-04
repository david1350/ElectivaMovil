import { Component, ViewChild } from '@angular/core';
import { Nav, Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { HomePage } from '../pages/home/home';
import { CategoriasPage } from '../pages/categorias/categorias';
import { ListarPlatillosPage } from '../pages/listar-platillos/listar-platillos';
import { RealizarOrdenPage } from '../pages/realizar-orden/realizar-orden';
import { ListarMisOrdenesPage } from '../pages/listar-mis-ordenes/listar-mis-ordenes';
import { AcercaDePage } from '../pages/acerca-de/acerca-de';


@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;

  rootPage: any = HomePage;

  pages: Array<{title: string, component: any}>;

  constructor(public platform: Platform, public statusBar: StatusBar, public splashScreen: SplashScreen) {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [
      { title: 'Inicio', component: HomePage },
      { title: 'Categorias', component: CategoriasPage },
      { title: 'Listar platillos', component: ListarPlatillosPage },
      { title: 'Realizar orden', component: RealizarOrdenPage },
      { title: 'Listar mis ordenes', component: ListarMisOrdenesPage },
      { title: 'Acerca de', component: AcercaDePage }

    ];

  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  openPage(page) {
    this.nav.setRoot(page.component);
  }



}
