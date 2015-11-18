package Controlador;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Modelo.PersonajePrincipal;


public class ThreadTimer extends Thread{

	private boolean estaAtrapado ; 
	private boolean matarHilo;
	private int tipo;
	private JFrame vent;
	private int quitarVida ; 
	boolean enPausa;
	int TiempoEsperaDuo; 
	
	public ThreadTimer(JFrame ventana){
	
		vent = ventana;

		estaAtrapado = false ;  
	    matarHilo = false;
	    enPausa = false;
	    tipo = -1;
	    TiempoEsperaDuo = 0;
	
	}

	public void SetTipo(int estado){
		tipo = estado;
	}
	
	public void TiempoEsperaEnDuo(int valor){
		valor *= 1000;
		TiempoEsperaDuo = valor;
	}
	
	public void cambiarEstadoPausa(){
		enPausa=!enPausa;
	}
	
	public boolean estaEnPausa(){
		return enPausa;
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
	
			while(!matarHilo){
			    ThreadTimer.sleep(1000);
				//Al entrar al Duo, Accion o mosntruo
				if(estaAtrapado){
			    	ThreadTimer.sleep(TiempoEsperaDuo);
					if(estaAtrapado){			//while(estaAtrapado){
					    //ThreadTimer.sleep(1000);			
						if(tipo == 1){
							while(estaAtrapado){
								ThreadTimer.sleep(1000);
								PersonajePrincipal.setVida(PersonajePrincipal.getVida() - 1 );
								vent.update(vent.getGraphics());
								if( PersonajePrincipal.getVida() <= 0){
									terminar();
									desactivarBajaVidas();
								}
							}
						}
							//System.out.println("vida menos");
							PersonajePrincipal.setVida(PersonajePrincipal.getVida() - quitarVida );
							vent.update(vent.getGraphics());
				
							desactivarBajaVidas();
						
						if( PersonajePrincipal.getVida() <= 0){
							terminar();
							desactivarBajaVidas();
						}						
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