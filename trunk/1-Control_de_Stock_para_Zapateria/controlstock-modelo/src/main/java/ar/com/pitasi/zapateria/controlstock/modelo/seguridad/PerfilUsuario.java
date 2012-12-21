package ar.com.pitasi.zapateria.controlstock.modelo.seguridad;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modela al Perfil o Rol de un Usuario
 * 
 */
@Entity
@Table(name = "PerfilUsuario")
public class PerfilUsuario {

	private Integer idPerfilUsuario;
	private String nombre;
	private String descripcion;

	/**
	 * Perfil Usuario Tipo Nacion es equivalente a Perfil Usuario Tipo
	 * Responsable de Subsecretaria.
	 */
	private static final int idPerfilUsuarioTipoAdministrador = 1;
	private static final int idPerfilUsuarioTipoResponsable = 2;
	private static final int idPerfilUsuarioTipoVendedor = 3;

	private static Map<Integer, String> nombresVisualesParaPerfil = new HashMap<Integer, String>();
	static {
		nombresVisualesParaPerfil.put(idPerfilUsuarioTipoResponsable,
				"Responsable");
		nombresVisualesParaPerfil.put(idPerfilUsuarioTipoAdministrador,
				"Administrador");
		nombresVisualesParaPerfil.put(idPerfilUsuarioTipoVendedor, "Vendedor");
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idPerfilUsuario", unique = true, nullable = false)
	public Integer getIdPerfilUsuario() {
		return idPerfilUsuario;
	}

	public void setIdPerfilUsuario(Integer idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}

	@Column(name = "descripcion", unique = false, nullable = true, length = 100)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "nombre", unique = false, nullable = false, length = 20)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public static Map<Integer, String> getNombresVisualesParaPerfil() {
		return nombresVisualesParaPerfil;
	}

	public static String getNombreVisualParaPerfil(Integer id) {
		return nombresVisualesParaPerfil.get(id);
	}

}
