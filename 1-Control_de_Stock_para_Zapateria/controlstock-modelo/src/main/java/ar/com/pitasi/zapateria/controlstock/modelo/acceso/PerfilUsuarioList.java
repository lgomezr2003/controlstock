package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.seguridad.PerfilUsuario;

import java.util.Arrays;

@Name("perfilusuarioList")
public class PerfilUsuarioList extends EntityQuery<PerfilUsuario> {

	private static final String EJBQL = "select perfilusuario from Perfilusuario perfilusuario";

	private static final String[] RESTRICTIONS = {
			"lower(perfilusuario.descripcion) like lower(concat(#{perfilusuarioList.perfilusuario.descripcion},'%'))",
			"lower(perfilusuario.nombre) like lower(concat(#{perfilusuarioList.perfilusuario.nombre},'%'))",};

	private PerfilUsuario perfilusuario = new PerfilUsuario();

	public PerfilUsuarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public PerfilUsuario getPerfilusuario() {
		return perfilusuario;
	}
}
