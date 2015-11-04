package Vista;
import Modelo.*;
import javax.swing.Timer;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Controlador.GestorMapas;
import Controlador.InterpreteComandos;

public class Juego implements Renderizador{

	private int nextLevel ;
	private Scanner teclado;
	
	private PersonajePrincipal personajeA ; // EL Cuyo  
	private PersonajePrincipal personajeB ;  // La Cuya
	private ArrayList <PersonajeSecundario> listPersonajesSecundarios ; 
	private ArrayList <Objeto> listObjetos ; 
	private ArrayList <Mapa> listMapas ; 
	private GestorMapas gestorMapa ;
	private InterpreteComandos interpreteComando ; 
	private Mapa  mapaActual ; 
	private JFrame ventana; 

	private static int pantallaActual = 0 ;
	
	public void FinDelJuego(){
		System.out.println("Felicitaciones, eres el mejor. Terminaste el juego Mi estimado LOL by --BrayanRP");		
	}
	public void Historia_3(){
		String linea;
		System.out.println("Bienvenido a Historia_3  (presione enter para continuar)");
		linea = teclado.next();
		FinDelJuego();
	}

	public void setearVentana(JFrame ventana){
		this.ventana = ventana ; 
	}
	public Juego(StoredGame nuevoGame){
		 nextLevel =  0  ;
		 //listObjetos  = new  ArrayList <Objeto>(numerosDeObjetos) ; 
		 //listPersonajesSecundarios = new ArrayList <PersonajeSecundario>(numPersSecund) ;
		 setListMapas(new ArrayList <Mapa>(3)) ;
		 setInterpreteComando(new InterpreteComandos()) ;		 		 				 	
		 gestorMapa = new GestorMapas();		
		 
		 //Le pasamos el nivel del mapa --0. tutorial -- 1. nivel 1 -- 2. nivel 2
		 for(int i = 0 ; i <3 ; i++ ){ //Crea los mapas a utilizar
			 getListMapas().add(new Mapa(i));			 
		 }	
		 
		 int indice = 0 ;
		 for (Mapa miMapa : getListMapas()){
			 try {
				gestorMapa.crearMapa(miMapa, indice);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 miMapa.setContador(0);
			 miMapa.setEstadoDuo(indice);
			 indice++ ; 
		 }

		 mapaActual = new Mapa();
		 //gestorMapa.crearMapa(mapaActual, 0);
		 mapaActual =  getListMapas().get(0);  // puede ser no necesario		 
		 //	 mapaActual.ImprimirMapa();		 
		//iniciarPersonajes();
		 // Prueba 
		 /*personajeA = new PersonajePrincipal("Brayan", 22,22,2, 2, 'A', true, false) ; 
		 personajeB = new PersonajePrincipal("Brando", 22,22,3,3, 'A', true, false) ;
		 ImprimirMapa(mapaActual, personajeA, personajeB);*/
		 
		 
	  personajeA = new PersonajePrincipal(nuevoGame.personajeA.getNombre(), nuevoGame.personajeA.getAncho(), nuevoGame.personajeA.getAncho(), nuevoGame.personajeA.getPosX(), nuevoGame.personajeA.getPosY(), nuevoGame.personajeA.getLetraAsociada(), true, false);
		personajeB = new PersonajePrincipal(nuevoGame.personajeB.getNombre(), nuevoGame.personajeB.getAncho(), nuevoGame.personajeB.getAncho(), nuevoGame.personajeB.getPosX(), nuevoGame.personajeB.getPosY(), nuevoGame.personajeB.getLetraAsociada(), true, false);
		//System.out.println("Este es mapa Actual" + nuevoGame.numMapaActual);
		
		
	}
	public Juego(int numeroMapas , int numerosDeObjetos , int numPersSecund){
		 nextLevel =  0  ;
		 listObjetos  = new  ArrayList <Objeto>(numerosDeObjetos) ; 
		 listPersonajesSecundarios = new ArrayList <PersonajeSecundario>(numPersSecund) ;
		 setListMapas(new ArrayList <Mapa>(numeroMapas)) ;
		 setInterpreteComando(new InterpreteComandos()) ;		 		 				 	
		 gestorMapa = new GestorMapas();		
		 
		 //Le pasamos el nivel del mapa --0. tutorial -- 1. nivel 1 -- 2. nivel 2
		 for(int i = 0 ; i <3 ; i++ ){ //Crea los mapas a utilizar
			 getListMapas().add(new Mapa(i));			 
		 }	
		 
		 int indice = 0 ;
		 for (Mapa miMapa : getListMapas()){
			 try {
				gestorMapa.crearMapa(miMapa, indice);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 miMapa.setContador(0);
			 miMapa.setEstadoDuo(indice);
			 indice++ ; 
		 }

		 mapaActual = new Mapa();
		 //gestorMapa.crearMapa(mapaActual, 0);
		 mapaActual =  getListMapas().get(0);  // puede ser no necesario		 
		 //	 mapaActual.ImprimirMapa();		 
		iniciarPersonajes();
		 // Prueba 
		 /*personajeA = new PersonajePrincipal("Brayan", 22,22,2, 2, 'A', true, false) ; 
		 personajeB = new PersonajePrincipal("Brando", 22,22,3,3, 'A', true, false) ;
		 ImprimirMapa(mapaActual, personajeA, personajeB);*/		 
	}
	public void PerdisteElJuego(){
		System.out.println("Game Over");	
	}
		
	public void Nivel_2(PersonajePrincipal perA, PersonajePrincipal perB){
		int entero, direccion;
		char entrada ; 
		System.out.println("Bienvenido al Nivel_2  (escriba un numero para continuar)");
		entero = teclado.nextInt();
		//Posición inicial de los cuyes de nivel 2
		perA.setPosX(10);
		perA.setPosY(0);
		perB.setPosX(5);
		perB.setPosY(0);
		
		if(entero != nextLevel){
			while(true){
				// Fin de nivel
				System.out.println("TU VIDA ES" + perA.getVida());
				if (perA.getPosY() == 15 && perB.getPosY() == 15) break ;
				ImprimirMapa(getListMapas().get(2), perA, perB,ventana);
				if (perA.getVida() <= 0 ) {
				PerdisteElJuego();		
				break ; 
			}
			entrada = teclado.next().charAt(0); 
			direccion = getInterpreteComando().esTeclaValida(entrada);
			System.out.println(direccion);
				if (getInterpreteComando().movimientoValido(perA , perB , direccion , getListMapas().get(2))){
					getInterpreteComando().moverPersonajes(perA, perB, direccion);		
				}
			}
			if (perA.getVida() > 0 )Historia_3();
		}
		else{
			FinDelJuego();
		}
	}
	public void Historia_2(PersonajePrincipal perA, PersonajePrincipal perB){
		String linea;
		System.out.println("Bienvenido a Historia_2  (presione enter para continuar)");
		linea = teclado.next();
		Nivel_2(perA, perB);
	}
	
	public void Nivel_1(PersonajePrincipal perA , PersonajePrincipal perB){
		int entero, direccion;
		char entrada ; 
		System.out.println("Bienvenido al Nivel_1  (escriba un numero para continuar)");
		entero = teclado.nextInt();
		//POsición inicial en Nivel 1
		perA.setPosX(11);
		perA.setPosY(0);
		perB.setPosX(7);
		perB.setPosY(0);
		if(entero != nextLevel){			
			while(true){
				// Fin de nivel
				System.out.println("TU VIDA ES" + perA.getVida());
				if (perA.getPosY() == 15 && perB.getPosY() == 15) break ;
				ImprimirMapa(getListMapas().get(1), perA, perB,ventana);
				if (perA.getVida() <= 0 ) {
				PerdisteElJuego();		
				break ; 
			}
			entrada = teclado.next().charAt(0); 
			direccion = getInterpreteComando().esTeclaValida(entrada);
			System.out.println(direccion);
				if (getInterpreteComando().movimientoValido(perA , perB , direccion , getListMapas().get(1))){
					getInterpreteComando().moverPersonajes(perA, perB, direccion);		
				}
			}
			if (perA.getVida() > 0 )							
			 Historia_2(perA, perB);
		}
		else{
			System.out.println("Game Over");			
		}
	}
	public int tutorial_recuperaEstActual(PersonajePrincipal perA,PersonajePrincipal perB,Mapa mapa){
		int flag=-1;
		if (perA.getPosY() == 15 && perB.getPosY() == 15) {
			flag =  2 ;
			return flag;
		}
		if(perA.getVida()<=0){
			flag=3;
			return flag;
		}		
		flag=interpreteComando.VerificaEstado(mapa, perA, perB);
		return flag;
	}
	public void realizaAccion(PersonajePrincipal perA,PersonajePrincipal perB,char letra, JFrame vent, Mapa mapaActual){
		int direccion,entero;
		char entrada;
		entrada = letra; 				
		direccion = getInterpreteComando().esTeclaValida(entrada);
		System.out.println(direccion);
		if (getInterpreteComando().movimientoValido(perA , perB , direccion , mapaActual)){
			getInterpreteComando().moverPersonajes(perA, perB, direccion);		
		}		
		ImprimirMapa(getListMapas().get(0), perA, perB, vent);
	}
	public int Tutorial(PersonajePrincipal perA , PersonajePrincipal perB, char letra, JFrame vent){
		int entero, direccion;
		char entrada ; 
		System.out.println("Movimientos Jugador1 - Movimientos Jugador2");
		System.out.println("Arriba:    W         - Arriba:     I       ");
		System.out.println("Abajo:     K         - Abajo:      K       ");
		System.out.println("Izquierda: A         - Izquierda:  J       ");
		System.out.println("Derecha:   D         - Derecha:    L       ");
		System.out.println("Especial:  Q         - Especial:   U       ");
		System.out.println("Especial:  E         - Especial:   P       ");
		System.out.println("");
		System.out.println("Al llegar a D, aparecera los comandos que debe presionar\nLuego dar enter para activar la acción especial");
		System.out.println("Cualquier numero y enter para continuar ");
		
		entrada = letra; 				
		direccion = getInterpreteComando().esTeclaValida(entrada);
		System.out.println(direccion);
		if (getInterpreteComando().movimientoValido(perA , perB , direccion , getListMapas().get(0))){
			getInterpreteComando().moverPersonajes(perA, perB, direccion);		
		}		
		
		int flag = -1; // -1 -> no pasa nada. 0 -> duo. 1 -> accionEspecial. 2 -> acabo Nivel. 3 -> has perdido.
		// Fin de nivel
		System.out.println("VIDA " + perA.getVida());
		
		if (perA.getPosY() == 15 && perB.getPosY() == 15) {
			flag =  2 ;
			return flag;
		}
		if (perA.getVida() <= 0 ) {
			PerdisteElJuego();		
			flag = 3 ;
			return flag;
		}
		
		flag = getInterpreteComando().VerificaEstado(getListMapas().get(0), perA, perB);
		if(flag == 0)
			System.out.println("DUO 2");
		
		ImprimirMapa(getListMapas().get(0), perA, perB, vent);
		
		return flag;
			
			///if (perA.getVida() > 0 ) Nivel_1(perA, perB);
	
	}
	public void Historia_1(PersonajePrincipal perA , PersonajePrincipal perB)	{		
		String linea ; 
		System.out.println("Bienvenido a Historia1 ");
		System.out.println("Cristobal y su hermana eran cuyes pequeños.\nSiempre se preguntaban para qué servían, si su existencia era valiosa.\nPara responder a sus incógnitas, fueron en busca de la llama Sabia.\nUn ser lleno de respuestas.");
		System.out.println("( cualquier numero y enter para continuar )");
		linea = teclado.next();
		Tutorial(perA , perB,' ',ventana);			
		
	}
	public void NuevoJuego(PersonajePrincipal perA, PersonajePrincipal perB){
		String linea;
		System.out.println("Escriba su nombre: ");
		linea = teclado.next();	
		System.out.println("Wecome to my world my friend " + linea);
		Historia_1(perA ,perB);
	}
	public void PantallaInicial(){
		
		int opcion;
		int salida = 0;
		
		while(true){
		
			teclado = new Scanner(System.in);
			
			System.out.println("Bienvenidos Todos! amiguitos! a este juego kawai!\n\n");
			System.out.println("1) Nuevo juego");
			System.out.println("2) Salir\n\n");
			System.out.println("Seleccione una de las dos opciones: ");
			
			opcion = teclado.nextInt();
			
			if(opcion == 1){
				NuevoJuego(getPersonajeA() , getPersonajeB());	
			}
			else if(opcion == 2){
				System.out.println("Seguro que quiere salir? ");
				System.out.println("1) Sí");
				System.out.println("2) No");
								
				salida = teclado.nextInt();
				if(salida == 1)   // Si lo cambiamos este (una linea mas arriba regresa al menu principal)
					break;
			}			
		}
		System.out.println("Espero que Regrese Pronto mi estimado .");
	}
	
	
	public void iniciarPersonajes(){
		
		setPersonajeA(new PersonajePrincipal("Cristobal", 10, 190, 4 , 0, 'A', true, false))  ;
		setPersonajeB(new PersonajePrincipal("Hermana", 10, 190,9, 0, 'B', true, false)) ; 
		
	}
	
	public void ActualizarMapa(Mapa mapa,PersonajePrincipal cuy1 ,PersonajePrincipal cuy2){
		
	}
	
	
	public void ImprimirMapa(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal cuy2, JFrame vent){
		int flagDuo=0, flagAccA=0, flagAccB=0, flagNuevoEnemigo=0, esp=0;
		
		for(int fil=0;fil<12;fil++){
			for(int col=0;col<16;col++){
				if(mapa.obtenerCaracter(fil,col)==' ') esp++;
				if ((/*cuy1.getPosX()==i && cuy1.getPosY()==j*/ cuy1.getPos(fil, col)) ||(cuy2.getPos(fil, col))){ 
					if( cuy1.getPos(fil, col)) {						
						System.out.print('A');
						if (mapa.obtenerCaracter(fil,col)=='D') flagDuo++;
						if (mapa.obtenerCaracter(fil,col)=='C') flagAccA++;
						if(((cuy1.getPosX()==11 && cuy1.getPosY()==1)||(cuy1.getPosX()==10 && cuy1.getPosY()==0)) && esp>50){
							flagNuevoEnemigo++;
							mapa.establecerCaracter(fil, col+1, '@');
							//Esto hace que el caracter del costado muestre al enemigo en la impresion							
						}
						//if (mapa.obtenerCaracter(i,j)=='C') flagAccA++;
					}					    
					if(cuy2.getPosX()==fil && cuy2.getPosY()==col) {
						System.out.print('B');
						if (mapa.obtenerCaracter(fil,col)=='D') flagDuo++;	
						if (mapa.obtenerCaracter(fil,col)=='C') flagAccB++;
					}			
				}
				else System.out.print(mapa.obtenerCaracter(fil,col));								
			
			}
			System.out.println();
		}
		
		//la verificacion del duo debe darse en el juego, ya que si la cadena es correcta debera
				//mandar a otro método de impresión
		
		
		/*
		if(flagDuo==2){
			JOptionPane.showMessageDialog(null, "Mensaje", 
					"Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
			
			System.out.println("AMBOS HERMANOS PUEDEN HACER UN DUO");
			if(mapa.getEstadoDuo() == 0){
				ImprimirDuo(mapa, cuy1, cuy2);
				if (cuy1.getVida() <= 0 ) return ;

			}else if (mapa.getEstadoDuo() == 1){

				if (mapa.getContador() == 0){
					ImprimirDuo2(mapa, cuy1, cuy2);
					mapa.setContador( mapa.getContador() + 1 );
				}
				else{ 
					ImprimirDuo3(mapa, cuy1, cuy2);
				}
				if (cuy1.getVida() <= 0 ) return ;

			}else if (mapa.getEstadoDuo() == 2){
				ImprimirDuo4(mapa, cuy1, cuy2);
				if (cuy1.getVida() <= 0 ) return ;
			}	
		}
		if(flagAccA>0){
			System.out.println("CRISTOBAL PUEDE HACER UNA ACCION");
			if(cuy1.getPosX()==9 && cuy1.getPosY()==11) AccionNivel2A(mapa, cuy1, cuy2);
			else AccionTutorial(mapa, cuy1, cuy2);
		}
		if(flagAccB>0){
			System.out.println("LA HERMANA PUEDE HACER UNA ACCION");
			AccionNivel2B(mapa, cuy1, cuy2);
		}
		if(flagNuevoEnemigo>0 && esp> 50){
			//ACA HAY UN NUEVO ENEMIGO ESO VALIDA QUE HAYA PISADO EL TRIGGER
			System.out.println("UN NUEVO ENEMIGO HA APARECIDO");
			cuy1.setPosY(3);
			ImprimirAccionNUEVA(mapa, cuy1, cuy2);
		}
		*/
	}
	
	public void AccionTutorial(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR WDEWW");
		String duo1 = "WDEWW" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR WDEWW");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}
		//ACCION - POSICION 1
		cuy1.setPosY(11); ///
		cuy1.setPosX(7);		
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//ACCION - POSICION 2
		cuy1.setPosY(13);
		cuy1.setPosX(5);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//ACCION - POSICION 3
		cuy1.setPosY(13);
		cuy1.setPosX(8);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		System.out.println("PUEDES MOVERTE Y CONTINUAR");

	}
	
	public void AccionNivel2A(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR WQED");
		String duo1 = "WQED" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR WQED");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}
		//ACCION - POSICION 1
		cuy1.setPosY(4); ///
		cuy1.setPosX(9);		
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//ACCION - POSICION 2
		cuy1.setPosY(5);
		cuy1.setPosX(9);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		System.out.println("PUEDES MOVERTE Y CONTINUAR");

	}
	
	public void AccionNivel2B(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR IUOL");
		String duo1 = "IUOL" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR IUOL");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}
		//ACCION - POSICION 1
		cuy2.setPosY(4); ///
		cuy2.setPosX(3);		
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//ACCION - POSICION 2
		cuy2.setPosY(5);
		cuy2.setPosX(3);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine();
		System.out.println("PUEDES MOVERTE Y CONTINUAR");

	}
	
	public void ImprimirAccionNUEVA(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		Scanner teclado = new Scanner(System.in);
		int flag = validar(cuy1);
		String entrada;
		if(flag==1){
			//ACCION - POSICION 1
			cuy2.setPosY(4); ///
			cuy2.setPosX(8);		
			ImprimirMapa(mapa,cuy1,cuy2,ventana);
			entrada = teclado.nextLine() ;
			//ACCION - POSICION 2
			cuy2.setPosY(3);
			cuy2.setPosX(10);
			ImprimirMapa(mapa,cuy1,cuy2,ventana);
			entrada = teclado.nextLine() ;
			//ACCION - POSICION 3
			cuy2.setPosY(3);
			cuy2.setPosX(8);
			ImprimirMapa(mapa,cuy1,cuy2,ventana);
			System.out.println("HAZ SALVADO A TU HERMANO! :D");
			System.out.println("PUEDES MOVERTE Y CONTINUAR");
			entrada = teclado.nextLine() ;	
		}
	}
	
	public int validar( PersonajePrincipal cuy1){
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR WWKSLS");
		String duo1 = "WWKSLS" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR WWKSLS");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) {
				break ;
			}
			entrada = teclado.nextLine() ; 
		}
		if(duo1.equals(entrada)) return 1;
		else return 0;
	}
	
