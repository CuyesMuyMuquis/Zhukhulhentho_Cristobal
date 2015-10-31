package Modelo;

import Modelo.*;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileWriter;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.thoughtworks.xstream.XStream;

import Vista.Juego;
import Vista.Ventana;

public class Serializar {
	private Ventana ventana;
	private StoredGame newgame;
	
	public Serializar(Ventana copia){
	// :)
		ventana = copia;
	}
	public Serializar(){

	}
	
	public void Guardar(StoredGame saved){
		
		try {
			XStream xs = new XStream();
			FileWriter fw = new FileWriter("SavedGame.xml");
			fw.write(xs.toXML(saved));
	        fw.close();
		} catch (IOException e) {
			  System.out.println(e.toString());
	   	}
	}
	
	
	public StoredGame deserializar(){
		try{
			XStream xs = new XStream();
			File archivo = new File ("SavedGame.xml");
			newgame = (StoredGame)xs.fromXML(archivo);	
		}
		catch(Exception e){
	         e.printStackTrace();
	         
		}
		finally{
			return newgame;
		}
	}
}
