package Controlador;

import java.io.BufferedInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import Vista.Ventana;

public class HiloMultijugador extends Thread {
	
	public Ventana miVentanaJuego ;
	private ServerSocket fServerSocket ;
	private BufferedInputStream entrada  ; 
	
	public HiloMultijugador(int puerto ,  Ventana ventanaJuego){
		super();		
		try{
			fServerSocket = new ServerSocket(puerto); 
			this.miVentanaJuego  = ventanaJuego		;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ERROR CONST HILOMULTIJUGADOR " + e.toString());			
		}		
	}
	
	public void run(){
		Socket cliente ; 
		while(true){
			if(fServerSocket == null) return ; 
			try {
				cliente = fServerSocket.accept() ; 
				InetAddress direccion = cliente.getInetAddress() ;
				entrada  = new BufferedInputStream(cliente.getInputStream()) ; 
				
				byte[] buffer = new byte[1024]  ; 
				int longitud  = entrada.read(buffer) ; 
				if (longitud>0){
					String  linea = new String(buffer);
					
					miVentanaJuego.eventoTecladoMultijadorCliente(linea.charAt(0));										
				}							
				
			} catch (Exception e) {

			}					
		}		
	}

}