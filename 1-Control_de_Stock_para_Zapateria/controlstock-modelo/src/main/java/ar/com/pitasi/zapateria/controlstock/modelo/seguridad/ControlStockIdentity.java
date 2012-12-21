package ar.com.pitasi.zapateria.controlstock.modelo.seguridad;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Identity;

@Name("org.jboss.seam.security.identity")
@Scope(ScopeType.SESSION)
@Install(precedence = Install.APPLICATION)
@BypassInterceptors
@Startup
public class ControlStockIdentity extends Identity {

	private static final long serialVersionUID = 1570211188338025771L;

	private Usuario usuario;

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	//TODO: (09/03/2012) Verificar si es necesario sobreescribir este metodo
	@Override
	public String login() {
		return super.login();
	}
}
