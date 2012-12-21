package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.HashMap;
import java.util.List;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoVenta;
import ar.com.pitasi.zapateria.controlstock.modelo.Venta;

public interface CargaVenta {
	
	void init();
	void destroy();
	
	public String comenzarCargaDeVenta();
	
	public String getCodigoUnico();
	public void setCodigoUnico(String codigoUnico);
	
	public Compra getCompraSeleccionada();
	public void setCompraSeleccionada(Compra compraSeleccionada) ;
	
	public String guardarVenta();
	public String persistirTodasLasVentas();
	
	public void removerVenta(Venta venta);
	public void setSubtotal(Double subtotal);
	public Double getSubtotal();
	public Double getTotal();
	
	public List<Venta> getVentasACargar();
	public void setVentasACargar(List<Venta> ventasACargar);
	
	public void setModificandoPrecioOriginal(Boolean modificandoPrecioOriginal);
	public Boolean getModificandoPrecioOriginal();
	
	public void modificarPrecioOriginal();
	public void cancelarModificarPrecio();
	
	public void asignarTipoDePago();
	public String cargarOtraVenta();
	
	public void setIdTipoVentaSugeridoSeleccionado(String idTipoVentaSugeridoSeleccionado);
	public String getIdTipoVentaSugeridoSeleccionado();
	
	public boolean esCompatibleConTipoDeVenta();
	
	public HashMap<Venta, Double> getPreciosCalculadosPorVenta();
	public void setPreciosCalculadosPorVenta(HashMap<Venta, Double> preciosCalculadosPorVenta);
	
	public void setTipoVentaSeleccionado(TipoVenta tipoVentaSeleccionado);
	public TipoVenta getTipoVentaSeleccionado();
	
	public Double obtenerPrecioCalculadoParaVenta(Venta venta);
}
