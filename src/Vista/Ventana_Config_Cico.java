package Vista;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javafx.scene.text.Text;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.scenario.effect.AbstractShadow.ShadowMode;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ventana_Config_Cico extends  JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	public static boolean activo ; 
	public Ventana_Config_Cico() {
		this.setVisible(true);
		this.setModal(true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		this.setVisible(true);
		JLabel lblModoDeJuego = new JLabel("Modo de Juego:");
		lblModoDeJuego.setBounds(49, 31, 77, 14);
		contentPanel.add(lblModoDeJuego);
		
		textField = new JTextField();
		textField.setBounds(152, 28, 140, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblJugador = new JLabel("Jugador:");
		lblJugador.setBounds(49, 58, 77, 14);
		contentPanel.add(lblJugador);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(152, 55, 140, 20);
		contentPanel.add(textField_1);
		
		JLabel lblIpDestino = new JLabel("Ip Destino:");
		lblIpDestino.setBounds(49, 90, 77, 14);
		contentPanel.add(lblIpDestino);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(152, 87, 140, 20);
		contentPanel.add(textField_2);
		
		JLabel lblPuertoEntrada = new JLabel("Puerto entrada:");
		lblPuertoEntrada.setBounds(49, 118, 77, 14);
		contentPanel.add(lblPuertoEntrada);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(152, 115, 140, 20);
		contentPanel.add(textField_3);
		
		JLabel lblPuertoSalida = new JLabel("Puerto Salida:");
		lblPuertoSalida.setBounds(49, 149, 77, 14);
		contentPanel.add(lblPuertoSalida);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(152, 146, 140, 20);
		contentPanel.add(textField_4);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						JOptionPane.showMessageDialog(null, "");
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						Ventana_Config_Cico.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				activo = false ; 
			}
		}
	}
}
