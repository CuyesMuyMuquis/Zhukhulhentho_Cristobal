package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.ThreadTimer;

import java.awt.Toolkit;
import Modelo.Mapa;
import Modelo.Personaje;
import Modelo.PersonajePrincipal;
import Modelo.Posicion;

import Modelo.Serializar2;
import Modelo.StoredGame;


public class Ventana extends JFrame implements Renderizador{
	private static final long serialVersionUID = 1L;
	private static Toolkit tol = Toolkit.getDefaultToolkit ();
	static final int MULTIPLAYER  = 2;
	static final int SINGLEPLAYER = 1;
	public static int puerto ;
	public static String direccion ;
	public static int opcionJuego ;
	public static int posMapa = 0;
	
	private  BufferedImage gif, gif2 ; // = ImageIO.read(new File("cuy_1.jpg"));	
	
	

	private  Juego nuevoJuego ;
	private static final int TILE = 64;
	private static final int ANCHO_R = 1024 ;
	private static final int ANCHO_L = 300 ;
	private static final int ALTO_BARRA_MENU = 20  ;
	private static final int ALTO = 768  ;
	private boolean limpiar; 
	private static int numeroPantalla = 0;
	private static int numDuo=1;
	private int estado=-1;
	private String teclaPres="";
	public static int numeroPuertoJugador1 ;
	public static int puertoDestino; 	
	private enum pantallaActual {
	    MENU, HISTORIA_1, TUTORIAL, HISTORIA_2,
	    NIVEL_1, HISTORIA_3, NIVEL_2,PERDIO_JUEGO,FIN_DEL_JUEGO;
	}
	
	
	//INICIO
	private BufferedImage  imgFondo;
	private BufferedImage  imgLateral;
	private BufferStrategy bufferStrategy;		
	private BufferedImage corazon;
	private BufferedImage mostro;
	private ThreadTimer timer;
	
	
	
	//HISTORIA1 
	private BufferedImage fondoHistoria;
	
	// Nivel1
	private  Tutorial  tutorial = new Tutorial() ;
	private Historia_2 historia_2 = new Historia_2();
	private Nivel_1 nivel_1 = new Nivel_1();
	private Historia_3 historia_3 = new Historia_3();
	private Nivel_2 nivel_2 = new Nivel_2();
	private Perdio_juego perdio_juego = new Perdio_juego();
	private Fin_del_juego fin_del_juego  = new Fin_del_juego();
	
	public void imprimeEnPantallaLateral(int estado, int numMapa){
		int mapaReal = 0;		
		Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();
		graph2D.setFont(new Font("Monspaced", Font.BOLD, 26));
		graph2D.setColor(Color.WHITE);
		int vida = PersonajePrincipal.getVida();
		System.out.println(vida);
		graph2D.drawImage( imgLateral, ANCHO_R, ALTO_BARRA_MENU , this);
		//graph2D.clearRect(ANCHO_R+85, 74, 70, 28);
		graph2D.drawImage( corazon, ANCHO_R+35, 36 , this);

		//graph2D.drawString("VIDA: ", ANCHO_R+10, 100);
		if( vida == 10){
			graph2D.drawString("" + vida, ANCHO_R+52, 80);
		}else{
			graph2D.drawString("" + vida, ANCHO_R+60, 80);
		}
		
		if(numMapa == 2){
			mapaReal = 0;
		}else{
			if(numMapa == 3){
				posMapa = 0;
			}
			if(numMapa == 4){
				mapaReal = 1;
			}else{
				if(numMapa == 5){
					posMapa = 0;
				}
				if(numMapa == 6){
					mapaReal = 2;
				}
			}
		}
			
		if(estado == 0||estado==1){					
				//graph2D.clearRect(ANCHO_R+10, 120, 250, 56);
				//graph2D.clearRect(ANCHO_R+10, 170, 250, 56);
			
                String combinacion = Ventana.this.nuevoJuego.getListMapas().get(mapaReal).getListaAcciones().get(posMapa).getCombinacion();
                Font fuente=new Font("Monospaced", Font.BOLD, 26);
                graph2D.setFont(fuente);
                graph2D.setColor(Color.WHITE);
                graph2D.drawString("Debe presionar:", ANCHO_R+10, 150);
                fuente.deriveFont(36);	                
                graph2D.drawString(combinacion, ANCHO_R+10, 170);
                //LO que se presiona
                fuente.deriveFont(26);
                graph2D.drawString("Has presionado: ", ANCHO_R+10, 200);
                fuente.deriveFont(36);
                if( teclaPres == ""){
                	//graph2D.clearRect(ANCHO_R+10, 200, 250, 28);
                }else{
                	graph2D.drawString(teclaPres, ANCHO_R+10, 220);
                }      
                System.out.println("Aca deberia imprimir"+teclaPres);
		}
		//else{
			//graph2D.clearRect(ANCHO_R+10, 120, 250, 56);
			//graph2D.clearRect(ANCHO_R+10, 170, 250, 56);
		//}
		
		//bufferStrategy.show();
		//Ventana.this.repaint();
	}
	
