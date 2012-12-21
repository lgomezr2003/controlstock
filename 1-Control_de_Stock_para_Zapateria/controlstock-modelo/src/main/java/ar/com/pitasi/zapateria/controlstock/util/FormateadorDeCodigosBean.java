package ar.com.pitasi.zapateria.controlstock.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Scope(ScopeType.SESSION)
@Name("formateadorDeCodigos")
public class FormateadorDeCodigosBean implements FormateadorDeCodigos {

	public String formatearCodigo(String codigo, Integer cantidadDeDigitos) {
		
		final int NUMERO_RELLENO = 0;
		final int NUMERO_DIGITOS = cantidadDeDigitos;

		StringBuilder codigoFormateado = new StringBuilder();
		for (int i = 0; i < NUMERO_DIGITOS - codigo.length(); i++) {
			codigoFormateado.append(NUMERO_RELLENO);
		}
		codigoFormateado.append(codigo);

		return codigoFormateado.toString();
	}

}
