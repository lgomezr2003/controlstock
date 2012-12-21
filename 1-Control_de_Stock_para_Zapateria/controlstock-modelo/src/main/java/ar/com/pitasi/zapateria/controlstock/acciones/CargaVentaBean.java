package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Init;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoPago;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoVenta;
import ar.com.pitasi.zapateria.controlstock.modelo.Venta;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.RelacionCompraCodigoUnicoHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.TipoPagoHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.TipoVentaHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.VentaHome;

@Stateful
@Name("cargaVenta")
@Scope(ScopeType.CONVERSATION)
public class CargaVentaBean implements CargaVenta {

	@In
	FacesMessages facesMessages;

	@In(create = true)
	VentaHome ventaHome;
	@In(create = true)
	RelacionCompraCodigoUnicoHome relacionCompraCodigoUnicoHome;
	@In(create = true)
	TipoPagoHome tipoPagoHome;
	@In(create = true)
	TipoVentaHome tipoVentaHome;
	@In (create = true)
	SelectorTipoDeVenta selectorTipoDeVenta;

	private TipoVenta tipoVentaSeleccionado;
	
	private Compra compraSeleccionada;

	private Double subtotal = 0.0;
	private String codigoUnico;
	private String idTipoVentaSugeridoSeleccionado;
	private Boolean modificandoPrecioOriginal = false;
	
	private HashMap<Venta, Double> preciosCalculadosPorVenta = new HashMap<Venta, Double>();

	private List<Venta> ventasACargar = new ArrayList<Venta>();

	@Begin(join=true)
	public String comenzarCargaDeVenta() {
		
		if (tipoVentaSeleccionado == null && selectorTipoDeVenta.getIdTipoVentaSeleccionado() != null) {
			tipoVentaHome.setTipoVentaIdTipoVenta(selectorTipoDeVenta.getIdTipoVentaSeleccionado());
			tipoVentaSeleccionado = tipoVentaHome.getInstance();
			tipoVentaHome.clearInstance();
		}
		
		reset();
		
		return String.valueOf(ResultadoNavegacion.CARGA_VENTA);
	}
	
	public String cargarOtraVenta() {
		
		compraSeleccionada= new Compra();
		return String.valueOf(ResultadoNavegacion.CARGA_VENTA);
	}

	public void setCodigoUnico(String codigoUnico) {
		
		Boolean encontrado = false;
		Boolean fin = false;
		
		while (!encontrado && !fin) {
			
			for (Venta venta : ventasACargar) {
				if (relacionCompraCodigoUnicoHome.obtenerCodigoUnicoParaCompra(venta.getCompra()).equals(codigoUnico)) {
					encontrado = true;
					facesMessages.add("Ya se ha cargado la venta correspondiente a este codigo");
				}
			}
			
			fin = true;

		}
		
		if (!encontrado) {
			
			this.codigoUnico = codigoUnico;
			
			compraSeleccionada = relacionCompraCodigoUnicoHome.obtenerCompraPorCodigoUnico(this.codigoUnico);
			
			if (compraSeleccionada != null) {
				ventaHome.getInstance().setCompra(compraSeleccionada);
			}
			
		}
		
	}

	public String getCodigoUnico() {
		return codigoUnico;
	}

	private void asignarPrecioDeVenta( Venta venta ) {

		if (modificandoPrecioOriginal) {
			venta.setPrecioDeVenta ( ventaHome.getInstance().getPrecioDeVentaModificado());
		} else {
			venta.setPrecioDeVenta( compraSeleccionada.getPrecioDeVenta()) ;
		}
		
		preciosCalculadosPorVenta.put(venta, venta.getPrecioDeVenta());

	}

	private Double aplicarRecargo(Double precioDeVenta) {
		
		Double recargo = 0.0;
		
		if (this.tipoPagoHome != null && tipoPagoHome.getInstance().getRecargo() != null) {
			recargo = (precioDeVenta * tipoPagoHome.getInstance().getRecargo()) / 100;
		}
		
		return recargo;
	}
	
	private void aplicarDescuento(Venta venta) {
		
		Double precioDeVenta = venta.getPrecioDeVenta();
		Double descuento = 0.0;		
		
		if ( venta.getTipoVenta() != null) {
			descuento = (precioDeVenta * Double.valueOf(tipoVentaSeleccionado.getPorcentajeDescuento())) / 100;			
		}

		preciosCalculadosPorVenta.put(venta, precioDeVenta - descuento);
		
	}

	public Compra getCompraSeleccionada() {
		return compraSeleccionada;
	}

	public void setCompraSeleccionada(Compra compraSeleccionada) {
		this.compraSeleccionada = compraSeleccionada;
	}

	public String guardarVenta() {
		
		Venta ventaAGuardar = new Venta();
		
		ventaAGuardar.setCompra(ventaHome.getInstance().getCompra());
		ventaAGuardar.setDescuento(ventaHome.getInstance().getDescuento());
		ventaAGuardar.setFecha(ventaHome.getInstance().getFecha());
		ventaAGuardar.setTipoVenta(tipoVentaSeleccionado);

		this.asignarPrecioDeVenta( ventaAGuardar );
		
		if(!modificandoPrecioOriginal) {
			this.aplicarDescuento( ventaAGuardar );
		}
		
		ventasACargar.add(ventaAGuardar);
		
		subtotal += preciosCalculadosPorVenta.get(ventaAGuardar);
		reset();

		return String.valueOf(ResultadoNavegacion.QUE_DESEA);
	}
	
