package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Precio")
public class Precio implements Serializable {

	private static final long serialVersionUID = 3493766376372560749L;

	private Integer idPrecio;
	private Integer desdeNumero;
	private Integer hastaNumero;
	private Double valor;
	
	private Articulo articuloCorrespondiente;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idPrecio", unique = true, nullable = false)
	public Integer getIdPrecio() {
		return idPrecio;
	}
	public void setIdPrecio(Integer idPrecio) {
		this.idPrecio = idPrecio;
	}

	@Column(name = "desdeNumero", nullable = false)
	public Integer getDesdeNumero() {
		return desdeNumero;
	}
	public void setDesdeNumero(Integer desdeNumero) {
		this.desdeNumero = desdeNumero;
	}
	
	@Column(name = "hastaNumero", nullable = false)
	public Integer getHastaNumero() {
		return hastaNumero;
	}
	public void setHastaNumero(Integer hastaNumero) {
		this.hastaNumero = hastaNumero;
	}
	
	@Column(name = "valor", nullable = false)
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="idArticuloCorrespondiente")
	public Articulo getArticuloCorrespondiente() {
		return articuloCorrespondiente;
	}
	public void setArticuloCorrespondiente(Articulo articuloCorrespondiente) {
		this.articuloCorrespondiente = articuloCorrespondiente;
	}

}
