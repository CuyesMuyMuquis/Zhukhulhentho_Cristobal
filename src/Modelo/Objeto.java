package Modelo;

import java.util.ArrayList;

public class Objeto {
	
	   private boolean  visible; 
	   private boolean  atravesable;
	   
	   private int posX; 
	   private int posY;
	   
	   private Sprite matriz[][] ;
	   private ArrayList<Integer> estados;  
	   private int estadoActual;
	   
	   
	   public Objeto(boolean visible , boolean atravesable , int posX , int posY){ 
		   // Constructor 
		   this.visible = visible ; 
		   this.atravesable = atravesable ; 
		   this.posX = posX ; 
		   this.posY = posY ; 	   
		   estados = new ArrayList<Integer>(10);
		   for(int i = 0 ; i<=10 ; i++) getEstados().add(i) ;   // Añados estados del 0 a. 9  
			
		    // Considero 10 estados por 20 imagenes
			matriz  = new Sprite[10][20];
			
			setEstadoActual(getEstados().get(0)) ; // estado inicial es el el valor del priver indice
	   }
	   public void SetAllValues(boolean visible , boolean atravesable , int posX , int posY) {
		   // Seteo de todos los valores 
		   setPosX(posX);
		   setPosY(posY);
		   setAtravesable(atravesable);
		   setVisible(visible);	   
	   }   
	   
	   public void agregarSprites(ArrayList<Sprite> sprites, int estado ){
			for(int i = 0 ; i<sprites.size() ; i++){
			   matriz[estado][i] = new Sprite();
			   // Aqui se debe agregar la referencia a sprite que se carga
			   
			}
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
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public ArrayList<Integer> getEstados() {
		return estados;
	}
	public void setEstados(ArrayList<Integer> estados) {
		this.estados = estados;
	}
	public int getEstadoActual() {
		return estadoActual;
	}
	public void setEstadoActual(int estadoActual) {
		this.estadoActual = estadoActual;
	}
	public Sprite[][] getMatriz() {
		return matriz;
	}
	public void setMatriz(Sprite matriz[][]) {
		this.matriz = matriz;
	}
	   
	   
}
