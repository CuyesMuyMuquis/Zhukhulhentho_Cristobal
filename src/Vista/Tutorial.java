package Vista;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Tutorial  {
	private static final int ANCHO_R = 1024 ;
	private static final int ANCHO_L = 300 ;
	private static final int ALTO_BARRA_MENU = 20  ;
	private static final int ALTO = 768  ;
	
	
	
	public BufferedImage  imgFondo;	
	public java.awt.Image cuy_1,cuy_2;
	public BufferStrategy bufferStrategy;
	
	public void cargarImagen(JFrame vent) throws IOException{
		imgFondo = ImageIO.read(new File("mapa_tutorial.jpg"));
		Toolkit tol = vent.getToolkit();
		tol = Toolkit.getDefaultToolkit();
		   java.awt.Image cuy_1 = tol.getImage ("cuy_1.gif");		        	    
	       java.awt.Image cuy_2 = tol.getImage ("cuy_2.gif");
      	   if (cuy_1!=null) System.out.print("Hola");
      	   if (cuy_2!=null) System.out.print("Hola");
	}
	
	
}
