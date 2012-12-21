package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;
import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.Proveedor;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoArticulo;

@Name("compraHome")
public class CompraHome extends EntityHome<Compra> {

	@In(create = true)
	ArticuloHome articuloHome;

	@In
	FacesMessages facesMessages;

	public void setCompraIdCompra(Integer id) {
		setId(id);
	}

	public Integer getCompraIdCompra() {
		return (Integer) getId();
	}

	@Override
	protected Compra createInstance() {
		Compra compra = new Compra();
		return compra;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		if (this.getInstance().getArticulo() == null) {
			Articulo articulo = new Articulo();
			articulo.setProveedor(new Proveedor());
			articulo.setTipoArticulo(TipoArticulo.CALZADO_HOMBRE);
			getInstance().setArticulo(articulo);
		}
	}

	public boolean isWired() {
		if (getInstance().getArticulo() == null)
			return false;
		return true;
	}

	public Compra getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	@SuppressWarnings("unchecked")
	public Integer generarNuevoCodigo(Compra compra) {

		//Si existe el número de artículo con el mismo proveedor, talle y color, incrementa el número identificatorio  
		Integer ultimoCodigo = 0;
		Integer nuevoCodigo = 1;
		Boolean hayQueIncrementar = Boolean.FALSE;
		final int INCREMENTO = 1;
		
		List<Compra> comprasEncontradas = this.getEntityManager().createQuery("Select compra from Compra compra where " +
						"compra.articulo.codigoArticulo = :paramArticulo " +
						"and compra.articulo.proveedor.codigoFabricante = :paramProveedor " +
						"and compra.color.codigoColor = :paramColor " +
						"and compra.numero = :paramNumero order by compra.codigoCompra desc")
						.setParameter("paramArticulo", compra.getArticulo().getCodigoArticulo())
						.setParameter("paramProveedor", compra.getArticulo().getProveedor().getCodigoFabricante())
						.setParameter("paramColor", compra.getColor().getCodigoColor())
						.setParameter("paramNumero", compra.getNumero())
						.getResultList();
		if (!comprasEncontradas.isEmpty()) {
			ultimoCodigo = comprasEncontradas.get(0).getCodigoCompra();
			hayQueIncrementar = Boolean.TRUE;
		}
	
		if (hayQueIncrementar) {
			nuevoCodigo = Integer.valueOf(ultimoCodigo) + INCREMENTO;
		}

		return nuevoCodigo;
	}

	public String persist() {

		this.getInstance().setVendida(new Boolean(false));
		String retorno = super.persist();
		facesMessages.clear();

		return retorno;
	}

	public String update() {
		String retorno = super.update();
		facesMessages.clear();

		return retorno;
	}

	@SuppressWarnings("unchecked")
	public List<Compra> obtenerComprasParaArticulo(Articulo articulo) {

		List<Compra> compras = this.getEntityManager().createQuery(
				"Select compra from Compra compra where "
						+ "compra.articulo = :paramArticulo").setParameter(
				"paramArticulo", articulo).getResultList();

		return compras;

	}

	public Double obtenerUltimoPrecioDeVentaParaArticulo(Articulo articulo) {

		Date ultimaFechaDeCompraParaArticulo = null;
		Double ultimoPrecioDeVentaParaArticulo = null;

		try {
			ultimaFechaDeCompraParaArticulo = (Date) this.getEntityManager()
					.createQuery(
							"Select max(compra.fecha) from " + "Compra compra where compra.articulo = :paramArticulo")
							.setParameter("paramArticulo", articulo)
					.getSingleResult();
		} catch (NoResultException nre) {
			// Definir
		}

		if (ultimaFechaDeCompraParaArticulo != null) {

			try {
				ultimoPrecioDeVentaParaArticulo = (Double) this
						.getEntityManager().createQuery(
								"Select compra.precioDeVenta from Compra compra "
										+ "where compra.fecha = :paramFecha and compra.articulo = :paramArticulo")
						.setMaxResults(1).setParameter("paramFecha",
								ultimaFechaDeCompraParaArticulo)
								.setParameter("paramArticulo", articulo)
						.getSingleResult();
			} catch (NoResultException nre) {
				// Definir
			}

		}

		return ultimoPrecioDeVentaParaArticulo;

	}

	public Double obtenerUltimoPrecioDeVentaPorMayorParaArticulo(
			Articulo articulo) {

		Date ultimaFechaDeCompraParaArticulo = null;
		Double ultimoPrecioDeVentaPorMayorParaArticulo = null;

		try {
			ultimaFechaDeCompraParaArticulo = (Date) this.getEntityManager()
					.createQuery(
							"Select max(compra.fecha) from " + "Compra compra")
					.getSingleResult();
		} catch (NoResultException nre) {
			// Definir
		}

		if (ultimaFechaDeCompraParaArticulo != null) {

			try {
				ultimoPrecioDeVentaPorMayorParaArticulo = (Double) this
						.getEntityManager().createQuery(
								"Select compra.precioDeVentaPorMayor from Compra compra "
										+ "where compra.fecha = :paramFecha")
						.setMaxResults(1).setParameter("paramFecha",
								ultimaFechaDeCompraParaArticulo)
						.getSingleResult();
			} catch (NoResultException nre) {
				// Definir
			}

		}

		return ultimoPrecioDeVentaPorMayorParaArticulo;

	}

	public Double obtenerUltimoCostoUnitarioParaArticulo(Articulo articulo) {
		Date ultimaFechaDeCompraParaArticulo = null;
		Double ultimoCostoUnitarioParaArticulo = null;

		try {
			ultimaFechaDeCompraParaArticulo = (Date) this.getEntityManager()
					.createQuery(
							"Select max(compra.fecha) from " + "Compra compra where compra.articulo = :paramArticulo")
							.setParameter("paramArticulo", articulo)
					.getSingleResult();
		} catch (NoResultException nre) {
			// Definir
		}

		if (ultimaFechaDeCompraParaArticulo != null) {

			try {
				ultimoCostoUnitarioParaArticulo = (Double) this
						.getEntityManager().createQuery(
								"Select compra.costoUnitario from Compra compra "
										+ "where compra.fecha = :paramFecha and compra.articulo = :paramArticulo")
						.setMaxResults(1).setParameter("paramFecha",
								ultimaFechaDeCompraParaArticulo).setParameter("paramArticulo", articulo)
						.getSingleResult();
			} catch (NoResultException nre) {
				// Definir
			}

		}

		return ultimoCostoUnitarioParaArticulo;

	}
	
	@SuppressWarnings("unchecked")
	public List <Compra> obtenerComprasEnStockParaArticulo (Articulo articulo) {
		
		List <Compra> comprasEnStock = this.getEntityManager().createQuery("Select compra from Compra compra " +
				"where compra.articulo = :paramArticulo and compra.vendida = :paramVendida")
				.setParameter("paramArticulo", articulo)
				.setParameter("paramVendida", new Boolean(false))
				.getResultList();
		
		return comprasEnStock;
		
	}
}
