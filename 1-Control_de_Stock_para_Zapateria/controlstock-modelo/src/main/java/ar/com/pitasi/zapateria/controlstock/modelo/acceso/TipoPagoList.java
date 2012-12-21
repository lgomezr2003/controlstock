package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.TipoPago;

import java.util.Arrays;

@Name("tipoPagoList")
public class TipoPagoList extends EntityQuery<TipoPago> {

	private static final String EJBQL = "select tipoPago from TipoPago tipoPago";

	private static final String[] RESTRICTIONS = {
			"lower(tipoPago.descripcion) like lower(concat(#{tipoPagoList.tipoPago.descripcion},'%'))",
			"lower(tipoPago.sigla) like lower(concat(#{tipoPagoList.tipoPago.sigla},'%'))",};

	private TipoPago tipoPago = new TipoPago();

	public TipoPagoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}
}