	public void ImprimirDuo4(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR LDOEQUOE");
		String duo1 = "LDOEQUOE" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR LDOEQUOE");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}
		//DUO - POSICION 1
		cuy1.setPosX(6);
		cuy1.setPosY(10);
		cuy2.setPosX(6);
		cuy2.setPosY(9);		
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 2
		cuy1.setPosX(6);
		cuy1.setPosY(13);
		cuy2.setPosX(5);
		cuy2.setPosY(13);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		System.out.println("PUEDES MOVERTE Y CONTINUAR");
		
	}
	public void ImprimirDuo3(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		///// FAlta 
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR WUOQEI");
		String duo1 = "WUOQEI" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR WUOQEI");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}
		//DUO - POSICION 1
		cuy1.setPosX(9);
		cuy1.setPosY(11);
		cuy2.setPosX(9);
		cuy2.setPosY(10);		
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 2
		cuy1.setPosX(7);
		cuy1.setPosY(11);
		cuy2.setPosX(7);
		cuy2.setPosY(10);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 3
		cuy1.setPosX(5);
		cuy1.setPosY(11);		
		cuy2.setPosX(6);
		cuy2.setPosY(11);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 4
		cuy1.setPosX(2);
		cuy1.setPosY(12);		
		cuy2.setPosX(2);
		cuy2.setPosY(13);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 5
		cuy1.setPosX(0);
		cuy1.setPosY(12);		
		cuy2.setPosX(4);
		cuy2.setPosY(12);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;

		System.out.println("PUEDES MOVERTE Y CONTINUAR");
	}
	//La impresion del duo
	public void ImprimirDuo2(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2){
		Scanner teclado = new Scanner(System.in);
		System.out.println("DEBE PRESIONAR WDEWW");
		String duo1 = "WDEWW" ;
		String entrada = teclado.nextLine() ;
		while ( !duo1.equals(entrada))  {
			System.out.println("DEBE PRESIONAR WDEWW");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}
		//DUO - POSICION 1
		cuy1.setPosX(9);
		cuy1.setPosY(4);
		cuy2.setPosX(9);
		cuy2.setPosY(5);		
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 2
		cuy1.setPosX(9);
		cuy1.setPosY(7);
		cuy2.setPosX(9);
		cuy2.setPosY(8);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		//DUO - POSICION 3
		cuy1.setPosX(11);
		cuy1.setPosY(7);		
		cuy2.setPosX(7);
		cuy2.setPosY(7);
		ImprimirMapa(mapa,cuy1,cuy2,ventana);
		entrada = teclado.nextLine() ;
		
		//DUO - POSICION 3
		//cuy1.setPosX(9);
		//cuy1.setPosY(8);		
		//cuy2.setPosX(5);
		//cuy2.setPosY(8);
		//ImprimirMapa(mapa,cuy1,cuy2);
		//entrada = teclado.nextLine() ;

		System.out.println("PUEDES MOVERTE Y CONTINUAR");
	}
	
