package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.TipoVenta;

import java.util.Arrays;

@Name("tipoVentaList")
public class TipoVentaList extends EntityQuery<TipoVenta> {

	private static final long serialVersionUID = 1202144677084573699L;

	private static final String EJBQL = "select tipoVenta from TipoVenta tipoVenta";

	private static final String[] RESTRICTIONS = {
			"lower(tipoVenta.descripcion) like lower(concat(#{tipoVentaList.tipoVenta.descripcion},'%'))",};

	private TipoVenta tipoVenta = new TipoVenta();

	public TipoVentaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public TipoVenta getTipoVenta() {
		return tipoVenta;
	}
}
