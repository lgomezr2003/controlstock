package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.Date;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoPago;
import ar.com.pitasi.zapateria.controlstock.modelo.Venta;

@Name("ventaHome")
public class VentaHome extends EntityHome<Venta> {
	
	@In
	FacesMessages facesMessages;

	public void setVentaIdVenta(Integer id) {
		setId(id);
	}

	public Integer getVentaIdVenta() {
		return (Integer) getId();
	}

	@Override
	protected Venta createInstance() {
		Venta venta = new Venta();
		return venta;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		if (getInstance().getCompra() == null) {
			getInstance().setCompra(new Compra());
		}
		if (getInstance().getTipoPago() == null) {
			getInstance().setTipoPago(new TipoPago());
		}
		if (getInstance().getFecha() == null ) {
			getInstance().setFecha(new Date());
		}
	}

	public boolean isWired() {
		return true;
	}

	public Venta getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public String persist () {
		String retorno = "";
		
		if (! this.getInstance().getCompra().getVendida()) {
			this.getInstance().getCompra().setVendida(true);
			retorno = super.persist();
		}
		
		facesMessages.clear();
		
		return retorno;
	}

}
