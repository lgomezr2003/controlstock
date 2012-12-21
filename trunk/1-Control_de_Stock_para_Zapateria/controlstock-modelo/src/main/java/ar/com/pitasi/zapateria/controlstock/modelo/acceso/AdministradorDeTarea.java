package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import javax.ejb.Local;

@Local
public interface AdministradorDeTarea {
	
	public Integer getCantidadHombre(Integer numero);

	public Integer getCantidadMujer(Integer numero);

}
