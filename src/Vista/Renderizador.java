package Vista;
import javax.swing.JFrame;

import Modelo.Mapa;
import Modelo.PersonajePrincipal;
public interface Renderizador {
	public void ImprimirMapa( Mapa mapa, PersonajePrincipal cuy1 ,PersonajePrincipal cuy2,JFrame vent);	
	public void ActualizarMapa(Mapa mapa,PersonajePrincipal cuy1 ,PersonajePrincipal cuy2);
}
