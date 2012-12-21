package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RelacionCompraCodigoUnico")
public class RelacionCompraCodigoUnico {
	
	private Integer idRelacionCompraCodigoUnico;
	private Compra compra;
	private String codigoUnico;
	

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idRelacionCompraCodigoUnico", unique = true, nullable = false)
	public Integer getIdRelacionCompraCodigoUnico() {
		return idRelacionCompraCodigoUnico;
	}
	public void setIdRelacionCompraCodigoUnico(
			Integer idRelacionCompraCodigoUnico) {
		this.idRelacionCompraCodigoUnico = idRelacionCompraCodigoUnico;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCompra", nullable = false)
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	@Column(name = "codigoUnico", nullable = false, unique = true)
	public String getCodigoUnico() {
		return codigoUnico;
	}
	public void setCodigoUnico(String codigoUnico) {
		this.codigoUnico = codigoUnico;
	}

}
