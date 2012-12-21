package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;
import ar.com.pitasi.zapateria.controlstock.modelo.Proveedor;

@Name("articuloList")
public class ArticuloList extends EntityQuery<Articulo> {

	private static final long serialVersionUID = 7785365193746101747L;

	private static final String EJBQL = "select articulo from Articulo articulo";

	private static final String[] RESTRICTIONS_PARA_SELECCION_POR_PROVEEDOR = 
	{"lower(articulo.descripcion) like lower(concat('%',#{articuloList.articulo.descripcion},'%'))",
	 "articulo.proveedor = #{proveedorHome.instance}"};
	
	private static final String[] RESTRICTIONS_PARA_LISTA_DE_PRECIOS = 
	{"lower(articulo.descripcion) like lower(concat('%',#{articuloList.articulo.descripcion},'%'))",
	};

	private static final String[] RESTRICTIONS_PARA_REPORTE =
	{"lower(articulo.descripcion) like lower(concat('%',#{articuloList.articulo.descripcion},'%'))",
		"lower(articulo.codigoArticulo) like lower(concat('%',#{articuloList.articulo.codigoArticulo},'%'))",
		"lower(articulo.proveedor.codigoFabricante) like lower(concat('%',#{articuloList.articulo.proveedor.codigoFabricante},'%'))",
	};

	private Articulo articulo = new Articulo();
	private Proveedor proveedor = new Proveedor();

	public ArticuloList() {
		articulo.setProveedor(proveedor);
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS_PARA_SELECCION_POR_PROVEEDOR));
		setMaxResults(25);
	}
	
	public List<Articulo> getResultListParaSeleccion() {
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS_PARA_SELECCION_POR_PROVEEDOR));
		return super.getResultList();
	}
	
	public List<Articulo> getResultListParaListaDePrecios() {
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS_PARA_LISTA_DE_PRECIOS));
		return super.getResultList();
	}
	
	public List<Articulo> getResultListParaReporte() {
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS_PARA_REPORTE));
		return super.getResultList();
	}

	public Articulo getArticulo() {
		return articulo;
	}
}
