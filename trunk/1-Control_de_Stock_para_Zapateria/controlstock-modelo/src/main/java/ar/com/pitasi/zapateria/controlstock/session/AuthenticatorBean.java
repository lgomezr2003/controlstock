package ar.com.pitasi.zapateria.controlstock.session;

import javax.ejb.Stateless;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import ar.com.pitasi.zapateria.controlstock.modelo.acceso.UsuarioHome;
import ar.com.pitasi.zapateria.controlstock.modelo.seguridad.Usuario;

@Stateless
@Name("authenticator")
public class AuthenticatorBean implements Authenticator {
	@Logger
	private Log log;

	@In
	Credentials credentials;
	
	@In (create=true)
	UsuarioHome usuarioHome;
	
	@In
	@Out(required=true)
	Identity identity;

	public boolean authenticate() {
		log.info("authenticating {0}", credentials.getUsername());
		
		boolean autenticado = false;
		
		String nombreDeUsuario = credentials.getUsername();
		Usuario usuarioEncontrado = usuarioHome.getUsuarioPorNombreDeUsuario(nombreDeUsuario);
		
		if (usuarioEncontrado != null) {
			autenticado = usuarioEncontrado.getClave().equals(credentials.getPassword());
			
			if (autenticado) {
				identity.addRole(usuarioEncontrado.getPerfilUsuario().getNombre());				
			}
		}
		
		return autenticado;
	}
}
