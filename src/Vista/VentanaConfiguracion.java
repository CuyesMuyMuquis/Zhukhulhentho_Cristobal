package Vista;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javafx.scene.text.Text;

public class VentanaConfiguracion extends JFrame{

	public VentanaConfiguracion(){
		this.setSize(400,500);
		this.setVisible(true); 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JTextField texto = new JTextField() ;				
	}
}
