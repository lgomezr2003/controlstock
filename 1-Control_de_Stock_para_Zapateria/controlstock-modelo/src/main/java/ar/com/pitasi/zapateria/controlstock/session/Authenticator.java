package ar.com.pitasi.zapateria.controlstock.session;

import javax.ejb.Local;

@Local
public interface Authenticator {

	boolean authenticate();

}