	private void recalcularSubtotal () {
		
		subtotal = 0.0;
		
		for ( Venta venta : ventasACargar ) {
			aplicarDescuento( venta );
			subtotal += preciosCalculadosPorVenta.get(venta);
			
		}
		
	}

	private void reset() {
		ventaHome.setId(null);
		if (tipoVentaSeleccionado != null) {
			ventaHome.getInstance().setDescuento(getTipoVentaSeleccionado().getPorcentajeDescuento());
		}
		this.modificandoPrecioOriginal = false;
		this.codigoUnico = "";
		this.idTipoVentaSugeridoSeleccionado = null;

	}

	@End
	public String persistirTodasLasVentas() {

		this.asignarTipoDePago();
		
		for (Venta venta : ventasACargar) {
			
			if (venta.getDescuento() == null) {
				venta.setDescuento(0);
			}
			
			if (venta.getTipoPago() == null) {
				venta.setTipoPago(tipoPagoHome.getInstance());
			}
			
			venta.setPrecioDeVenta(preciosCalculadosPorVenta.get(venta));
			
			ventaHome.setInstance(venta);
			ventaHome.persist();
		}

		facesMessages.add("Ventas guardadas correctamente");
		this.selectorTipoDeVenta.resetSeleccion();
		this.ventasACargar.clear();
		this.preciosCalculadosPorVenta.clear();

		return String.valueOf(ResultadoNavegacion.LISTA_VENTAS);
	}

	public void asignarTipoDePago() {

		for (Venta venta : ventasACargar) {
			TipoPago tipoPagoSeleccionado = tipoPagoHome.getInstance();
			venta.setTipoPago(tipoPagoSeleccionado);
		}

	}

	public void removerVenta(Venta venta) {
		ventasACargar.remove(venta);
		venta.getCompra().setVendida(false);
		subtotal -= preciosCalculadosPorVenta.get(venta);
		preciosCalculadosPorVenta.remove(venta);

	}
	
	public void setIdTipoVentaSugeridoSeleccionado(String idTipoVentaSugeridoSeleccionado) {
		
		if (idTipoVentaSugeridoSeleccionado != null) {
			
			selectorTipoDeVenta.setIdTipoVentaSeleccionado(Integer.valueOf(idTipoVentaSugeridoSeleccionado) , false); 
			
			tipoVentaHome.setIdSeleccionadoParaTipoVenta(idTipoVentaSugeridoSeleccionado);
			this.tipoVentaSeleccionado = tipoVentaHome.getInstance();
			
			for ( Venta venta : ventasACargar ) {
				
				venta.setTipoVenta(tipoVentaSeleccionado);
				venta.setDescuento(tipoVentaSeleccionado.getPorcentajeDescuento());
				aplicarDescuento(venta);
			}
			
			this.tipoPagoHome.getTiposDePagoParaTipoDeVenta(tipoVentaSeleccionado.getIdTipoVenta());
			this.tipoPagoHome.activarSeleccionPorDefecto();
			this.tipoVentaHome.clearInstance();
			recalcularSubtotal();
		}
	}

	public String getIdTipoVentaSugeridoSeleccionado() {
		return idTipoVentaSugeridoSeleccionado;
	}
	
	public boolean esCompatibleConTipoDeVenta() {
		
		boolean esCompatible = this.ventasACargar.size() >= getTipoVentaSeleccionado().getMinimoDeArticulosVendidos();
		
		return esCompatible;
	}

	public HashMap<Venta, Double> getPreciosCalculadosPorVenta() {
		return preciosCalculadosPorVenta;
	}

	public void setPreciosCalculadosPorVenta(HashMap<Venta, Double> preciosCalculadosPorVenta) {
		this.preciosCalculadosPorVenta = preciosCalculadosPorVenta;
	}
	
	public Double obtenerPrecioCalculadoParaVenta(Venta venta) {
		
		return this.preciosCalculadosPorVenta.get(venta);		
	}

	public void setTipoVentaSeleccionado(TipoVenta tipoVentaSeleccionado) {
		
		this.tipoVentaSeleccionado = tipoVentaSeleccionado;
		this.tipoPagoHome.activarSeleccionPorDefecto();

		for ( Venta venta : ventasACargar ) {
			
			venta.setTipoVenta(tipoVentaSeleccionado);
			venta.setDescuento(tipoVentaSeleccionado.getPorcentajeDescuento());
			aplicarDescuento(venta);
		}
	}

	public TipoVenta getTipoVentaSeleccionado() {
		
		return tipoVentaSeleccionado;
	}
	

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public Double getTotal() {
		
		return subtotal + aplicarRecargo(subtotal);
		
	}

	public List<Venta> getVentasACargar() {
		return ventasACargar;
	}

	public void setVentasACargar(List<Venta> ventasACargar) {
		this.ventasACargar = ventasACargar;
	}

	public void setModificandoPrecioOriginal(Boolean modificandoPrecioOriginal) {
		this.modificandoPrecioOriginal = modificandoPrecioOriginal;
	}

	public Boolean getModificandoPrecioOriginal() {
		return modificandoPrecioOriginal;
	}

	public void modificarPrecioOriginal() {
		modificandoPrecioOriginal = true;
	}

	public void cancelarModificarPrecio() {
		modificandoPrecioOriginal = false;
	}	

	@Init
	public void init() {
		// TODO Auto-generated method stub

	}

	@Remove
	@Destroy
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
