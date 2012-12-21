package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.Proveedor;
import ar.com.pitasi.zapateria.controlstock.util.FormateadorDeCodigos;

@Name("proveedorHome")
public class ProveedorHome extends EntityHome<Proveedor> {
	
	private String codigoSeleccionado;
	
	@In(create=true)
	CompraHome compraHome;
	@In(create=true)
	FormateadorDeCodigos formateadorDeCodigos;

	public void setProveedorIdProveedor(Integer id) {
		setId(id);
		
		if (getInstance() != null) {
			this.codigoSeleccionado = this.getInstance().getCodigoFabricante();			
		}
	}

	public Integer getProveedorIdProveedor() {
		return (Integer) getId();
	}

	@Override
	protected Proveedor createInstance() {
		Proveedor proveedor = new Proveedor();
		proveedor.setCodigoFabricante(crearNuevoCodigo());
		return proveedor;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Proveedor getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public String persist () {
		String retorno = super.persist();
		this.setId(null);
		return retorno;
	}

	public void setCodigoSeleccionado(String codigoSeleccionado) {
		this.codigoSeleccionado = codigoSeleccionado;
		Proveedor proveedorEncontrado;
		
		try {
			proveedorEncontrado = (Proveedor) this.getEntityManager().createQuery("Select proveedor from Proveedor proveedor " +
			"where proveedor.codigoFabricante = :paramCodigo").setParameter("paramCodigo", codigoSeleccionado)
			.getSingleResult();		
			
			setInstance(proveedorEncontrado);
		} catch (NoResultException nre) {
			//TODO: log error
		}
	}

	public String getCodigoSeleccionado() {
		return codigoSeleccionado;
	}
	
	private String crearNuevoCodigo() {
		
		final int INCREMENTO = 1;
		String cadenaUltimoCodigo = "0";
		
		try {
			cadenaUltimoCodigo = (String) this.getEntityManager().createQuery("Select max(proveedor.codigoFabricante) from " +
					"Proveedor proveedor").getSingleResult();
			
			if (cadenaUltimoCodigo == null) {
				cadenaUltimoCodigo = "0";				
			}
		}
		
		catch (NoResultException nre) {

			cadenaUltimoCodigo = "0";
			
		}
		
		
		Integer enteroUltimoCodigo = Integer.valueOf(cadenaUltimoCodigo);
		String nuevoCodigo = String.valueOf(enteroUltimoCodigo + INCREMENTO);
		
		nuevoCodigo = formateadorDeCodigos.formatearCodigo(nuevoCodigo,4);
		
		return nuevoCodigo;
		
	}
	
	public void clearInstance() {
		super.clearInstance();
		this.codigoSeleccionado="";
	}

}
