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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Ventana extends JFrame {
	private static final long serialVersionUID = 1L;
	private   Juego nuevoJuego ; 
	
	private static final int ANCHO_R = 1024 ;
	private static final int ANCHO_L = 300 ;
	private static final int ALTO_BARRA_MENU = 20  ;
	private static final int ALTO = 768  ;
	private boolean limpiar; 
	private static int numeroPantalla = 0;
	
	private enum pantallaActual {
	    MENU, HISTORIA_1, NIVEL_1, HISTORIA_2,
	    NIVEL_2, HISTORIA_3, NIVEL_3,FIN_DEL_JUEGO;
	}
	
	
	//INICIO
	private BufferedImage  imgFondo;	
	private BufferStrategy bufferStrategy;	
	
	//HISTORIA1 
	private BufferedImage fondoHistoria;
	
	// Nivel1
	private static Nivel_1 nivel_1 = new Nivel_1() ;
	public BufferedImage Cuy_1,cuy_2;
	 
	
	public Ventana(){
		
		configuracionIniciales();		
		cargarImagenes() ;
		eventoMouse()  ; 		
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		repaint() ;
		nuevoJuego = new Juego(10, 50,40);
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
		cargarImagenes() ;		
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
					
					
				}else if (numeroPantalla == pantallaActual.NIVEL_1.ordinal()){
					
					
					
				}

			}

				
		});


	}

	public void configuracionIniciales(){
		if (numeroPantalla == pantallaActual.MENU.ordinal() ){
			this.setSize(ANCHO_R+ ANCHO_L, ALTO + ALTO_BARRA_MENU);
			this.setVisible(true); 
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			repaint();
		}
	}
	public  void cargarImagenes(){
		try{
				if (numeroPantalla == pantallaActual.MENU.ordinal()){	
					//imgFondo = new BufferedImage(100,100, BufferedImage.TYPE_INT_RGB);
					imgFondo = ImageIO.read(new File("Pantalla_inicio.jpg"));					
	
				}else if (numeroPantalla == pantallaActual.HISTORIA_1.ordinal()){
					fondoHistoria = ImageIO.read(new File("Historia.jpg"));	
	
				}else if (numeroPantalla == pantallaActual.NIVEL_1.ordinal()){
					//nivel_1.cargarImagen();
					
					imgFondo = ImageIO.read(new File("mapa_nivel_1.jpg"));
					Cuy_1 = ImageIO.read(new File("cuy_1.gif"));
					cuy_2 = ImageIO.read(new File("cuy_2.gif"));
					
				}

		}catch(java.io.IOException e){
			

		}
	}
	
	public void paint(Graphics g){		
		super.paint(g);		
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
			}else if (numeroPantalla == pantallaActual.NIVEL_1.ordinal()){
			
				//nivel_1.bufferStrategy = bufferStrategy;
				Graphics2D graph2D = (Graphics2D)bufferStrategy.getDrawGraphics();				
				graph2D.drawImage(imgFondo ,0, ALTO_BARRA_MENU,this);
				graph2D.drawImage(Cuy_1,30,40,this);
				graph2D.drawImage(cuy_2,100,100,this);
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

}
