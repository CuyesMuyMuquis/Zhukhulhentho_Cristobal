package Vista;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Nivel_2 {
	public BufferedImage  imgFondo;		
	public void cargarImagen() throws IOException{
		imgFondo = ImageIO.read(new File("nivel2_completo.jpg")); // CAmbiar	
	}	
}
