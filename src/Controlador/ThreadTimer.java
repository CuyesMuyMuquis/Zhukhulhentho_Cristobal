package Controlador;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Modelo.PersonajePrincipal;


public class ThreadTimer extends Thread{
	private int quitaVida ;
	private int contador ;
	private boolean estaAtrapado ; 
	private JFrame Ventana ;
	private int estado ; 
	public ThreadTimer(int quitaVida, JFrame Vent,int estado){
		super();
		this.quitaVida = quitaVida ;
		contador = 0  ; 
		estaAtrapado = true ; 
	    Ventana = Vent ; 
	    this.estado = estado ; 
	}
	public void setEstado(int estado ){
		this.estado = estado ; 
	}
	public void terminar(){
		if(estaAtrapado) estaAtrapado = false ; 		
	}
	public void run(){
		try{
			while(true){
			    ThreadTimer.sleep(1000);
				while(true){			
				    ThreadTimer.sleep(1000);
					contador++ ;			
					if (contador %2 == 0 )
					
					
					if(!estaAtrapado) break ;					
				}
			}
		} catch (InterruptedException e) {				
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
