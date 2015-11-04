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
	
	
	
	
	public BufferedImage  imgFondo;	
	
	public BufferStrategy bufferStrategy;
	
	public void cargarImagen(JFrame vent) throws IOException{
		//imgFondo = ImageIO.read(new File("mapa_tutorial.jpg"));
		imgFondo = ImageIO.read(new File("tutorial1-completo.jpg"));
		
		//Toolkit tol = vent.getToolkit();
		//tol = Toolkit.getDefaultToolkit();
		  // java.awt.Image cuy_1 = tol.getImage ("cuy_1.gif");		        	    
	       //java.awt.Image cuy_2 = tol.getImage ("cuy_2.gif");
		
	
		
	}
	
	
}
