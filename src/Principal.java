import javax.swing.JOptionPane;

import Controlador.HiloMultijugador;
import Vista.Juego;
import Vista.Ventana;



public class Principal {
	static final int MULTIPLAYER  = 2;
	static final int SINGLEPLAYER = 1;
		
	
	
	public static void main(String[] args) {
		
 	    /*Juego nuevoJuego = new Juego(10, 50,40) ; 
 	    nuevoJuego.iniciarPersonajes();
 	    nuevoJuego.PantallaInicial();*/
		
		Ventana.opcionJuego = MULTIPLAYER ; 
		Ventana.puerto = 15000 ; 
		Ventana.puertoDestino =  10000;
		Ventana.direccion = "127.0.0.1";
		Ventana v = new Ventana() ;
		
		if(Ventana.opcionJuego == MULTIPLAYER ){
			HiloMultijugador hm = new HiloMultijugador(Ventana.puerto, v);
			hm.start();
		}		
	}
	
}
