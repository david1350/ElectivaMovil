import { BrowserModule } from '@angular/platform-browser';
import { ErrorHandler, NgModule } from '@angular/core';
import { IonicApp, IonicErrorHandler, IonicModule } from 'ionic-angular';

import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import { CategoriasPage } from '../pages/categorias/categorias';
import { PlatillosPrincipalesPage } from '../pages/categorias/platillos-principales/platillos-principales';
import { EnsaladasPage } from "../pages/categorias/ensaladas/ensaladas";
import { PostresPage } from "../pages/categorias/postres/postres";

import { ListarPlatillosPage } from '../pages/listar-platillos/listar-platillos';
import { RealizarOrdenPage } from '../pages/realizar-orden/realizar-orden';
import { ListarMisOrdenesPage } from '../pages/listar-mis-ordenes/listar-mis-ordenes';
import { AcercaDePage } from '../pages/acerca-de/acerca-de';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import {HttpModule} from "@angular/http";
import { HttpClientModule} from '@angular/common/http';
import { ServiceProvider } from '../providers/service-provider';
import { GlobalProvider } from '../providers/global/global';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
    CategoriasPage,
    PlatillosPrincipalesPage,
    EnsaladasPage,
    PostresPage,

    ListarPlatillosPage,
    RealizarOrdenPage,
    ListarMisOrdenesPage,
    AcercaDePage

  ],
  imports: [
    BrowserModule,
    HttpModule,
    HttpClientModule,
    IonicModule.forRoot(MyApp),
  ],
  bootstrap: [IonicApp],

  entryComponents: [
    MyApp,
    HomePage,
    CategoriasPage,
    PlatillosPrincipalesPage ,
    EnsaladasPage,
    PostresPage,

    ListarPlatillosPage,
    RealizarOrdenPage,
    ListarMisOrdenesPage,
    AcercaDePage
  ],
  providers: [
    ListarPlatillosPage,
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
     ServiceProvider,
    GlobalProvider
  ]
})
export class AppModule {}
