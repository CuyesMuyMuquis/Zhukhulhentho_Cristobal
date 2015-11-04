package Controlador;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Modelo.PersonajePrincipal;


public class ThreadTimer extends Thread{

	private int contador ;
	private boolean estaAtrapado ; 
	private boolean matarHilo;
	private JFrame vent;
	private int quitarVida ; 
	
	public ThreadTimer(JFrame ventana){
	
		vent = ventana;
		contador = 0  ; 
		estaAtrapado = false ;  
	    matarHilo = false;
	
	}

	public void setearQuitarVida(int quitaVida){
		this.quitarVida = quitaVida ; 
	}
	public void activarBajaVidas(){
		estaAtrapado = true;
	}
	
	public void desactivarBajaVidas(){
		estaAtrapado = false;
	}
	
	public void terminar(){
		matarHilo = true;		
	}
	
	public void run(){
		try{
			contador = 0;
			while(!matarHilo){
			    ThreadTimer.sleep(1000);
		
				while(estaAtrapado){
				    ThreadTimer.sleep(1000);
					contador++ ;			
					if (contador % 2 == 0 ){
						//System.out.println("vida menos");
						PersonajePrincipal.setVida(PersonajePrincipal.getVida() - quitarVida );
						vent.update(vent.getGraphics());
					}
					if( PersonajePrincipal.getVida() == 0){
						terminar();
						desactivarBajaVidas();
					}
					
				}
			}
		}catch (InterruptedException e) {				
			e.printStackTrace();
		}
		/*while(estaAtrapado){
			try {
				sleep(1000);
				contador++ ;
				if (contador %2 == 0 && PersonajePrincipal.getVida() >=0){
					PersonajePrincipal.setVida(PersonajePrincipal.getVida()-quitaVida);
					JOptionPane.showMessageDialog(null,PersonajePrincipal.getVida());
					Ventana.update(Ventana.getGraphics());
				} 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}
			
		}*/		
	}
}