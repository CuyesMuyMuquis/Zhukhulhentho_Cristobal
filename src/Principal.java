import javax.swing.JFrame;

import Controlador.Hilo2;
import Vista.Juego;
import Vista.Ventana;



public class Principal {

	public static void main(String[] args) {
		
		Ventana v = new Ventana() ;
		Hilo2  nuevoHilo = new Hilo2(v);
		

	}
	
}
