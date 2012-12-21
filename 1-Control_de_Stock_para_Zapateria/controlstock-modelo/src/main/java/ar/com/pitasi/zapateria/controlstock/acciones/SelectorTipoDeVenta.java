package ar.com.pitasi.zapateria.controlstock.acciones;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import ar.com.pitasi.zapateria.controlstock.modelo.acceso.TipoVentaHome;

@Scope(ScopeType.SESSION)
@Name("selectorTipoDeVenta")
public class SelectorTipoDeVenta {
	
	private Integer idTipoVentaSeleccionado;
	
	@In(create=true)
	TipoVentaHome tipoVentaHome;
	
	@In(create=true)
	CargaVenta cargaVenta;
	
	public void setIdTipoVentaSeleccionado(Integer idTipoVentaSeleccionado, Boolean actualizarCargaVenta) {
		
		this.idTipoVentaSeleccionado = idTipoVentaSeleccionado;

		if (actualizarCargaVenta) {
		
			tipoVentaHome.setTipoVentaIdTipoVenta(idTipoVentaSeleccionado);
			cargaVenta.setTipoVentaSeleccionado(tipoVentaHome.getInstance());
			cargaVenta.setIdTipoVentaSugeridoSeleccionado(null);
			tipoVentaHome.clearInstance();
			
		}
		
		
	}
	
	public void setIdTipoVentaSeleccionado(Integer idTipoVentaSeleccionado) {
		
		this.setIdTipoVentaSeleccionado(idTipoVentaSeleccionado,true);
		
	}
	
	public Integer getIdTipoVentaSeleccionado() {
		return idTipoVentaSeleccionado;
	}

	public void resetSeleccion() {
		this.idTipoVentaSeleccionado = null;
	}

}
