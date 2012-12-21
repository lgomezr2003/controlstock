package ar.com.pitasi.zapateria.controlstock.controller;

public interface NavegabilidadController {
	
	void init();
	void destroy();
	
	public void setPasoSiguiente(String pasoSiguiente);
	public String getPasoSiguiente();

}
