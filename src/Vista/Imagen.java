package Vista;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Imagen extends JFrame {
	
	private BufferedImage img = null; //por si acaso Null :v
	
	public Imagen() {
		super();
		configurarVentana();
		JPanel panel1 = new JPanel();

        leerImagen();
        setSize(1820,980); //Para dimensionar la imagen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        createBufferStrategy(2);
   }
	
	public void configurarVentana(){
		this.setTitle("Esta Es Una Ventana");                   // colocamos titulo a la ventana
        this.setSize(310, 210);                                 // colocamos tamanio a la ventana (ancho, alto)
        this.setLocationRelativeTo(null);                       // centramos la ventana en la pantalla
        this.setLayout(null);                                   // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
        this.setResizable(false);                               // hacemos que la ventana no sea redimiensionable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void leerImagen(){

        try {
            img = ImageIO.read(new File("Pantalla_inicio_cuy.jpg"));  // ImageIO permite leer desde file, url entre otros :)
        } catch (IOException ex) {
            System.out.println("No se pudo leer la imagen");
        }
        
    }
	
	 public void paint(Graphics g){
	        g.drawImage(img, 10, 20,getWidth()-10, getHeight()-10, null); //Esto es tipo C# :)
	}


	    
}
