package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.Venta;

import java.util.Arrays;
import java.util.List;

@Name("ventaList")
public class VentaList extends EntityQuery<Venta> {

	private static final String EJBQL = "select venta from Venta venta";

	private static final String[] RESTRICTIONS = {"venta.fecha = #{ventaList.venta.fecha}",
		"venta.compra.vendida = #{ventaList.vendida}"};

	private Venta venta = new Venta();
	private Boolean vendida;

	public VentaList() {
		vendida = new Boolean(true);
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVendida(Boolean vendida) {
		this.vendida = vendida;
	}

	public Boolean getVendida() {
		return vendida;
	}
}
