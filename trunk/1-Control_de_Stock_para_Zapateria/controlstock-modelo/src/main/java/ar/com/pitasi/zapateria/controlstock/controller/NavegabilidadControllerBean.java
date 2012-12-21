package ar.com.pitasi.zapateria.controlstock.controller;

import javax.ejb.Init;
import javax.ejb.Remove;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.pitasi.zapateria.controlstock.modelo.acceso.ArticuloHome;

@Name("navegabilidadController")
@Scope(ScopeType.SESSION)
public class NavegabilidadControllerBean implements NavegabilidadController {

	@In(create=true)
	ArticuloHome articuloHome;
	
	private String pasoSiguiente;

	public void setPasoSiguiente(String pasoSiguiente) {
		this.pasoSiguiente = pasoSiguiente;
	}

	public String getPasoSiguiente() {
		return pasoSiguiente;
	}

	@Remove
	@Destroy
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Init
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
