package ar.com.pitasi.zapateria.controlstock.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Proveedor")
public class Proveedor implements java.io.Serializable{
	
	private static final long serialVersionUID = -7853011541515182231L;

	private Integer idProveedor;
	private String codigoFabricante;
	private String nombre;
	private String direccion;
	private String telefono;

	public Proveedor() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idProveedor", unique = true, nullable = false)
	public Integer getIdProveedor() {
		return this.idProveedor;
	}
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	@Column(name = "codigoFabricante", length=4, nullable = false, unique = true)
	public String getCodigoFabricante() {
		return codigoFabricante;
	}
	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
	}

	@Column(name = "nombre", nullable = true)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "direccion", nullable = true)
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", nullable = true)
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
		
}
