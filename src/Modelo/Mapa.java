package Modelo;

import java.util.ArrayList;

public class Mapa{
	
	private ArrayList<AccionesEspeciales> listaAcciones;	
	private Celda [][] mapa = new Celda[12][16];
	private int estadoDuo; 
	private int contador; 
	
	public Mapa(){
		setListaAcciones(new ArrayList<AccionesEspeciales>());
		for(int fil=0;fil<12;fil++){
			for(int col=0;col<16;col++){
				mapa[fil][col] = new Celda( fil, col,' ');
			}
		}
	}
	
	public Mapa(int nivel){		//Inicializa el mapa con caracteres en blanco
		setListaAcciones(new ArrayList<AccionesEspeciales>());
		if(nivel == 0){ //Tutorial
			agregarCombinacion(0,  5,5,     9,5,"WSIKDDLL");
			agregarCombinacion(1,  3,12,    9,11,"WDEWW");
			agregarCombinacion(1,  4,12,    9,11,"WDEWW");
			agregarCombinacion(1,  5,12    ,9,11,"WDEWW");
			agregarCombinacion(1,  6,12    ,9,11,"WDEWW");
		}
		if(nivel == 1){ //Nivel 1 //Cuy_1  //Cuy_2
			agregarCombinacion(0,   8,5,    10,5,"IWOELD");
			agregarCombinacion(0,   8,10,   10,10,"WUOQEI");
		}
		if(nivel == 2){ //Nivel 2
			agregarCombinacion(0,   5,9,    7,9, "WQED");
			agregarCombinacion(1,  -1,-1,   9,3,"IUOL");
			agregarCombinacion(1,   3,3,    -1,-1,"LDOEQUOE");
		}
		for(int fil=0;fil<12;fil++){
			for(int col=0;col<16;col++){
				mapa[fil][col] = new Celda( fil, col,' ');
			}
		}
		
	}
	
	public void agregarCombinacion(int tipo, int posXC1, int posYC1, int posXC2, int posYC2, String combinacion){
		AccionesEspeciales accionNueva = new AccionesEspeciales(tipo, posXC1, posYC1, posXC2, posYC2, combinacion);
		getListaAcciones().add(accionNueva);
	}
	
	//funcion 
	public void establecerCaracter(int i, int j, char x){
		mapa[i][j].setCaracter(x);
	}
	
	public char obtenerCaracter(int fil, int col){
		return mapa[fil][col].getCaracter();
	}
	
	public void ImprimirMapa(){ //Imprime mapa para verificar que estaba bien cargado
		for(int fil=0;fil<12;fil++){
			for(int col=0;col<16;col++){
				System.out.print(mapa[fil][col].getCaracter());
			}
			System.out.println();
			}
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public int getEstadoDuo() {
		return estadoDuo;
	}

	public void setEstadoDuo(int estadoDuo) {
		this.estadoDuo = estadoDuo;
	}
	
	public void cargarImagenes(){
		
	}

	public ArrayList<AccionesEspeciales> getListaAcciones() {
		return listaAcciones;
	}

	public void setListaAcciones(ArrayList<AccionesEspeciales> listaAcciones) {
		this.listaAcciones = listaAcciones;
	}
	
}
