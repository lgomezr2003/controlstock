package ar.com.pitasi.zapateria.controlstock.acciones;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;

public interface ReporteStock {
	
	void init();
	void destroy();

	public Long obtenerCantidadDeUnidadesPorArticulo(Articulo articulo);
	public Long obtenerCantidadDeUnidadesEnStockPorArticulo(Articulo articulo);
	public Long obtenerCantidadDeUnidadesVendidasPorArticulo(Articulo articulo);
	
}
