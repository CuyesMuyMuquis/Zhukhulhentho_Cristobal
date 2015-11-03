package Modelo;

public class AccionesEspeciales {
	private int posXCuy1;
	private int posYCuy1;
	private int posXCuy2;
	private int posYCuy2;
	private int tipo; // 0 -> duo y 1 -> especial cuy1 
	private int tiempoMax;
//	individuales arreglo de combinaciones
	private String combinacion;
	
	public AccionesEspeciales(int tipo, int posXC1, int posYC1, int posXC2, int posYC2, String combinacion,int tiempo){
		setTipo(tipo);
		setPosXCuy1(posXC1);
		setPosYCuy1(posYC1);
		setPosXCuy2(posXC2);
		setPosYCuy2(posYC2);
		setCombinacion(combinacion);
		setTiempoMax(tiempo);
	}

	public int getPosXCuy1() {
		return posXCuy1;
	}

	public void setPosXCuy1(int posXCuy1) {
		this.posXCuy1 = posXCuy1;
	}

	public int getPosYCuy1() {
		return posYCuy1;
	}

	public void setPosYCuy1(int posYCuy1) {
		this.posYCuy1 = posYCuy1;
	}

	public int getPosXCuy2() {
		return posXCuy2;
	}

	public void setPosXCuy2(int posXCuy2) {
		this.posXCuy2 = posXCuy2;
	}

	public int getPosYCuy2() {
		return posYCuy2;
	}

	public void setPosYCuy2(int posYCuy2) {
		this.posYCuy2 = posYCuy2;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getCombinacion() {
		return combinacion;
	}

	public void setCombinacion(String combinacion) {
		this.combinacion = combinacion;
	}

	private int getTiempoMax() {
		return tiempoMax;
	}

	private void setTiempoMax(int tiempoMax) {
		this.tiempoMax = tiempoMax;
	}
	

}
