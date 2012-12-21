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
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Compra")
public class Compra implements Serializable {

	private static final long serialVersionUID = 1442579355590280953L;

	private Integer idCompra;
	private Integer codigoCompra;
	private Double costoUnitario;
	private Date fecha;
	private Integer numero;
	private Boolean codigoDeBarrasGenerado;
	private Boolean vendida;

	private Double precioDeVenta;
//	private Double precioDeVentaPorMayor;
	private Articulo articulo;
	private Color color;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idCompra", unique = true, nullable = false)
	public Integer getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	@Column(name = "codigoCompra", nullable = true, unique = false)
	public Integer getCodigoCompra() {
		return codigoCompra;
	}

	public void setCodigoCompra(Integer codigoCompra) {
		this.codigoCompra = codigoCompra;
	}

	@Column(name = "costoUnitario", nullable = true)
	public Double getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(Double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	@Column(name = "fecha", nullable = true)
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idArticulo", nullable = false)
	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	@Column(name = "numero", nullable = true, length = 2)
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Column(name = "codigoDeBarrasGenerado")
	public Boolean getCodigoDeBarrasGenerado() {
		return codigoDeBarrasGenerado;
	}

	public void setCodigoDeBarrasGenerado(Boolean codigoDeBarrasGenerado) {
		this.codigoDeBarrasGenerado = codigoDeBarrasGenerado;
	}

	@Column(name = "vendida")
	public Boolean getVendida() {
		return vendida;
	}

	public void setVendida(Boolean vendida) {
		this.vendida = vendida;
	}

	@Column(name = "precioDeVenta")
	public Double getPrecioDeVenta() {
		return precioDeVenta;
	}

	public void setPrecioDeVenta(Double precioDeVenta) {
		this.precioDeVenta = precioDeVenta;
	}

//	@Column(name = "precioDePorMayor")
//	public Double getPrecioDeVentaPorMayor() {
//		return precioDeVentaPorMayor;
//	}
//
//	public void setPrecioDeVentaPorMayor(Double precioDeVentaPorMayor) {
//		this.precioDeVentaPorMayor = precioDeVentaPorMayor;
//	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idColor", nullable = true)
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Transient
	@Override
	public String toString() {
		return "Compra [codigoCompra=" + codigoCompra + ", fecha=" + fecha
				+ "]";
	}

}
