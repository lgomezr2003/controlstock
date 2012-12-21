package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;
import ar.com.pitasi.zapateria.controlstock.modelo.Compra;

@Name("compraList")
public class CompraList extends EntityQuery<Compra> {

	private static final String EJBQL = "select compra from Compra compra";

	private static final String[] RESTRICTIONS = {"compra.fecha = #{compraList.compra.fecha}",
		"compra.vendida = #{compraList.condicionSeleccionada}",
		"compra.articulo.codigoArticulo like concat('%',#{compraList.compra.articulo.codigoArticulo},'%')"};

	private List<SelectItem> itemsCondicion;

	private Boolean condicionSeleccionada;

	private Compra compra = new Compra();
	private List<Compra> resultadoCompras;

	public CompraList() {
		
		compra.setArticulo(new Articulo());
		
		if (condicionSeleccionada == null) {
			condicionSeleccionada = false;
		}
		
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(39);
	}
	
	public List<Compra> getResultList() {
		
		if (resultadoCompras == null) {
			resultadoCompras = super.getResultList();
		}
		
		return resultadoCompras;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setResultadoCompras(List<Compra> resultadoCompras) {
		this.resultadoCompras = resultadoCompras;
	}

	public List<Compra> getResultadoCompras() {
		return resultadoCompras;
	}
	
	private List<SelectItem> crearItemsParaCondicionEnStock() {
		this.itemsCondicion = new ArrayList<SelectItem>();
		itemsCondicion.add(new SelectItem(new Boolean(false), "Articulos en stock"));
		itemsCondicion.add(new SelectItem(new Boolean(true), "Articulos vendidos"));

		return this.itemsCondicion;
	}
	
	public List<SelectItem> getItemsCondicion() {
		
		if (itemsCondicion == null) {
			itemsCondicion = crearItemsParaCondicionEnStock();
		}
		
		return itemsCondicion;
	}

	public void setCondicionSeleccionada(Boolean condicionSeleccionada) {
		this.condicionSeleccionada = condicionSeleccionada;
	}

	public Boolean getCondicionSeleccionada() {
		return condicionSeleccionada;
	}
	
}
