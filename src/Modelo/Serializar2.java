package Modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public  class Serializar2 {
  
	public Serializar2(){
		
	}
	public void Guardar(StoredGame sg){
		try {
			XStream xs = new XStream(new DomDriver());
			FileWriter fw = new FileWriter("SavedGame.xml");
			fw.write(xs.toXML(sg));
	        fw.close();
		} catch (IOException e) {
			  System.out.println(e.toString());
	   	}
		
		
	}
	public StoredGame DesGuardar(){
		StoredGame newgame = new StoredGame();
		try{
			XStream xs = new XStream(new DomDriver());
			File archivo = new File ("SavedGame.xml");
			newgame = (StoredGame)xs.fromXML(archivo);	
			return newgame;
		}
		catch(Exception e){
	         e.printStackTrace();	  
	         return newgame;
		}	
	}
}
