package Modelo;

import java.util.ArrayList;

public class PersonajePrincipal extends Personaje {
	private static int vida ;
	private  String nombre; 
	
	public String getNombre() {
		return nombre;
		
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public static int getVida() {
		return vida;
	}
	public static void setVida(int vida) {
		PersonajePrincipal.vida = vida;
	}
	public PersonajePrincipal(String nombre , int ancho , int alto , int posX, int posY, char letra, boolean visible , boolean atravesable){
		super.setMainValues(ancho, alto, posX, posY, letra, visible , atravesable);		
		setVida(10);		
	}	
}
