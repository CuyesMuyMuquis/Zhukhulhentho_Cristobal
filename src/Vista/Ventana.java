package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.prism.Image;

import java.awt.Toolkit;

import Modelo.Mapa;
import Modelo.Personaje;
import Modelo.PersonajePrincipal;
import Modelo.Posicion;

public class Ventana extends JFrame implements Renderizador{
	private static final long serialVersionUID = 1L;
	private static Toolkit tol = Toolkit.getDefaultToolkit ();
	private static java.awt.Image gif = tol.getImage ("cuy_1.jpg");	
	private static java.awt.Image gif2 = tol.getImage ("cuy_2.png");
	private  Juego nuevoJuego ;
	private static final int TILE = 64;
	private static final int ANCHO_R = 1024 ;
	private static final int ANCHO_L = 300 ;
	private static final int ALTO_BARRA_MENU = 20  ;
	private static final int ALTO = 768  ;
	private boolean limpiar; 
	private static int numeroPantalla = 0;
	private PersonajePrincipal per1 ;
	private PersonajePrincipal per2 ;
	private int estado=-1;
	//private string teclaPres="";
	private enum pantallaActual {
	    MENU, HISTORIA_1, TUTORIAL, HISTORIA_2,
	    NIVEL_1, HISTORIA_3, NIVEL_2,PERDIO_JUEGO,FIN_DEL_JUEGO;
	}
	
	
	//INICIO
	private BufferedImage  imgFondo;	
	private BufferStrategy bufferStrategy;	
	
	//HISTORIA1 
	private BufferedImage fondoHistoria;
	
	// Nivel1
	private  Tutorial  tutorial = new Tutorial() ;
	
	 
	
