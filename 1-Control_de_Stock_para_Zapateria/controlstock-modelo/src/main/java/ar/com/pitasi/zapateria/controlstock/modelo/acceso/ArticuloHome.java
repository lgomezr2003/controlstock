package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;
import ar.com.pitasi.zapateria.controlstock.modelo.Proveedor;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoArticulo;

@Name("articuloHome")
public class ArticuloHome extends EntityHome<Articulo> {

	private static final long serialVersionUID = 4871608525777239055L;

	@In(create = true)
	ProveedorHome proveedorHome;
	
	@In
	FacesMessages facesMessages;

	private String codigoSeleccionado;
	
	public String getCodigoSeleccionado() {
		return codigoSeleccionado;
	}

	public void setCodigoSeleccionado(String codigoSeleccionado) {
		this.codigoSeleccionado = codigoSeleccionado;
		Articulo articuloEncontrado = null;;
		
		if (codigoSeleccionado != null) {
			try {
				articuloEncontrado = (Articulo) this.getEntityManager().createQuery("Select articulo from Articulo articulo " +
				"where articulo.codigoArticulo = :paramCodigo").setParameter("paramCodigo", codigoSeleccionado)
				.getSingleResult();
				
				this.setInstance(articuloEncontrado);
			} catch (NoResultException nre) {
				//TODO: Log error
			}
		}
	}
	
	public void setArticuloIdArticulo(Integer id) {
		setId(id);
		
		if (getInstance() != null) {
			this.codigoSeleccionado = this.getInstance().getCodigoArticulo();			
		}
	}

	public Integer getArticuloIdArticulo() {
		return (Integer) getId();
	}

	@Override
	protected Articulo createInstance() {
		Articulo articulo = new Articulo();
		articulo.setProveedor(new Proveedor());
		articulo.setTipoArticulo(TipoArticulo.CALZADO_HOMBRE);
		return articulo;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
	}

	public boolean isWired() {
		return true;
	}

	public Articulo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public List<SelectItem> getItemsParaTipoDeCalzado() {
		List<SelectItem> itemsParaTipoDeCalzado = new ArrayList<SelectItem>();
		itemsParaTipoDeCalzado.add(new SelectItem(TipoArticulo.CALZADO_HOMBRE, "Hombre"));
		itemsParaTipoDeCalzado.add(new SelectItem(TipoArticulo.CALZADO_MUJER, "Mujer"));
		itemsParaTipoDeCalzado.add(new SelectItem(TipoArticulo.CALZADO_NINIO, "Ninio"));

		return itemsParaTipoDeCalzado;
	}
	
	public Boolean existeArticulo (String codigoArticulo, Proveedor proveedor, TipoArticulo tipoArticulo) {
		
		Boolean existe = false;
		try {
			Articulo articuloEncontrado = (Articulo) this.getEntityManager().createQuery("Select articulo from Articulo articulo " +
					"where articulo.codigoArticulo = :paramCodigo and articulo.proveedor = :paramProveedor" +
					" and articulo.tipoArticulo = :paramTipoArticulo")
					.setParameter("paramCodigo", codigoArticulo).setParameter("paramProveedor", proveedor)
					.setParameter("paramTipoArticulo", tipoArticulo)
					.getSingleResult();
			existe = true;
		}
		catch (NoResultException nre) {
			existe = false;
		}
		
		return existe;
	}
	
	public String update() {
		
		String retorno = super.update();
		
		facesMessages.clear();
		facesMessages.add("Cambios guardados correctamente");
		
		return retorno;
		
	}

}
