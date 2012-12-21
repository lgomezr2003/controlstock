package ar.com.pitasi.zapateria.controlstock.modelo.seguridad;

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
@Table(name="Usuario")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = -3504472426467955232L;
	
	private Integer idUsuario;
	private String nombre;
	private String clave;
	private String nombrePersona;
	private String apellidoPersona;
	private PerfilUsuario perfilUsuario;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idUsuario", unique = true, nullable = false)
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "clave", nullable = false)
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	@Column(name = "nombrePersona", nullable = false)
	public String getNombrePersona() {
		return nombrePersona;
	}
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	
	@Column(name = "apellidoPersona", nullable = false)
	public String getApellidoPersona() {
		return apellidoPersona;
	}
	public void setApellidoPersona(String apellidoPersona) {
		this.apellidoPersona = apellidoPersona;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idPerfilUsuario", nullable=true)
	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}
	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}
	
}
