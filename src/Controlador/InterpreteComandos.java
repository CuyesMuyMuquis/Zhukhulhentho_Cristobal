package Controlador;

import java.util.ArrayList;

import Modelo.AccionesEspeciales;
import Modelo.Mapa;
import Modelo.Personaje;
import Modelo.PersonajePrincipal;

public class InterpreteComandos {
	 public int esTeclaValida(char x){
	    	if (x == 'W' || x == 'w') return 1;
	    	if (x == 'S' || x == 's') return 2;
	    	if (x == 'A' || x == 'a') return 3;
	    	if (x == 'D' || x == 'd') return 4;
	    	if (x == 'Q' || x == 'q') return 5;
	    	if (x == 'E' || x == 'e') return 6;
	    	
	    	if (x == 'I' || x == 'i') return 7;
	    	if (x == 'K' || x == 'k') return 8;
	    	if (x == 'J' || x == 'j') return 9;
	    	if (x == 'L' || x == 'l') return 10;
	    	if (x == 'U' || x == 'u') return 11;
	    	if (x == 'O' || x == 'o') return 12;
	    	
	    	return -1;
	    }
	 
	 public boolean movimientoValido(Personaje a ,Personaje b , int direccion , Mapa mapa){
			int xx , yy ,posibleX , posibleY ;  
			if( direccion == 1 || direccion == 7){ 			 
				xx = -1 ; yy = 0 ; 
			}else if(direccion == 2 || direccion == 8){
				xx = +1 ; yy = 0;			
			}else if(direccion == 3 || direccion == 9){
				xx = 0 ; yy = -1 ; 
			}else if(direccion == 4 || direccion == 10){
				xx = 0 ; yy = +1 ;			
			}else {
				xx = 0 ; yy = 0;
			}
			if(direccion <=6 ){
				posibleX = a.getPosX()  +  xx ;
				posibleY = a.getPosY()  +  yy ;			
			}else{
				posibleX = b.getPosX()  +  xx ;
				posibleY = b.getPosY()  +  yy ;			
			}
			
			if(!(  posibleX >= 0 && posibleX <=11 )) return false ; 
			if(!(  posibleY >= 0 && posibleY <=15 ))return false ;
			char c = mapa.obtenerCaracter(posibleX, posibleY) ; 
			if (c == 'N' || c=='S' || c =='T' || c == 'E' || c == 'D' || c == 'C' ||  c=='@') return true ; 
			if (c == 'p' || c == 'v') return false ; 		
			return false ; 		
		}
	 
	 public void moverPersonajes(Personaje a ,Personaje b , int direccion ){
			int xx , yy ;
			if( direccion == 1 || direccion == 7){ 			 
				xx = -1 ; yy = 0 ; 
			}else if(direccion == 2 || direccion == 8){
				xx = +1 ; yy = 0;			
			}else if(direccion == 3 || direccion == 9){
				xx = 0 ; yy = -1 ; 
			}else if(direccion == 4 || direccion == 10){
				xx = 0 ; yy = +1 ;			
			}else {
				xx = 0 ; yy = 0;
			}
			if(direccion <=6 ){
				int hh =  a.getPosX() + xx ;
				int jj =  a.getPosY() + yy ;
				a.setPosX(hh );
				a.setPosY(jj);
			}else {
				b.setPosX( b.getPosX() + xx );
				b.setPosY( b.getPosY() + yy );			
			}
		}
	 
	 public int VerificaEstado(Mapa mapaActual, PersonajePrincipal cuy1, PersonajePrincipal cuy2){
		 int estado = -1;
		 ArrayList<AccionesEspeciales> lista = mapaActual.getListaAcciones();
		 for(int i = 0; i < lista.size(); i++){
			 if ( lista.get(i).getTipo() == 0){
				 if( cuy1.getPosX() == lista.get(i).getPosXCuy1() && cuy1.getPosY() == lista.get(i).getPosYCuy1() && cuy2.getPosX() == lista.get(i).getPosXCuy2() && cuy2.getPosY() == lista.get(i).getPosYCuy2())
					 estado = 0;
			 }
			 else{
				 if(cuy1.getPosX() == lista.get(i).getPosXCuy1() && cuy1.getPosY() == lista.get(i).getPosYCuy1() && cuy2.getPosX() == lista.get(i).getPosXCuy2() && cuy2.getPosY() == lista.get(i).getPosYCuy2())
					 estado = 1;
				 else{
					 if(lista.get(i).getPosXCuy1() == -1 && lista.get(i).getPosYCuy1() == -1 && cuy2.getPosX() == lista.get(i).getPosXCuy2() && cuy2.getPosY() == lista.get(i).getPosYCuy2())
						 estado = 1;
					 else{
						 if(cuy1.getPosX() == lista.get(i).getPosXCuy1() && cuy1.getPosY() == lista.get(i).getPosYCuy1() && lista.get(i).getPosXCuy2() == -1 && lista.get(i).getPosYCuy2() == -1)
							 estado = 1;
					 }
				 }
			 }
		 }		 
		 return estado;
	 }
}
