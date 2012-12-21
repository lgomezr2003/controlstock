package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TipoPago")
public class TipoPago implements Serializable {

	private static final long serialVersionUID = -5889914465673668449L;

	private Integer idTipoPago;
	private String sigla;
	private String descripcion;
	private Integer recargo;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idTipoPago", unique = true, nullable = false)
	public Integer getIdTipoPago() {
		return idTipoPago;
	}
	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}
	
	@Column(name = "sigla", nullable = false)
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name = "descripcion", nullable = true)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Column(name = "recargo", nullable = true)
	public Integer getRecargo() {
		return recargo;
	}
	public void setRecargo(Integer recargo) {
		this.recargo = recargo;
	}

}
