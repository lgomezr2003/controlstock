package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Init;
import javax.ejb.Remove;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.Venta;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.RelacionCompraCodigoUnicoHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.VentaHome;

@Name("cargaCambio")
@Scope(ScopeType.CONVERSATION)
public class CargaCambioBean implements CargaCambio {

	private String codigoAValidar;
	private Double precioAnterior;

	private Compra compraSeleccionada;
	private Date fecha = new Date();

	private String nuevoCodigo;
	private List<Venta> nuevasVentas = new ArrayList<Venta>();

	private Double diferencia = new Double(0.0);

	@In(create = true)
	VentaHome ventaHome;

	@In(create = true)
	RelacionCompraCodigoUnicoHome relacionCompraCodigoUnicoHome;
	@In
	FacesMessages facesMessages;

	@Remove
	@Destroy
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Init
	public void init() {
		// TODO Auto-generated method stub

	}

	@Begin(join = true)
	public String comenzarCambio() {
		this.codigoAValidar = null;
		this.compraSeleccionada = null;
		
		return String.valueOf(ResultadoNavegacion.CARGA_CAMBIO);
	}

	public String validarCodigo() {

		Venta ventaEncontrada = relacionCompraCodigoUnicoHome
				.obtenerVentaPorCodigoUnico(codigoAValidar);
			ventaHome.setInstance(ventaEncontrada);
			precioAnterior = ventaHome.getInstance().getPrecioDeVenta();

			diferencia = precioAnterior;
		return String.valueOf(ResultadoNavegacion.VER_VENTA);
	}

	public String agregarVenta() {

		Venta ventaNueva = new Venta();
		ventaNueva.setCompra(compraSeleccionada);
		ventaNueva.setFecha(this.fecha);
		ventaNueva.setCodigoDeLaVentaQueCambio(codigoAValidar);

		// Validar si el precio de venta tambien se puede modificar durante un
		// cambio
		ventaNueva.setPrecioDeVenta(compraSeleccionada.getPrecioDeVenta());
		// Validar si toma el tipo de pago de la venta anulada
		ventaNueva.setTipoPago(ventaHome.getInstance().getTipoPago());

		if (!this.nuevasVentas.contains(ventaNueva)) {
			this.nuevasVentas.add(ventaNueva);
			diferencia -= ventaNueva.getPrecioDeVenta();
		}

		nuevoCodigo = "";

		return String.valueOf(ResultadoNavegacion.CONFIRMAR_CAMBIO);

	}

	@End
	public String aceptarCambio() {

		ventaHome.getInstance().getCompra().setVendida(false);
		ventaHome.update();
		ventaHome.remove();

		for (Venta ventaNueva : this.nuevasVentas) {

			ventaHome.setInstance(ventaNueva);
			ventaHome.persist();

		}

		facesMessages.add("Cambio realizado correctamente");

		return String.valueOf(ResultadoNavegacion.LISTA_VENTAS);

	}

	public void removerArticulo(Venta venta) {
		this.nuevasVentas.remove(venta);
		diferencia += venta.getPrecioDeVenta();
	}

	public String getCodigoAValidar() {
		return codigoAValidar;
	}

	public void setCodigoAValidar(String codigo) {
		this.codigoAValidar = codigo;
	}

	public Double getPrecioAnterior() {
		return precioAnterior;
	}

	public void setNuevoCodigo(String nuevoCodigo) {
		this.nuevoCodigo = nuevoCodigo;
		this.compraSeleccionada = relacionCompraCodigoUnicoHome
				.obtenerCompraPorCodigoUnico(nuevoCodigo);

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Compra getCompraSeleccionada() {
		return compraSeleccionada;
	}

	public void setCompraSeleccionada(Compra compraSeleccionada) {
		this.compraSeleccionada = compraSeleccionada;
	}

	public void setDiferencia(Double diferencia) {
		this.diferencia = diferencia;
	}

	public Double getDiferencia() {
		return diferencia;
	}

	public List<Venta> getNuevasVentas() {
		return nuevasVentas;
	}

	public void setNuevasVentas(List<Venta> nuevasVentas) {
		this.nuevasVentas = nuevasVentas;
	}

	public String getNuevoCodigo() {
		return nuevoCodigo;
	}

}