	public void eventoTeclado(){
	     this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (numeroPantalla == pantallaActual.TUTORIAL.ordinal() ){
					
				// -1 -> no pasa nada. 0 -> duo. 1 -> accionEspecial. 2 -> acabo Nivel. 3 -> has perdido.
				estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(per1,per2,Ventana.this.nuevoJuego.getListMapas().get(0));
				if(estado==-1){
												//Estado = Ventana.this.nuevoJuego.Tutorial(per1 , per2,e.getKeyChar(), Ventana.this);
					Ventana.this.nuevoJuego.realizaAccion(per1,per2,e.getKeyChar(),Ventana.this);
					estado=Ventana.this.nuevoJuego.tutorial_recuperaEstActual(per1,per2,Ventana.this.nuevoJuego.getListMapas().get(0));
				}
				if(estado == 0){
					System.out.println("DUO");
					//imprimeEnPantallaLateral(estado) <------CARLO DE SHIT TU HACES ESTO
					Ventana.this.nuevoJuego.tutorial_verficaCodigo(estado,e.getKeyChar(),per1,per2);
					
					
					
				}
				if(estado==1){
					//verficaCodigo(estado,e.getKeyChar(),per1,per2);
				}
				if(estado==2){
					numeroPantalla++;
				}
				if(estado==3){
					numeroPantalla = pantallaActual.PERDIO_JUEGO.ordinal();
				}
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
					  repaint();
				}				
			}
		});
		
	}
	
	public Ventana(){
		
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
				if(numeroPantalla == 0){
					if(e.getX() >= 80 && e.getX() <= 248){
						if (e.getY() >= 300 && e.getY() <= 360 ){
							//Configurar para tutorial 1 e iniciar juego							
							//ConfigurarTutorial();
							numeroPantalla++;
							IniciarPantalla();
							nuevoJuego = new Juego(10, 50,40);
							nuevoJuego.setearVentana(Ventana.this);
							
							per1 = nuevoJuego.getPersonajeA() ;
							per2 = nuevoJuego.getPersonajeB() ; 
							
						}
						if (e.getY() >= 400 && e.getY() <= 460 ){
							//Configurar para cargar partida
						}
						if (e.getY() >= 500 && e.getY() <= 560 ){

							Pantalla_0_Mensaje_Salida();

						}
					}

				}else  if(numeroPantalla == pantallaActual.HISTORIA_1.ordinal()){ //1
					numeroPantalla++ ;
					IniciarPantalla();
					
					
				}else if (numeroPantalla == pantallaActual.TUTORIAL.ordinal()){
					
					
					
				}

			}

				
		});


	}

	public void configuracionIniciales(){
		if (numeroPantalla == pantallaActual.MENU.ordinal() ){
			this.setSize(ANCHO_R+ ANCHO_L, ALTO + ALTO_BARRA_MENU+8);
			this.setVisible(true); 
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			repaint();
		}
	}
	public  void cargarImagenes(JFrame ventana){
		try{
				if (numeroPantalla == pantallaActual.MENU.ordinal()){	
					//imgFondo = new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
					imgFondo = ImageIO.read(new File("Pantalla_inicio.jpg"));					
	
				}else if (numeroPantalla == pantallaActual.HISTORIA_1.ordinal()){
					fondoHistoria = ImageIO.read(new File("Historia.jpg"));	
	
				}else if (numeroPantalla == pantallaActual.TUTORIAL.ordinal()){
					 tutorial.cargarImagen(this);
					/*
					imgFondo = ImageIO.read(new File("mapa_tutorial.jpg"));
					Cuy_1 = ImageIO.read(new File("cuy_1.gif"));
					cuy_2 = ImageIO.read(new File("cuy_2.gif"));*/
					
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
			bufferStrategy.show();	
		}
		try{
			if (numeroPantalla == pantallaActual.MENU.ordinal()){

				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics(); 
				// Extraigo el graphics de mi bufferStrategy pero lo casteo a Graphics3D
				graph2D.drawImage(imgFondo, 0, ALTO_BARRA_MENU , this); // Meto la imagen de fondo 	

				//graph2D.drawImage(imgDibujar, posX, 0, null);

				bufferStrategy.show();					 // Lo muestro

			}else if (numeroPantalla == pantallaActual.HISTORIA_1.ordinal()){
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();
				graph2D.drawImage(fondoHistoria, 0, ALTO_BARRA_MENU , this); // Meto la imagen de fondo
				bufferStrategy.show();		
			}else if (numeroPantalla == pantallaActual.TUTORIAL.ordinal()){
			
				//nivel_1.bufferStrategy = bufferStrategy;
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage( tutorial.imgFondo ,0, ALTO_BARRA_MENU,this);
				//graph2D.drawImage( tutorial.cuy_1  ,per1.getPosY()*TILE   ,ALTO_BARRA_MENU + per1.getPosX()*TILE,this);
				//graph2D.drawImage( tutorial.cuy_2  ,per2.getPosY()*TILE   ,ALTO_BARRA_MENU + per2.getPosX()*TILE,this);
			    
				
		        graph2D.drawImage ( gif ,per1.getPosY()*TILE   ,ALTO_BARRA_MENU + per1.getPosX()*TILE, this);		   
		        graph2D.drawImage (gif2 ,per2.getPosY()*TILE   ,ALTO_BARRA_MENU + per2.getPosX()*TILE, this);
		        
		        
		        
				/*for(int j = 0 ; j<12; j++)
				for(int i = 0 ; i<16; i++){
					//graph2D.drawImage( tutorial.cuy_2,0,ALTO_BARRA_MENU + i*TILE,this);
					graph2D.drawImage( tutorial.cuy_2, i*TILE,ALTO_BARRA_MENU + j*TILE,this);
				}*/				
				bufferStrategy.show();	
			}

		}catch(Exception e){
			System.out.println(e);
		}

		
	}


	public static int getNumeroPatnalla() {
		return numeroPantalla;
	}


	public static void setNumeroPatnalla(int numeroPantalla) {
		Ventana.numeroPantalla = numeroPantalla;
	}
	
	public void ImprimirMapa( Mapa mapa, PersonajePrincipal cuy1 ,PersonajePrincipal cuy2,JFrame vent){
		
	}
	public void ActualizarMapa(Mapa mapa,PersonajePrincipal cuy1 ,PersonajePrincipal cuy2){
		
	}

}
