package Modelo;
import java.util.ArrayList;

public abstract class Personaje { //sin constructor

	private  int ancho ; 
	private int alto ; 
	private Posicion posicion; //Sin set y get
	private char letraAsociada ;
	private boolean visible ; 
	private boolean atravesable ;
	
	private Sprite matriz[][] ; //Sin set y get
	private ArrayList<Integer> estados ; //Sin set y get
	private int estadoActual ;
	
	
    public int getPosX(){    	
    	return posicion.getFila() ; 
    }
   	
    public int getPosY(){    	
    	return posicion.getColumna() ; 
    }
    
    public void setPosX(int posX){
    	posicion.setFila(posX);    	
    } 
    
    public void setPosY(int posY){
    	posicion.setColumna(posY);    	
    }
    
	
	public boolean getPos(int fil, int col){ //Retorna verdadero si está en la posición solicitada
		return (this.getPosicion().getFila() == fil && this.getPosicion().getColumna() == col);
	}
	
	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	
	public int getAlto() {
		return alto;
	}
	
	public void setAlto(int alto) {
		this.alto = alto;
	}

	public char getLetraAsociada() {
		return letraAsociada;
	}

	public void setLetraAsociada(char letraAsociada) {
		this.letraAsociada = letraAsociada;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isAtravesable() {
		return atravesable;
	}

	public void setAtravesable(boolean atravesable) {
		this.atravesable = atravesable;
	}

	public int getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(int estadoActual) {
		this.estadoActual = estadoActual;
	}
	
	public void agregarSprites(ArrayList<Sprite> sprites, int estado ){
		for(int i = 0 ; i<sprites.size() ; i++){
		   matriz[estado][i] = new Sprite();
		   // Aqui se debe agregar la referencia a sprite que se carga
		   
		}
	}
	
	public void setMainValues(int ancho , int alto , int posX, int posY, char letra , boolean visible , boolean atravesable ){		
		setAlto(alto);
		setAncho(ancho);	
		setPosicion(new Posicion(posX, posY)); 
		setLetraAsociada(letra);
		setVisible(visible);
		setAtravesable(atravesable);
		estados = new ArrayList<Integer>(10);
		for(int i = 0 ; i<=10 ; i++) {  // Añados estados del 0 a. 9			
			estados.add((Integer)i) ; 
		}
		// Considero 10 estados por 20 imagenes
		matriz  = new Sprite[10][20];
		estadoActual = estados.get(0) ; // estado inicial es el el valor del primer indice
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}
}
