package Modelo;

public class StoredGame {
	public PersonajePrincipal personajeA ;
	public PersonajePrincipal personajeB ;
	public int numMapaActual;
	
	public StoredGame(PersonajePrincipal A, PersonajePrincipal B, int MapaAct){
		personajeA = new PersonajePrincipal(A.getNombre() , A.getAncho() , A.getAlto() , A.getPosX(), A.getPosY(), A.getLetraAsociada(), true , false);
		personajeA.setVida(A.getVida());
		personajeB = new PersonajePrincipal(B.getNombre() , B.getAncho() , B.getAlto() , B.getPosX(), B.getPosY(), B.getLetraAsociada(), true , false);
		personajeB.setVida(A.getVida());
		numMapaActual= MapaAct;
	}
	public StoredGame(StoredGame game){
		personajeA = game.personajeA;
		personajeB = game.personajeB;
		numMapaActual = game.numMapaActual;
	}
	
	public StoredGame(){
	}
	
}
