package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.Proveedor;

import java.util.Arrays;

@Name("proveedorList")
public class ProveedorList extends EntityQuery<Proveedor> {

	private static final String EJBQL = "select proveedor from Proveedor proveedor";

	private static final String[] RESTRICTIONS = {
			"lower(proveedor.nombre) like lower(concat('%',#{proveedorList.proveedor.nombre},'%'))",
			"lower(proveedor.codigoFabricante) like lower(concat('%',#{proveedorList.proveedor.codigoFabricante},'%'))",};

	private Proveedor proveedor = new Proveedor();

	public ProveedorList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Proveedor getProveedor() {
		return proveedor;
	}
}
