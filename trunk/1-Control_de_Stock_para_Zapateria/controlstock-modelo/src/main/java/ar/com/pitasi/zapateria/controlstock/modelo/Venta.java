package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Venta")
public class Venta implements Serializable {

	private static final long serialVersionUID = 2264770456541009850L;
	
	private Integer idVenta;
	private Double precioDeVenta;
	private Double precioDeVentaModificado;
	private Integer descuento;
	private Date fecha;
	private String codigoDeLaVentaQueCambio;
	
	private Compra compra;
	private TipoPago tipoPago;
	private TipoVenta tipoVenta;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idVenta", unique = true, nullable = false)
	public Integer getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}
	
	@Column(name = "precioDeVenta", nullable = false)
	public Double getPrecioDeVenta() {
		return precioDeVenta;
	}
	public void setPrecioDeVenta(Double precioDeVenta) {
		this.precioDeVenta = precioDeVenta;
	}
	
	@Column(name = "precioDeVentaModificado", nullable = true)
	public Double getPrecioDeVentaModificado() {
		return precioDeVentaModificado;
	}
	public void setPrecioDeVentaModificado(Double precioDeVentaModificado) {
		this.precioDeVentaModificado = precioDeVentaModificado;
	}
	
	@Column(name = "descuento", nullable = true)
	public Integer getDescuento() {
		return descuento;
	}
	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}
	
	@Column(name = "fecha", nullable = false)
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	@Column(name = "codigoDeLaVentaQueCambio", nullable = true)
	public void setCodigoDeLaVentaQueCambio(String codigoDeLaVentaQueCambio) {
		this.codigoDeLaVentaQueCambio = codigoDeLaVentaQueCambio;
	}
	public String getCodigoDeLaVentaQueCambio() {
		return codigoDeLaVentaQueCambio;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idCompra", unique=true, nullable=false)
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idTipoPago", nullable=true)
	public TipoPago getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idTipoVenta", nullable=true)
	public TipoVenta getTipoVenta() {
		return tipoVenta;
	}	
	public void setTipoVenta(TipoVenta tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

}