	public void imprimirDuo_t_2(Mapa mapa, PersonajePrincipal cuy1, PersonajePrincipal  cuy2, JFrame ven){
		cuy2.setPosX(7);
		cuy2.setPosY(4);
		cuy1.setPosX(7);
		cuy1.setPosY(5);
		ImprimirMapa(mapa,cuy1,cuy2,ven);
		
	}
	public void ImprimirDuo(Mapa mapa, PersonajePrincipal cuy2, PersonajePrincipal  cuy1, JFrame ven){
//		Scanner teclado = new Scanner(System.in);
		//System.out.println("DEBE PRESIONAR WSIKDDLL");
		//String duo1 = "WSIKDDLL" ;
		//String entrada = teclado.nextLine() ;
		
		/*while ( !duo1.equals(entrada))  {			
			System.out.println("DEBE PRESIONAR WSIKDDLL");
			cuy1.setVida(cuy1.getVida() -1) ;
			if (cuy1.getVida() <= 0 ) return ; 
			entrada = teclado.nextLine() ; 
		}*/
		//DUO - POSICION 1
	
		
		
		cuy2.setPosX(6);
		cuy2.setPosY(4);
		cuy1.setPosX(8);
		cuy1.setPosY(4);		
		ImprimirMapa(mapa,cuy1,cuy2,ven);
		ven.update((Graphics2D)ven.getGraphics());
		//ven.repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
	
	cuy2.setPosX(7);
		cuy2.setPosY(4);
		cuy1.setPosX(7);
		cuy1.setPosY(5);
		ImprimirMapa(mapa,cuy1,cuy2,ven);
		
		ven.update((Graphics2D)ven.getGraphics());
		//ven.repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
//		entrada = teclado.nextLine() ;
		
		//DUO - POSICION 3
		cuy2.setPosX(8);
		cuy2.setPosY(7);		
		cuy1.setPosX(8);
		cuy1.setPosY(8);
		ImprimirMapa(mapa,cuy1,cuy2,ven);
		//entrada = teclado.nextLine() ;
		
		ven.update((Graphics2D)ven.getGraphics());
		//ven.repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//DUO - POSICION 4
		cuy1.setPosX(9);
		cuy1.setPosY(8);		
		cuy2.setPosX(5);
		cuy2.setPosY(8);
		ImprimirMapa(mapa,cuy1,cuy2,ven);
	
		ven.update((Graphics2D)ven.getGraphics());
		//ven.repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//entrada = teclado.nextLine() ;
		System.out.println("PUEDES MOVERTE Y CONTINUAR");
		
	}
	public PersonajePrincipal getPersonajeA() {
		return personajeA;
	}
	public void setPersonajeA(PersonajePrincipal personajeA) {
		this.personajeA = personajeA;
	}
	public PersonajePrincipal getPersonajeB() {
		return personajeB;
	}
	public void setPersonajeB(PersonajePrincipal personajeB) {
		this.personajeB = personajeB;
	}
	public InterpreteComandos getInterpreteComando() {
		return interpreteComando;
	}
	public void setInterpreteComando(InterpreteComandos interpreteComando) {
		this.interpreteComando = interpreteComando;
	}
	public ArrayList <Mapa> getListMapas() {
		return listMapas;
	}
	public void setListMapas(ArrayList <Mapa> listMapas) {
		this.listMapas = listMapas;
	}
	
