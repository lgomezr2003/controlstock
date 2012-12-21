package ar.com.pitasi.zapateria.controlstock.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="AsignacionTipoVentaTipoPago")
public class AsignacionTipoVentaTipoPago implements Serializable {

	private static final long serialVersionUID = -1617936387545888092L;
	private Integer idAsignacionTipoVentaTipoPago;
	private TipoVenta tipoVenta;
	private TipoPago tipoPago;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuarioDefendoc", unique = true, nullable = false)
	public Integer getIdAsignacionTipoVentaTipoPago() {
		return idAsignacionTipoVentaTipoPago;
	}
	public void setIdAsignacionTipoVentaTipoPago(
			Integer idAsignacionTipoVentaTipoPago) {
		this.idAsignacionTipoVentaTipoPago = idAsignacionTipoVentaTipoPago;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTipoVenta", nullable = true)
	public TipoVenta getTipoVenta() {
		return tipoVenta;
	}
	public void setTipoVenta(TipoVenta tipoVenta) {
		this.tipoVenta = tipoVenta;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTipoPago", nullable = true)
	public TipoPago getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

}
