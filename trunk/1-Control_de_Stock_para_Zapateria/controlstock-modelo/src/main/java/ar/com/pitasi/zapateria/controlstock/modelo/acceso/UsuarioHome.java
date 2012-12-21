package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.seguridad.Usuario;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {

	public void setUsuarioIdUsuario(Integer id) {
		setId(id);
	}

	public Integer getUsuarioIdUsuario() {
		return (Integer) getId();
	}

	@Override
	protected Usuario createInstance() {
		Usuario usuario = new Usuario();
		return usuario;
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

	public Usuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public Usuario getUsuarioPorNombreDeUsuario(String nombre) {
		Usuario usuarioEncontrado = null;
		try {
			usuarioEncontrado = (Usuario) this.getEntityManager().createQuery("Select usuario from Usuario usuario" +
					" where usuario.nombre = :paramNombreUsuario").setParameter("paramNombreUsuario", nombre)
					.getSingleResult();
		} catch (NoResultException nre) {
			usuarioEncontrado = null;
		}
		return usuarioEncontrado;
	}

//	public List<Asignacionperfilusuario> getAsignacionperfilusuarios() {
//		return getInstance() == null
//				? null
//				: new ArrayList<Asignacionperfilusuario>(getInstance()
//						.getAsignacionperfilusuarios());
//	}

}