	public void quitarVida(char apretado){
		if (Character.isLetter(apretado)){
			PersonajePrincipal.setVida(PersonajePrincipal.getVida()-2);
		}
	}
	public void salvarJuego(){
		StoredGame game = new StoredGame(Ventana.this.nuevoJuego.getPersonajeA(),Ventana.this.nuevoJuego.getPersonajeB(),getNumeroPantalla(),posMapa);
		Serializar2 serial = new Serializar2();
		serial.Guardar(game);		
	}
	public void Ventana_Nivel_1(char letra){
		
		estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(1));
		if(estado==-1){
			Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() ,letra,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(1));
			Ventana.this.repaint();// actualizar
			estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(1));
		}else if(estado == 0){ 
			
		    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(1));
			System.out.print(segundos);
			timer.TiempoEsperaEnDuo(segundos);
			
			timer.setearQuitarVida(2);
			timer.activarBajaVidas();
			System.out.println("DUO");
			teclaPres=teclaPres+letra;					
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(1));
			//JOptionPane.showMessageDialog(null,teclaPres);
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			
			//AGREGAR HILO DE ERROR
			//AGREGAR HILO DE TIEMPO
			
			//JOptionPane.showMessageDialog(null,codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){							
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
					if(numDuo==1){
					Ventana.this.nuevoJuego.ImprimirDuo1_1(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					posMapa++;
					numDuo++;
					}else{
					if(numDuo==2){
						Ventana.this.nuevoJuego.ImprimirDuo1_2(Ventana.this.nuevoJuego.getListMapas().get(1), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
						Ventana.this.repaint();
						posMapa++;
						}
					}
					/*
				Ventana.this.nuevoJuego.ImprimirDuo_t_1(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.update((Graphics2D)Ventana.this.getGraphics());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Ventana.this.nuevoJuego.imprimirDuo_t_2(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}

			Ventana.this.repaint();
				System.out.println(estado);



		}else if(estado==1){
				
		}else if(estado==3){
				setNumeroPantalla(pantallaActual.PERDIO_JUEGO.ordinal());
		}else if(estado ==2){
			int t = getNumeroPantalla();
		    setNumeroPantalla(getNumeroPantalla() + 1);
			System.out.println(getNumeroPantalla());
			estado=-1;
			System.out.println("Estoy afuera");
			Ventana.this.update(Ventana.this.getGraphics());
			Ventana.this.IniciarPantalla();
		}
	}
	public void Ventana_Nivel_2(char letra){
		
	estado=Ventana.this.nuevoJuego.nivel2_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(2));	
	
	if(estado==-1){
		timer.desactivarBajaVidas();
		Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() ,letra,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(2));
		Ventana.this.repaint();// actualizar
		estado=Ventana.this.nuevoJuego.nivel2_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(2));	
	}else if(estado == 0){ 
		posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
	    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(2));
		System.out.print(segundos);
		timer.TiempoEsperaEnDuo(segundos);
		
		timer.setearQuitarVida(2);
		timer.activarBajaVidas();
		System.out.println("DUO");
		
		teclaPres=teclaPres+letra;					
		String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
		
		int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
		
		if(resultado !=-1){
			if (teclaPres.equals(codigoExtraido)){							
				timer.desactivarBajaVidas();
				estado = -1 ; // Cambio el estado para salir del DUO o Accion.
				teclaPres = "" ;
				Ventana.this.nuevoJuego.ImprimirDuo2(Ventana.this.nuevoJuego.getListMapas().get(2), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.repaint();
				//posMapa++;
			}
		}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
			teclaPres = "";
			//EQUIVOCACIÓN DE TECLAS PRESIONADAS
			quitarVida(letra);						
		}
		//imprimeEnPantallaLateral(estado);
		Ventana.this.repaint();
			System.out.println(estado);
			
	}else if(estado==1){
		//CAMBIO DE LA VIDA
		posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
		timer.SetTipo(estado);
		int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(2));
		System.out.print(segundos);
		timer.TiempoEsperaEnDuo(segundos);
		
		timer.setearQuitarVida(2);
		timer.activarBajaVidas();
		int subEstado=Ventana.this.nuevoJuego.inmoviliza_cuy2(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(2));
		System.out.println("==================");
		System.out.println(subEstado);
		System.out.println("==================");
		if(subEstado==0){//CUY1 HACE ACCION ESPECIAL
			
			teclaPres=teclaPres+letra;		
			//posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){
					timer.SetTipo(-1);
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
				
					Ventana.this.nuevoJuego.ImprimeAccion2_1(Ventana.this.nuevoJuego.getListMapas().get(2), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					//posMapa++;
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}
		}
		if(subEstado==1){//CUY2 debe hacer su accion especial)
			//posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			teclaPres=teclaPres+letra;	
			//posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){
					timer.SetTipo(-1);
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
					Ventana.this.nuevoJuego.ImprimeAccion2_2(Ventana.this.nuevoJuego.getListMapas().get(2), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					//posMapa++;
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}	
			
		}
		
		Ventana.this.repaint();

	}else if(estado==2){
		    setNumeroPantalla(pantallaActual.FIN_DEL_JUEGO.ordinal());
		    //int t = getNumeroPantalla();
		    //setNumeroPantalla(getNumeroPantalla() + 1);
			System.out.println(getNumeroPantalla());
			estado=-1;
			System.out.println("Estoy afuera");
			Ventana.this.update(Ventana.this.getGraphics());
			Ventana.this.IniciarPantalla();
		    
	}else if(estado==3){
			setNumeroPantalla(pantallaActual.PERDIO_JUEGO.ordinal());
	}
		
	}
	public void Ventana_Tutorial(char letra ){
		//imprimeEnPantallaLateral(estado);
		// -1 -> no pasa nada. 0 -> duo. 1 -> accionEspecial. 2 -> acabo Nivel. 3 -> has perdido.
		// SE DEBE CAMBIAR A:    -1 -> no pasa nada.
		//					  0 -> duo. 
		//                    1 -> acciontriger.
		// 					  3 -> acabo Nivel.
		// 				      4 -> has perdido.
		////////////////////////////////////////////////////////
		estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(0));
		System.out.println(estado);
		////////////////////////////////////////////////////////
		if(estado==-1){
			timer.desactivarBajaVidas();
			Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() ,letra,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(0));
			Ventana.this.repaint();// actualizar
		}else if(estado == 0){ // Duo 
			
			//Tiempo de espera para completar el duo (en segundos)
		    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(0));
			System.out.print(segundos);
			timer.TiempoEsperaEnDuo(segundos);
			
			timer.setearQuitarVida(2);
			timer.activarBajaVidas();
			System.out.println("DUO");
			//imprimeEnPantallaLateral(estado);
			teclaPres=teclaPres+letra;					
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(0));
			//JOptionPane.showMessageDialog(null,teclaPres);
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			
			//AGREGAR HILO DE ERROR
			//AGREGAR HILO DE TIEMPO
			
			//JOptionPane.showMessageDialog(null,codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){							
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
					Ventana.this.nuevoJuego.ImprimirDuo(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					posMapa++;
					/*
				Ventana.this.nuevoJuego.ImprimirDuo_t_1(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.update((Graphics2D)Ventana.this.getGraphics());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Ventana.this.nuevoJuego.imprimirDuo_t_2(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}
			//imprimeEnPantallaLateral(estado);
			Ventana.this.repaint();
				System.out.println(estado);



		}else if(estado==1){
			timer.SetTipo(estado);
			//Tiempo de espera para completar la accion (en segundos)
		    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(0));
			System.out.print("Segundos:"+segundos);
			timer.TiempoEsperaEnDuo(segundos);
			
			timer.setearQuitarVida(1);
			timer.activarBajaVidas();
			int subEstado=Ventana.this.nuevoJuego.inmoviliza_cuy(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(0));
			if(subEstado==0){//no se puede mover el cuy 1
				if(letra!='w'&&letra!='W'&&letra!='a'&&letra!='A'&&letra!='s'&&letra!='S'&& letra!='d'&&letra!='D'){
					Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() , letra ,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(0));
					Ventana.this.repaint();// acutlizar
				}
			}
			if(subEstado==1){//no se puede mover el cuy 2
					if(letra!='i'&& letra!='I'&&letra!='j'&&letra!='J'&& letra!='k'&&letra!='K'&& letra!='l'&& letra!='L'){
						Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() , letra ,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(0));
						
						Ventana.this.repaint();// acutlizar
					}
				
			}
			if(subEstado==-1){//el cuy libre se encuentra en una posicion para liberar al otro cuy
				//SI TIENE EXITO
					
					System.out.println("Accion");
					//imprimeEnPantallaLateral(estado);
					teclaPres=teclaPres+letra ; 					
					String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(0));
					//JOptionPane.showMessageDialog(null,teclaPres);
					System.out.println(codigoExtraido);
					int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
				
					//AGREGAR HILO DE ERROR
					//AGREGAR HILO DE TIEMPO
				
					//JOptionPane.showMessageDialog(null,codigoExtraido);
					if(resultado !=-1){
						if (teclaPres.equals(codigoExtraido)){
							timer.desactivarBajaVidas();
							timer.SetTipo(-1);
							estado = -1 ; // Cambio el estado para salir del DUO o Accion.
							teclaPres = "" ;
							Ventana.this.nuevoJuego.ImprimeAccion(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
							Ventana.this.repaint();
						}
						}else {
							//SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
							teclaPres = "";
							//EQUIVOCACIÓN DE TECLAS PRESIONADAS
							quitarVida(letra);		
					
						}
						Ventana.this.nuevoJuego.cambiaCaracterEnMapa(Ventana.this.nuevoJuego.getListMapas().get(0));
						
					}
					Ventana.this.repaint();

		}else if(estado==3){
			//nuevoJuego.getPersonajeA().setPosY(nuevoJuego.getPersonajeA().getPosY() + 1 );
			setNumeroPantalla(pantallaActual.PERDIO_JUEGO.ordinal());
			JOptionPane.showMessageDialog(null,"Adios");
			Ventana.this.IniciarPantalla();
		}				
		estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(0));		
		if(estado ==2){
			int t = getNumeroPantalla();
		    setNumeroPantalla(getNumeroPantalla() + 1);
			System.out.println(getNumeroPantalla());
			estado=-1;
			System.out.println("Estoy afuera");
			Ventana.this.update(Ventana.this.getGraphics());
			Ventana.this.IniciarPantalla();
		}
		
		System.out.println(estado);
		/*	
		int direccion = Ventana.this.nuevoJuego.getInterpreteComando().esTeclaValida(e.getKeyChar());	

				System.out.println("Per1 FILA COLUMN A" +per1.getPosX() +" " +  per1.getPosY() + "\nPer2 FILA COLUMNA " + per2.getPosX() + " "+ per2.getPosY());
			if (Ventana.this.nuevoJuego.getInterpreteComando().movimientoValido(per1 , per2 , direccion ,
				Ventana.this.nuevoJuego.getListMapas().get(0))){
				Ventana.this.nuevoJuego.getListMapas().get(0).ImprimirMapa();
				Ventana.this.nuevoJuego.getInterpreteComando().moverPersonajes(per1 , per2, direccion);
				System.out.println("Per1 FILA COLUMN A" +per1.getPosX() +" " +  per1.getPosY() + "\nPer2 FILA COLUMNA " + per2.getPosX() + " "+ per2.getPosY());
			}
		 */	
		//if(Ventana.this.nuevoJuego.getInterpreteComando().movimientoValido(per1, per2, direccion, mapa))

		
	}
	public void eventoTecladoMultijadorCliente(char letra ){
					
		
		if( letra == 'g' ||letra == 'G'){
			salvarJuego();
			JOptionPane.showMessageDialog(null, "El juego se ha guardado");
			return ;					
		}				
		if (getNumeroPantalla() == pantallaActual.TUTORIAL.ordinal() ){
			Ventana_Tutorial(letra);					
		}else if (getNumeroPantalla() == pantallaActual.NIVEL_1.ordinal()){
			Ventana_Nivel_1(letra);
		}
		else if(getNumeroPantalla() == pantallaActual.NIVEL_2.ordinal()){
			Ventana_Nivel_2(letra);
		}
		
	}
	public void eventoTeclado(){
	     this.addKeyListener(new KeyAdapter(){		    	
			@Override
			public void keyPressed(KeyEvent e) {
				
				char letra = Character.toUpperCase(e.getKeyChar());								
				if( letra == 'g' ||letra == 'G'){
					salvarJuego();
					JOptionPane.showMessageDialog(null, "El juego se ha guardado");
					return ;					
				}				
				if (getNumeroPantalla() == pantallaActual.TUTORIAL.ordinal() ){
					if (opcionJuego == MULTIPLAYER )
						Ventana_Tutorial(letra,direccion, puertoDestino);
					else if (opcionJuego == SINGLEPLAYER )
						Ventana_Tutorial(letra);					
				}else if (getNumeroPantalla() == pantallaActual.NIVEL_1.ordinal()){
					if (opcionJuego == MULTIPLAYER )
						Ventana_Nivel_1(letra,direccion, puertoDestino);
					else if (opcionJuego == SINGLEPLAYER )
						Ventana_Nivel_1(letra);
				}
				else if(getNumeroPantalla() == pantallaActual.NIVEL_2.ordinal()){
					if (opcionJuego == MULTIPLAYER )
						Ventana_Nivel_2(letra, direccion, puertoDestino);
					else if (opcionJuego == SINGLEPLAYER )
						Ventana_Nivel_2(letra);
						
				}
			}
	     });
		
	}
	
	protected void Ventana_Nivel_2(char letra, String direccion2, int puertoDestino2) {
		
	estado=Ventana.this.nuevoJuego.nivel2_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(2));	
	enviarMensaje(direccion2 , puertoDestino2 , letra);
	if(estado==-1){
		timer.desactivarBajaVidas();
		Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() ,letra,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(2));
		Ventana.this.repaint();// actualizar
		estado=Ventana.this.nuevoJuego.nivel2_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(2));	
	}else if(estado == 0){ 
		posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
	    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(2));
		System.out.print(segundos);
		timer.TiempoEsperaEnDuo(segundos);
		
		timer.setearQuitarVida(2);
		timer.activarBajaVidas();
		System.out.println("DUO");
		
		teclaPres=teclaPres+letra;					
		String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
		
		int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
		
		if(resultado !=-1){
			if (teclaPres.equals(codigoExtraido)){							
				timer.desactivarBajaVidas();
				estado = -1 ; // Cambio el estado para salir del DUO o Accion.
				teclaPres = "" ;
				Ventana.this.nuevoJuego.ImprimirDuo2(Ventana.this.nuevoJuego.getListMapas().get(2), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.repaint();
				//posMapa++;
			}
		}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
			teclaPres = "";
			//EQUIVOCACIÓN DE TECLAS PRESIONADAS
			quitarVida(letra);						
		}
		//imprimeEnPantallaLateral(estado);
		Ventana.this.repaint();
			System.out.println(estado);
			
	}else if(estado==1){
		//CAMBIO DE LA VIDA
		posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
		timer.SetTipo(estado);
		int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(2));
		System.out.print(segundos);
		timer.TiempoEsperaEnDuo(segundos);
		
		timer.setearQuitarVida(2);
		timer.activarBajaVidas();
		int subEstado=Ventana.this.nuevoJuego.inmoviliza_cuy2(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(2));
		System.out.println("==================");
		System.out.println(subEstado);
		System.out.println("==================");
		if(subEstado==0){//CUY1 HACE ACCION ESPECIAL
			
			teclaPres=teclaPres+letra;		
			//posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){
					timer.SetTipo(-1);
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
				
					Ventana.this.nuevoJuego.ImprimeAccion2_1(Ventana.this.nuevoJuego.getListMapas().get(2), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					//posMapa++;
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}
		}
		if(subEstado==1){//CUY2 debe hacer su accion especial)
			//posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			teclaPres=teclaPres+letra;	
			//posMapa = Ventana.this.nuevoJuego.buscaCodigo2(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(2));
			
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){
					timer.SetTipo(-1);
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
					Ventana.this.nuevoJuego.ImprimeAccion2_2(Ventana.this.nuevoJuego.getListMapas().get(2), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					//posMapa++;
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}	
			
		}
		
		Ventana.this.repaint();

	}else if(estado==2){
		    setNumeroPantalla(pantallaActual.FIN_DEL_JUEGO.ordinal());
		    //int t = getNumeroPantalla();
		    //setNumeroPantalla(getNumeroPantalla() + 1);
			System.out.println(getNumeroPantalla());
			estado=-1;
			System.out.println("Estoy afuera");
			Ventana.this.update(Ventana.this.getGraphics());
			Ventana.this.IniciarPantalla();
		    
	}else if(estado==3){
			setNumeroPantalla(pantallaActual.PERDIO_JUEGO.ordinal());
	}
	
		
	}

	protected void Ventana_Nivel_1(char letra, String direccion2, int puertoDestino2) {
		
		estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(1));
		enviarMensaje(direccion2 , puertoDestino2 , letra);
		if(estado==-1){
			Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() ,letra,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(1));
			Ventana.this.repaint();// actualizar
			estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(1));
		}else if(estado == 0){ 
			
		    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(1));
			System.out.print(segundos);
			timer.TiempoEsperaEnDuo(segundos);
			
			timer.setearQuitarVida(2);
			timer.activarBajaVidas();
			System.out.println("DUO");
			teclaPres=teclaPres+letra;					
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(1));
			//JOptionPane.showMessageDialog(null,teclaPres);
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			
			//AGREGAR HILO DE ERROR
			//AGREGAR HILO DE TIEMPO
			
			//JOptionPane.showMessageDialog(null,codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){							
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
					if(numDuo==1){
					Ventana.this.nuevoJuego.ImprimirDuo1_1(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					posMapa++;
					numDuo++;
					}else{
					if(numDuo==2){
						Ventana.this.nuevoJuego.ImprimirDuo1_2(Ventana.this.nuevoJuego.getListMapas().get(1), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
						Ventana.this.repaint();
						posMapa++;
						}
					}
					/*
				Ventana.this.nuevoJuego.ImprimirDuo_t_1(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.update((Graphics2D)Ventana.this.getGraphics());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Ventana.this.nuevoJuego.imprimirDuo_t_2(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}

			Ventana.this.repaint();
				System.out.println(estado);



		}else if(estado==1){
				
		}else if(estado==3){
				setNumeroPantalla(pantallaActual.PERDIO_JUEGO.ordinal());
		}else if(estado ==2){
			int t = getNumeroPantalla();
		    setNumeroPantalla(getNumeroPantalla() + 1);
			System.out.println(getNumeroPantalla());
			estado=-1;
			System.out.println("Estoy afuera");
			Ventana.this.update(Ventana.this.getGraphics());
			Ventana.this.IniciarPantalla();
		}
	}

	public void Ventana_Tutorial(char letra, String direccion2, int puertoDestino) {
		//imprimeEnPantallaLateral(estado);
		// -1 -> no pasa nada. 0 -> duo. 1 -> accionEspecial. 2 -> acabo Nivel. 3 -> has perdido.
		// SE DEBE CAMBIAR A:    -1 -> no pasa nada.
		//					  0 -> duo. 
		//                    1 -> acciontriger.
		// 					  3 -> acabo Nivel.
		// 				      4 -> has perdido.
		////////////////////////////////////////////////////////
		estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(0));

		/////////////	IMPORTANTE                  ////////////
		////////////////////////////////////////////////////////
		enviarMensaje(direccion2 , puertoDestino , letra);
		////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////
		System.out.println(estado);
		////////////////////////////////////////////////////////
		if(estado==-1){
			timer.desactivarBajaVidas();
			Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() ,letra,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(0));
			Ventana.this.repaint();// actualizar
		}else if(estado == 0){ // Duo 
			
			//Tiempo de espera para completar el duo (en segundos)
		    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(0));
			System.out.print(segundos);
			timer.TiempoEsperaEnDuo(segundos);
			
			timer.setearQuitarVida(2);
			timer.activarBajaVidas();
			System.out.println("DUO");
			//imprimeEnPantallaLateral(estado);
			teclaPres=teclaPres+letra;					
			String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(0));
			//JOptionPane.showMessageDialog(null,teclaPres);
			int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
			
			//AGREGAR HILO DE ERROR
			//AGREGAR HILO DE TIEMPO
			
			//JOptionPane.showMessageDialog(null,codigoExtraido);
			if(resultado !=-1){
				if (teclaPres.equals(codigoExtraido)){							
					timer.desactivarBajaVidas();
					estado = -1 ; // Cambio el estado para salir del DUO o Accion.
					teclaPres = "" ;
					Ventana.this.nuevoJuego.ImprimirDuo(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
					Ventana.this.repaint();
					posMapa++;
					/*
				Ventana.this.nuevoJuego.ImprimirDuo_t_1(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.update((Graphics2D)Ventana.this.getGraphics());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Ventana.this.nuevoJuego.imprimirDuo_t_2(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
				Ventana.this.repaint();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				}
			}else { //SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
				teclaPres = "";
				//EQUIVOCACIÓN DE TECLAS PRESIONADAS
				quitarVida(letra);						
			}
			//imprimeEnPantallaLateral(estado);
			Ventana.this.repaint();
				System.out.println(estado);



		}else if(estado==1){
			timer.SetTipo(estado);
			//Tiempo de espera para completar la accion (en segundos)
		    int segundos = Ventana.this.nuevoJuego.buscaTiempoEspera(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB(), Ventana.this.nuevoJuego.getListMapas().get(0));
			System.out.print("Segundos:"+segundos);
			timer.TiempoEsperaEnDuo(segundos);
			
			timer.setearQuitarVida(1);
			timer.activarBajaVidas();
			int subEstado=Ventana.this.nuevoJuego.inmoviliza_cuy(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(0));
			if(subEstado==0){//no se puede mover el cuy 1
				if(letra!='w'&&letra!='W'&&letra!='a'&&letra!='A'&&letra!='s'&&letra!='S'&& letra!='d'&&letra!='D'){
					Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() , letra ,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(0));
					Ventana.this.repaint();// acutlizar
				}
			}
			if(subEstado==1){//no se puede mover el cuy 2
					if(letra!='i'&& letra!='I'&&letra!='j'&&letra!='J'&& letra!='k'&&letra!='K'&& letra!='l'&& letra!='L'){
						Ventana.this.nuevoJuego.realizaAccion(nuevoJuego.getPersonajeA()  ,nuevoJuego.getPersonajeB() , letra ,Ventana.this,Ventana.this.nuevoJuego.getListMapas().get(0));
						
						Ventana.this.repaint();// acutlizar
					}
				
			}
			if(subEstado==-1){//el cuy libre se encuentra en una posicion para liberar al otro cuy
				//SI TIENE EXITO
					
					System.out.println("Accion");
					//imprimeEnPantallaLateral(estado);
					teclaPres=teclaPres+letra ; 					
					String codigoExtraido = Ventana.this.nuevoJuego.buscaCodigo(estado,nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() ,  Ventana.this.nuevoJuego.getListMapas().get(0));
					//JOptionPane.showMessageDialog(null,teclaPres);
					System.out.println(codigoExtraido);
					int resultado = Ventana.this.nuevoJuego.estaCodigo(teclaPres,nuevoJuego.getPersonajeB() ,nuevoJuego.getPersonajeB() , codigoExtraido);
				
					//AGREGAR HILO DE ERROR
					//AGREGAR HILO DE TIEMPO
				
					//JOptionPane.showMessageDialog(null,codigoExtraido);
					if(resultado !=-1){
						if (teclaPres.equals(codigoExtraido)){
							timer.desactivarBajaVidas();
							timer.SetTipo(-1);
							estado = -1 ; // Cambio el estado para salir del DUO o Accion.
							teclaPres = "" ;
							Ventana.this.nuevoJuego.ImprimeAccion(Ventana.this.nuevoJuego.getListMapas().get(0), nuevoJuego.getPersonajeA() , nuevoJuego.getPersonajeB() , Ventana.this);
							Ventana.this.repaint();
						}
						}else {
							//SI se ha equivocado se resetea la teclaPres y se quita 2 puntos de vida
							teclaPres = "";
							//EQUIVOCACIÓN DE TECLAS PRESIONADAS
							quitarVida(letra);		
					
						}
						Ventana.this.nuevoJuego.cambiaCaracterEnMapa(Ventana.this.nuevoJuego.getListMapas().get(0));
						
					}
					Ventana.this.repaint();

		}else if(estado==3){
			//nuevoJuego.getPersonajeA().setPosY(nuevoJuego.getPersonajeA().getPosY() + 1 );
			setNumeroPantalla(pantallaActual.PERDIO_JUEGO.ordinal());
			JOptionPane.showMessageDialog(null,"Adios");
			Ventana.this.IniciarPantalla();
		}				
		estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(nuevoJuego.getPersonajeA() ,nuevoJuego.getPersonajeB() ,Ventana.this.nuevoJuego.getListMapas().get(0));		
		if(estado ==2){
			int t = getNumeroPantalla();
		    setNumeroPantalla(getNumeroPantalla() + 1);
			System.out.println(getNumeroPantalla());
			estado=-1;
			System.out.println("Estoy afuera");
			Ventana.this.update(Ventana.this.getGraphics());
			Ventana.this.IniciarPantalla();
		}
		
		System.out.println(estado);
		/*	
		int direccion = Ventana.this.nuevoJuego.getInterpreteComando().esTeclaValida(e.getKeyChar());	

				System.out.println("Per1 FILA COLUMN A" +per1.getPosX() +" " +  per1.getPosY() + "\nPer2 FILA COLUMNA " + per2.getPosX() + " "+ per2.getPosY());
			if (Ventana.this.nuevoJuego.getInterpreteComando().movimientoValido(per1 , per2 , direccion ,
				Ventana.this.nuevoJuego.getListMapas().get(0))){
				Ventana.this.nuevoJuego.getListMapas().get(0).ImprimirMapa();
				Ventana.this.nuevoJuego.getInterpreteComando().moverPersonajes(per1 , per2, direccion);
				System.out.println("Per1 FILA COLUMN A" +per1.getPosX() +" " +  per1.getPosY() + "\nPer2 FILA COLUMNA " + per2.getPosX() + " "+ per2.getPosY());
			}
		 */	
		//if(Ventana.this.nuevoJuego.getInterpreteComando().movimientoValido(per1, per2, direccion, mapa))

		
		}

	private void enviarMensaje(String direccion, int puerto, char letra) {
		
		Socket socket = null;
		BufferedWriter bWriter= null;
		try {
			socket = new Socket(direccion,puerto);
			bWriter = new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream()));
			bWriter.write(letra);
			System.out.println( "Se envio letra " + letra);
			bWriter.flush();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{ if (bWriter != null){bWriter.close();} }  catch(Exception e1){e1.printStackTrace();}
			try{ if (socket != null){socket.close();} }  catch(Exception e1){e1.printStackTrace();}			
		}								
	}

	public Ventana(){
		try {
			gif = ImageIO.read(new File("A1.gif"));
			gif2 = ImageIO.read(new File("B1.gif"));
			corazon = ImageIO.read(new File("corazon vida.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timer  = new ThreadTimer(this) ;
		timer.start();
		configuracionIniciales();		
		cargarImagenes(this) ;
		eventoTeclado();
		eventoMouse()  ; 		
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		repaint() ;
		
	}
	
	private void Pantalla_0_Mensaje_Salida(){
		String[] opciones = {"Si", "No"};
		 
        int opcion = JOptionPane.showOptionDialog(
                               null                             //componente
                             , "¿Desea salir del juego?"        // Mensaje
                             , "Salida del juego"               // Titulo en la barra del cuadro
                             , JOptionPane.DEFAULT_OPTION       // Tipo de opciones
                             , JOptionPane.INFORMATION_MESSAGE  // Tipo de mensaje (icono)
                             , null                             // Icono (ninguno)
                             , opciones                         // Opciones personalizadas
                             , null                             // Opcion por defecto
                           );
        if(opcion == 0){ //salir
        	System.exit(0);
        }
        else
        	JOptionPane.getRootFrame().dispose(); 
		
	}

	private void IniciarPantalla() {	
		limpiaPantallaLOL();
		cargarImagenes(this) ;		
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();		
		repaint() ;
	
	}	
	private  void limpiaPantallaLOL(){
		limpiar= true;
		repaint();	
		bufferStrategy.dispose();
	}
	
	public void eventoMouse(){
		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if(getNumeroPantalla() == 0){
					if(e.getX() >= 80 && e.getX() <= 248){
						if (e.getY() >= 300 && e.getY() <= 360 ){
							//Configurar para tutorial 1 e iniciar juego							
							//ConfigurarTutorial();
							
						//	Ventana_Config_Cico dd = new Ventana_Config_Cico();
						
							setNumeroPantalla(getNumeroPantalla() + 1);
							IniciarPantalla();
							nuevoJuego = new Juego(10, 50,40);
							nuevoJuego.setearVentana(Ventana.this);
							
												 
							
						}
						if (e.getY() >= 400 && e.getY() <= 460 ){
							
							Serializar2 serie = new Serializar2();
							StoredGame game = serie.DesGuardar();
							nuevoJuego = new Juego(game);
							posMapa = game.posCombinacion;
							PersonajePrincipal.setVida(game.vida);
							Ventana.this.setNumeroPantalla(game.numMapaActual);
							System.out.println(game.numMapaActual);
							Ventana.this.IniciarPantalla();					
						}
						if (e.getY() >= 500 && e.getY() <= 560 ){

							Pantalla_0_Mensaje_Salida();
						}
					}

				}else  if(getNumeroPantalla() == pantallaActual.HISTORIA_1.ordinal()){ //1
					setNumeroPantalla(getNumeroPantalla() + 1) ;
					IniciarPantalla();
					
					
				}else if (getNumeroPantalla() == pantallaActual.TUTORIAL.ordinal()){
									
					
				}else if (getNumeroPantalla() == pantallaActual.HISTORIA_2.ordinal()){
					nuevoJuego.getPersonajeA().setPosX(8);
					nuevoJuego.getPersonajeA().setPosY(0);
					nuevoJuego.getPersonajeB().setPosX(10);
					nuevoJuego.getPersonajeB().setPosY(0);
					setNumeroPantalla(getNumeroPantalla() + 1) ;
					IniciarPantalla();
				}else if (getNumeroPantalla() == pantallaActual.NIVEL_1.ordinal()){
					
				}else if (getNumeroPantalla() == pantallaActual.HISTORIA_3.ordinal()){
					nuevoJuego.getPersonajeA().setPosX(1);
					nuevoJuego.getPersonajeA().setPosY(0);
					nuevoJuego.getPersonajeB().setPosX(9);
					nuevoJuego.getPersonajeB().setPosY(0);
					setNumeroPantalla(getNumeroPantalla() + 1) ;
					IniciarPantalla();
				}else if (getNumeroPantalla() == pantallaActual.NIVEL_2.ordinal()){
					
				}
				
			}
		});
	}

	public void configuracionIniciales(){
		if (getNumeroPantalla() == pantallaActual.MENU.ordinal() ){
			this.setSize(ANCHO_R+ ANCHO_L, ALTO + ALTO_BARRA_MENU+8);
			this.setVisible(true); 
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			repaint();
		}
	}
	public  void cargarImagenes(JFrame ventana){
		try{
				if (getNumeroPantalla() == pantallaActual.MENU.ordinal()){	
					
					imgFondo = ImageIO.read(new File("Pantalla_inicio.jpg"));
					imgLateral = ImageIO.read(new File("barra_informacion.jpg"));
	
				}else if (getNumeroPantalla() == pantallaActual.HISTORIA_1.ordinal()){
					fondoHistoria = ImageIO.read(new File("historia1.png"));	
	
				}else if (getNumeroPantalla() == pantallaActual.TUTORIAL.ordinal()){
					 tutorial.cargarImagen(this);
					 mostro = ImageIO.read(new File("escarabajo_ataca.gif"));				
				}else if (getNumeroPantalla() == pantallaActual.HISTORIA_2.ordinal()){
					historia_2.cargarImagen(this);					
				}else if (getNumeroPantalla() == pantallaActual.NIVEL_1.ordinal()){
					nivel_1.cargarImagen();					
				}else if (getNumeroPantalla() == pantallaActual.HISTORIA_3.ordinal()){
					historia_3.cargarImagen();
				}else if (getNumeroPantalla() == pantallaActual.NIVEL_2.ordinal()){
					nivel_2.cargarImagen();
					mostro = ImageIO.read(new File("rata_ataca.gif"));	
				}else if (getNumeroPantalla() == pantallaActual.PERDIO_JUEGO.ordinal()){
					perdio_juego.cargarImagen();
				}else if (getNumeroPantalla() == pantallaActual.FIN_DEL_JUEGO.ordinal()){
					fin_del_juego.cargarImagen();
				}

		}catch(java.io.IOException e){
			

		}
	}
	
	public void paint(Graphics g){		
		super.paint(g);	
		Toolkit t = Toolkit.getDefaultToolkit ();
		if(limpiar) {
			Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();			
			graph2D.clearRect(0, 0, getWidth(), getHeight() );			
			limpiar = false;
			//bufferStrategy.show();	
		}
		try{
			imprimeEnPantallaLateral(estado, getNumeroPantalla()); //Muestra la información en la barra lateral
			if (getNumeroPantalla() == pantallaActual.MENU.ordinal()){

				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics(); 
				// Extraigo el graphics de mi bufferStrategy pero lo casteo a Graphics3D
				graph2D.drawImage(imgFondo, 0, ALTO_BARRA_MENU , this); // Meto la imagen de fondo 	
				bufferStrategy.show();					 // Lo muestro

			}else if (getNumeroPantalla() == pantallaActual.HISTORIA_1.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();
				graph2D.drawImage(fondoHistoria, 0, ALTO_BARRA_MENU , this); // Meto la imagen de fondo
				bufferStrategy.show();		
			}else if (getNumeroPantalla() == pantallaActual.TUTORIAL.ordinal()){
			
				//nivel_1.bufferStrategy = bufferStrategy;
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();	
				graph2D.drawImage( tutorial.imgFondo ,0, ALTO_BARRA_MENU,this);
				//graph2D.drawImage( imgLateral, ANCHO_R, ALTO_BARRA_MENU , this);
		        graph2D.drawImage ( gif ,nuevoJuego.getPersonajeA().getPosY()*TILE   ,ALTO_BARRA_MENU + nuevoJuego.getPersonajeA() .getPosX()*TILE, this);		   
		        graph2D.drawImage (gif2 ,nuevoJuego.getPersonajeB() .getPosY()*TILE   ,ALTO_BARRA_MENU + nuevoJuego.getPersonajeB().getPosX()*TILE, this);
		        if(estado == 1 && posMapa == 1){
		        	graph2D.drawImage ( mostro ,13*TILE   ,ALTO_BARRA_MENU + 4*TILE, this);
		        }
		        
		    	bufferStrategy.show();	
			}else if (getNumeroPantalla() == pantallaActual.HISTORIA_2.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( historia_2.imgFondo ,0, ALTO_BARRA_MENU,this);
				
				bufferStrategy.show();	
			}else if (getNumeroPantalla() == pantallaActual.NIVEL_1.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( nivel_1.imgFondo ,0, ALTO_BARRA_MENU,this);
			    graph2D.drawImage ( gif ,nuevoJuego.getPersonajeA().getPosY()*TILE   ,ALTO_BARRA_MENU + nuevoJuego.getPersonajeA() .getPosX()*TILE, this);		   
		        graph2D.drawImage (gif2 ,nuevoJuego.getPersonajeB() .getPosY()*TILE   ,ALTO_BARRA_MENU + nuevoJuego.getPersonajeB().getPosX()*TILE, this);		    
				bufferStrategy.show();
				
			}else if (getNumeroPantalla() == pantallaActual.HISTORIA_2.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( historia_2.imgFondo ,0, ALTO_BARRA_MENU,this);					  		    		    
				bufferStrategy.show();
				
			}else if (getNumeroPantalla() == pantallaActual.NIVEL_1.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( nivel_1.imgFondo ,0, ALTO_BARRA_MENU,this);			
				bufferStrategy.show();								
				
			}else if (getNumeroPantalla() == pantallaActual.HISTORIA_3.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( historia_3.imgFondo ,0, ALTO_BARRA_MENU,this);					  		    		    
				bufferStrategy.show();
				
			}else if (getNumeroPantalla() == pantallaActual.NIVEL_2.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( nivel_2.imgFondo ,0, ALTO_BARRA_MENU,this);
				graph2D.drawImage ( gif ,nuevoJuego.getPersonajeA().getPosY()*TILE   ,ALTO_BARRA_MENU + nuevoJuego.getPersonajeA() .getPosX()*TILE, this);		   
		        graph2D.drawImage (gif2 ,nuevoJuego.getPersonajeB() .getPosY()*TILE   ,ALTO_BARRA_MENU + nuevoJuego.getPersonajeB().getPosX()*TILE, this);
		        if(estado == 0){
		        	graph2D.drawImage ( mostro ,11*TILE   ,ALTO_BARRA_MENU + 4*TILE, this);
		        }
		        bufferStrategy.show();
				
			}else if (getNumeroPantalla() == pantallaActual.PERDIO_JUEGO.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( perdio_juego.imgFondo ,0, ALTO_BARRA_MENU,this);					  		    		    
				bufferStrategy.show();
				
			}else if (getNumeroPantalla() == pantallaActual.FIN_DEL_JUEGO.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( fin_del_juego.imgFondo ,0, ALTO_BARRA_MENU,this);					  		    		    
				bufferStrategy.show();
			}
			
			
			
			
			
		}catch(Exception e){
			System.out.println(e);
		}
	}


	public static int getNumeroPatnalla() {
		return getNumeroPantalla();
	}


	public static void setNumeroPatnalla(int numeroPantalla) {
		Ventana.setNumeroPantalla(numeroPantalla);
	}
	
	public void ImprimirMapa( Mapa mapa, PersonajePrincipal cuy1 ,PersonajePrincipal cuy2,JFrame vent){
		
	}
	public void ActualizarMapa(Mapa mapa,PersonajePrincipal cuy1 ,PersonajePrincipal cuy2){
		
	}

	public static int getNumeroPantalla() {
		return numeroPantalla;
	}

	public static void setNumeroPantalla(int numeroPantalla) {
		Ventana.numeroPantalla = numeroPantalla;
	}

}
