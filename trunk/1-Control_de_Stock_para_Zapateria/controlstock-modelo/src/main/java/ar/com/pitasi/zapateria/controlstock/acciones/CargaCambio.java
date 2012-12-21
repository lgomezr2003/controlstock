package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.Date;
import java.util.List;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.Venta;

public interface CargaCambio {
	
	void init();
	void destroy();
	
	public void setCodigoAValidar(String codigo);
	public String getCodigoAValidar();
	
	public String comenzarCambio();
	public String validarCodigo();
	
	public String getNuevoCodigo();
	public void setNuevoCodigo(String nuevoCodigo);
	public Date getFecha();
	public void setFecha(Date fecha);
	public Compra getCompraSeleccionada();
	public Double getDiferencia();
	public List<Venta> getNuevasVentas();
	public void setNuevasVentas(List<Venta> nuevasVentas);
	
}
