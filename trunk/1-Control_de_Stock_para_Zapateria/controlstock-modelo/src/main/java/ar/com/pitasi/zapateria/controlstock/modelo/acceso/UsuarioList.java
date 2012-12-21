package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.seguridad.Usuario;

import java.util.Arrays;

@Name("usuarioList")
public class UsuarioList extends EntityQuery<Usuario> {

	private static final String EJBQL = "select usuario from Usuario usuario";

	private static final String[] RESTRICTIONS = {
			"lower(usuario.apellidoPersona) like lower(concat(#{usuarioList.usuario.apellidoPersona},'%'))",
			"lower(usuario.clave) like lower(concat(#{usuarioList.usuario.clave},'%'))",
			"lower(usuario.nombre) like lower(concat(#{usuarioList.usuario.nombre},'%'))",
			"lower(usuario.nombrePersona) like lower(concat(#{usuarioList.usuario.nombrePersona},'%'))",};

	private Usuario usuario = new Usuario();

	public UsuarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
