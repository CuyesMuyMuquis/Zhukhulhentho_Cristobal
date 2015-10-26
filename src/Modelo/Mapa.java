package Modelo;


public class Mapa{
	private AccionesEspeciales acciones;
	private Celda [][] mapa = new Celda[12][16];
	private int estadoDuo ; 
	private int contador ; 
	
	public Mapa(){		//Inicializa el mapa con caracteres en blanco
		for(int fil=0;fil<12;fil++){
			for(int col=0;col<16;col++){
				mapa[fil][col] = new Celda( fil, col,' ');
			}
		}
	}
	
	//Carga combinaciones por mapa
	public void setAccionesIndividuales(String cadena){
		this.acciones.setCadenasIndividuales(cadena);
	}
	
	public void setAccionesDuos(String cadena){
		this.acciones.setCadenasDuos(cadena);
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

	public AccionesEspeciales getAcciones() {
		return acciones;
	}

	public void setAcciones(AccionesEspeciales acciones) {
		this.acciones = acciones;
	}

	public int getEstadoDuo() {
		return estadoDuo;
	}

	public void setEstadoDuo(int estadoDuo) {
		this.estadoDuo = estadoDuo;
	}
	
}
