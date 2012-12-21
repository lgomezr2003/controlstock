package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TipoVenta")
public class TipoVenta implements Serializable {
	
	private static final long serialVersionUID = 8990822741307544127L;
	private Integer idTipoVenta;
	private String descripcion;
	private Integer porcentajeDescuento;
	private Boolean aceptaCambios;
	private Integer minimoDeArticulosVendidos;
	private Set<AsignacionTipoVentaTipoPago> tiposDePagoAceptados;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idTipoVenta", unique = true, nullable = false)
	public Integer getIdTipoVenta() {
		return idTipoVenta;
	}
	public void setIdTipoVenta(Integer idTipoVenta) {
		this.idTipoVenta = idTipoVenta;
	}
	
	@Column(name = "descripcion", nullable = false)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name = "porcentajeDescuento", nullable=true)
	public Integer getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	public void setPorcentajeDescuento(Integer porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}
	
	@Column(name = "aceptaCambios")
	public Boolean getAceptaCambios() {
		return aceptaCambios;
	}
	public void setAceptaCambios(Boolean aceptaCambios) {
		this.aceptaCambios = aceptaCambios;
	}
	
	@Column(name = "minimoDeArticulosVendidos")
	public Integer getMinimoDeArticulosVendidos() {
		return minimoDeArticulosVendidos;
	}
	public void setMinimoDeArticulosVendidos(Integer minimoDeArticulosVendidos) {
		this.minimoDeArticulosVendidos = minimoDeArticulosVendidos;
	}

	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "idTipoVenta")
	public Set<AsignacionTipoVentaTipoPago> getTiposDePagoAceptados() {
		return tiposDePagoAceptados;
	}
	public void setTiposDePagoAceptados(Set<AsignacionTipoVentaTipoPago> tiposDePagoAceptados) {
		this.tiposDePagoAceptados = tiposDePagoAceptados;
	}
	
}
