package Controlador;

import javax.swing.JOptionPane;

import Vista.Ventana;
import Vista.Ventana.pantallaActual;


public class Hilo2 extends Thread{
	Ventana ventana ;
	
	public Hilo2(Ventana vent){
		ventana = vent ;
		vent.tiempo  =0  ;
		vent.setearHilo(this);
	}
	 
	public void run(){
		while(true){
			int t = ventana.aumentarTiempo();
			try {
				sleep( 1000  );
				System.out.println("Tiempo "+ t);				
				ventana.dibujarTiempo(t);		
				if (t > ventana.TiempoLimite) break ; 
					
				
			}
			catch( InterruptedException e )
			{
				System.out.println(e.toString());
			}						
		}
		JOptionPane.showMessageDialog(null,"You lost termino el juego");
		Ventana.setNumeroPantalla(pantallaActual.FIN_DEL_JUEGO.ordinal());		
		ventana.IniciarPantalla();
		//ventana.repaint();
	}
}