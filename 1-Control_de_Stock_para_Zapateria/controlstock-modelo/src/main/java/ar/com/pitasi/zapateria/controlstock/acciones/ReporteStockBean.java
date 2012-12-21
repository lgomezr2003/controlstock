package ar.com.pitasi.zapateria.controlstock.acciones;

import javax.ejb.Init;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;

@Stateful
@Name("reporteStock")
@Scope(ScopeType.SESSION)
public class ReporteStockBean implements ReporteStock {

	@PersistenceContext
	private EntityManager entityManager;
	
	public Long obtenerCantidadDeUnidadesPorArticulo(Articulo articulo) {
		
		Long cantidad = (Long) this.entityManager.createQuery("SELECT count(compra) from Compra compra where " +
				"compra.articulo = :paramArticulo").setParameter("paramArticulo", articulo).getSingleResult();
		
		return cantidad;
		
	}
	
	public Long obtenerCantidadDeUnidadesVendidasPorArticulo(Articulo articulo) {
		
		Boolean vendida = true;
		
		Long cantidad = (Long) this.entityManager.createQuery("SELECT count(compra) from Compra compra where " +
				"compra.articulo = :paramArticulo and compra.vendida = :paramVendida")
				.setParameter("paramArticulo", articulo)
				.setParameter("paramVendida", vendida)
				.getSingleResult();
		
		return cantidad;
		
	}
	
	public Long obtenerCantidadDeUnidadesEnStockPorArticulo(Articulo articulo) {
		
		Boolean vendida = false;
		
		Long cantidad = (Long) this.entityManager.createQuery("SELECT count(compra) from Compra compra where " +
				"compra.articulo = :paramArticulo and compra.vendida = :paramVendida")
				.setParameter("paramArticulo", articulo)
				.setParameter("paramVendida", vendida)
				.getSingleResult();
		
		return cantidad;
		
	}
	

	@Init
	public void init() {
	}
	
	@Remove
	@Destroy
	public void destroy() {
	}

}
