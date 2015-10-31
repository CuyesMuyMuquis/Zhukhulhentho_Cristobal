package Vista;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Historia_2 {

	public BufferedImage  imgFondo;	

	public void cargarImagen(JFrame vent) throws IOException{
		imgFondo = ImageIO.read(new File("Historia_2.jpg"));		
	}

}



