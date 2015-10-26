package Vista;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Nivel_1  {
	private static final int ANCHO_R = 1024 ;
	private static final int ANCHO_L = 300 ;
	private static final int ALTO_BARRA_MENU = 20  ;
	private static final int ALTO = 768  ;
	
	
	
	public BufferedImage  imgFondo;	
	public BufferedImage Cuy_1,cuy_2;
	public BufferStrategy bufferStrategy;
	
	public void cargarImagen() throws IOException{
		imgFondo = ImageIO.read(new File("mapa_nivel_1.jpg"));
		Cuy_1 = ImageIO.read(new File("cuy_1.jpg"));
		cuy_2 = ImageIO.read(new File("cuy_2.jpg"));
	}
	
	
}
