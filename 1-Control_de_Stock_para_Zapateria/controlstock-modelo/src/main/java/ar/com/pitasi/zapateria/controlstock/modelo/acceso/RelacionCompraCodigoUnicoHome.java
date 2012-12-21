package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import javax.persistence.NoResultException;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.RelacionCompraCodigoUnico;
import ar.com.pitasi.zapateria.controlstock.modelo.Venta;

@Name("relacionCompraCodigoUnicoHome")
public class RelacionCompraCodigoUnicoHome extends
		EntityHome<RelacionCompraCodigoUnico> {
	
	@In(create = true)
	CompraHome compraHome;
	@In
	FacesMessages facesMessages;

	public void setRelacionCompraCodigoUnicoIdRelacionCompraCodigoUnico(
			Integer id) {
		setId(id);
	}

	public Integer getRelacionCompraCodigoUnicoIdRelacionCompraCodigoUnico() {
		return (Integer) getId();
	}

	@Override
	protected RelacionCompraCodigoUnico createInstance() {
		RelacionCompraCodigoUnico relacionCompraCodigoUnico = new RelacionCompraCodigoUnico();
		return relacionCompraCodigoUnico;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}
	
	public String obtenerCodigoUnicoParaCompra(Compra compra){
		
		String codigoUnico = null;
		
		try {
			codigoUnico = (String) this.getEntityManager().createQuery("Select relacion.codigoUnico from " +
					"RelacionCompraCodigoUnico relacion where relacion.compra = :paramCompra")
					.setParameter("paramCompra", compra).getSingleResult();
		} catch (NoResultException nre) {
			codigoUnico = null;
		}
		
		return codigoUnico;
		
	}
	
	public RelacionCompraCodigoUnico obtenerRelacionCodigoUnicoParaCompra(Compra compra){
		
		RelacionCompraCodigoUnico relacion = null;
		
		try {
			relacion = (RelacionCompraCodigoUnico) this.getEntityManager().createQuery("Select relacion from " +
					"RelacionCompraCodigoUnico relacion where relacion.compra = :paramCompra")
					.setParameter("paramCompra", compra).getSingleResult();
		} catch (NoResultException nre) {
			relacion = null;
		}
		
		return relacion;
		
	}
	
	public Boolean existeCodigoUnicoParaCompra(Compra compra) {
		return obtenerCodigoUnicoParaCompra(compra) != null;		
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public RelacionCompraCodigoUnico getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public String persist() {
		String retorno = super.persist();
		this.getInstance().getCompra().setCodigoDeBarrasGenerado(new Boolean(true));
		compraHome.setInstance(this.getInstance().getCompra());
		compraHome.update();
		facesMessages.clear();
		
		return retorno;
	}
	
	public String update() {
		String retorno = super.update();
		facesMessages.clear();
		
		return retorno;
	}
	
	public Compra obtenerCompraPorCodigoUnico (String codigoUnico) {
		
		Compra compra = null;
		
		try {
			compra = (Compra) this.getEntityManager().createQuery("Select r.compra from RelacionCompraCodigoUnico r " +
					"where r.codigoUnico like :paramCodigo").setParameter("paramCodigo", codigoUnico).getSingleResult();			
		} catch (NoResultException nre) {
			compra = null;
		}
		
		return compra;
	}
	
	public Venta obtenerVentaPorCodigoUnico (String codigoUnico) {
		
		Compra compra = this.obtenerCompraPorCodigoUnico(codigoUnico);
		Venta venta = null;
		
		if (compra != null) {
			
			try {
				venta = (Venta) this.getEntityManager().createQuery("Select v from Venta v " +
				"where v.compra like :paramCompra").setParameter("paramCompra", compra).getSingleResult();			
			} catch (NoResultException nre) {
				venta = null;
			}
		}

		return venta;
	}

}
