import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-variables',
  templateUrl: './variables.component.html',
  styleUrls: ['./variables.component.css']
})
export class VariablesComponent implements OnInit {

	titulo:string = "El nombre de la Ciudad es: ";
	nombre:string = null;
	ciudades:string[] = [];
	contador = 0;
	listaLLena:boolean = true;

  constructor() { }

  ngOnInit() {
  }

  anadir_ciudad(){
	this.ciudades[this.contador] = this.nombre;
	this.contador++;
	this.nombre = "";
	if (this.ciudades.length==5) {
		this.listaLLena = false;
	}
}


}



