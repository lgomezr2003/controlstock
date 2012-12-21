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
@Table(name = "Articulo")
public class Articulo implements Serializable {
	
	private static final long serialVersionUID = 501784699917143476L;
	
	private Integer idArticulo;
	private String codigoArticulo;
	private String descripcion;
	
	private Proveedor proveedor;
	private TipoArticulo tipoArticulo;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idArticulo", unique = true, nullable = false)
	public Integer getIdArticulo() {
		return this.idArticulo;
	}
	public void setIdArticulo(Integer idArticulo) {
		this.idArticulo = idArticulo;
	}

	@Column(name = "codigoArticulo", length=5, nullable = false)
	public String getCodigoArticulo() {
		return codigoArticulo;
	}
	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	@Column(name = "descripcion", nullable = true)
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idProveedor", nullable=true)
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	@Column(name = "tipoArticulo", nullable = true)
	public TipoArticulo getTipoArticulo() {
		return tipoArticulo;
	}
	public void setTipoArticulo(TipoArticulo tipoArticulo) {
		this.tipoArticulo = tipoArticulo;
	}

}