	public int estaCodigo(String subCadena,PersonajePrincipal perA,PersonajePrincipal perB, String Combinacion){
		return Combinacion.indexOf(subCadena);
	}
	public String buscaCodigo(int estado,PersonajePrincipal perA,PersonajePrincipal perB,Mapa mapaActual){
		String cadena = "";
		ArrayList<AccionesEspeciales> lista = mapaActual.getListaAcciones();
		for(int i = 0; i < lista.size(); i++){
			if ( lista.get(i).getTipo() == estado){
				if( perA.getPosX() == lista.get(i).getPosXCuy1() && perA.getPosY() == lista.get(i).getPosYCuy1() && perB.getPosX() == lista.get(i).getPosXCuy2() && perB.getPosY() == lista.get(i).getPosYCuy2())
					cadena=lista.get(i).getCombinacion();

				if(perA.getPosX() == lista.get(i).getPosXCuy1() && perA.getPosY() == lista.get(i).getPosYCuy1() && perB.getPosX() == lista.get(i).getPosXCuy2() && perB.getPosY() == lista.get(i).getPosYCuy2())
					cadena=lista.get(i).getCombinacion();
				else{
					if(lista.get(i).getPosXCuy1() == -1 && lista.get(i).getPosYCuy1() == -1 && perB.getPosX() == lista.get(i).getPosXCuy2() && perB.getPosY() == lista.get(i).getPosYCuy2())
						cadena=lista.get(i).getCombinacion();
					else{
						if(perA.getPosX() == lista.get(i).getPosXCuy1() && perA.getPosY() == lista.get(i).getPosYCuy1() && lista.get(i).getPosXCuy2() == -1 && lista.get(i).getPosYCuy2() == -1)
							cadena=lista.get(i).getCombinacion();
					}


				}
			}		 		
		}
		return cadena;
	}
	public int inmoviliza_cuy(PersonajePrincipal perA,PersonajePrincipal perB,Mapa mapaActual){
			
		return -1; 
	}
}

