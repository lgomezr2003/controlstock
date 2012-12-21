package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Color")
public class Color implements Serializable{

	private static final long serialVersionUID = -4496751769073837199L;
	private Integer idColor;
	private Integer codigoColor;
	private String nombre;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idColor", unique = true, nullable = false)
	public Integer getIdColor() {
		return idColor;
	}
	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}
	
	@Column(name = "codigoColor", nullable = false, unique=true)
	public Integer getCodigoColor() {
		return codigoColor;
	}
	public void setCodigoColor(Integer codigoColor) {
		this.codigoColor = codigoColor;
	}

	@Column(name = "nombre", nullable = false, unique=true)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
