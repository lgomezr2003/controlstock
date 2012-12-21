package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.seguridad.PerfilUsuario;

@Name("perfilUsuarioHome")
public class PerfilUsuarioHome extends EntityHome<PerfilUsuario> {

	public void setPerfilUsuarioIdPerfilUsuario(Integer id) {
		setId(id);
	}

	public Integer getPerfilUsuarioIdPerfilUsuario() {
		return (Integer) getId();
	}

	@Override
	protected PerfilUsuario createInstance() {
		PerfilUsuario perfilUsuario = new PerfilUsuario();
		return perfilUsuario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public PerfilUsuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

//	public List<AsignacionPerfilUsuario> getAsignacionperfilUsuarios() {
//		return getInstance() == null
//				? null
//				: new ArrayList<AsignacionPerfilUsuario>(getInstance()
//						.getAsignacionperfilUsuarios());
//	}

}
