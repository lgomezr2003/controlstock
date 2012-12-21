package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.acciones.CargaVenta;
import ar.com.pitasi.zapateria.controlstock.acciones.SelectorTipoDeVenta;
import ar.com.pitasi.zapateria.controlstock.modelo.AsignacionTipoVentaTipoPago;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoPago;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoVenta;

@Name("tipoVentaHome")
public class TipoVentaHome extends EntityHome<TipoVenta> {

	private static final long serialVersionUID = 5663641561381177618L;
	private List<SelectItem> itemsParaTipoVenta;
	private List<String> idsTiposDePagoSeleccionados = new ArrayList<String>();

	@In(create = true)
	TipoPagoHome tipoPagoHome;
	@In(create = true)
	CargaVenta cargaVenta;
	@In(create = true)
	SelectorTipoDeVenta selectorTipoDeVenta;

	public void setTipoVentaIdTipoVenta(Integer id) {
		setId(id);
		idsTiposDePagoSeleccionados = new ArrayList<String>();
		
		if (isManaged()) {
			
			this.getEntityManager().refresh(this.getInstance());
			
			List <AsignacionTipoVentaTipoPago> asignaciones = 
				new ArrayList<AsignacionTipoVentaTipoPago>(this.getInstance().getTiposDePagoAceptados());
			
			for (AsignacionTipoVentaTipoPago asignacion : asignaciones) {
				this.idsTiposDePagoSeleccionados.add(String.valueOf(asignacion.getTipoPago().getIdTipoPago()));
				
			}
		}
	}

	public void setIdSeleccionadoParaTipoVenta(String id) {

		try {
			setId(Integer.valueOf(id));

		} catch (NumberFormatException nfe) {

			setId(null);
		}
	}

	public String getIdSeleccionadoParaTipoVenta() {

		String idSeleccionado = null;

		if (getId() == null) {

			if (!getTiposDeVenta().isEmpty()) {
				idSeleccionado = getTiposDeVenta().get(0).getIdTipoVenta()
						.toString();

			}
		}

		else {
			idSeleccionado = getId().toString();
		}

		return idSeleccionado;
	}

	public Integer getTipoVentaIdTipoVenta() {
		return (Integer) getId();
	}

	@Override
	protected TipoVenta createInstance() {
		TipoVenta tipoVenta = new TipoVenta();
		tipoVenta.setMinimoDeArticulosVendidos(1);
		tipoVenta.setAceptaCambios(true);
		tipoVenta.setPorcentajeDescuento(0);
		return tipoVenta;
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

	public TipoVenta getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	@SuppressWarnings("unchecked")
	public List<TipoVenta> getTiposDeVenta() {
		List<TipoVenta> tiposDeVenta = this.getEntityManager().createQuery(
				"Select t from TipoVenta t").getResultList();
		return tiposDeVenta;
	}

	@SuppressWarnings("unchecked")
	public List<SelectItem> getItemsParaTipoVentaSugeridos(
			Integer cantidadDeArticulos) {
		List<SelectItem> itemsParaTipoVenta = this.itemsParaTipoVenta;
		List<TipoVenta> tiposDeVenta = this
				.getEntityManager()
				.createQuery(
						"Select t from TipoVenta t where t.minimoDeArticulosVendidos <= :paramCantidad" +
						" and t.idTipoVenta != :paramIdUsado")
				.setParameter("paramCantidad", cantidadDeArticulos)
				.setParameter("paramIdUsado", this.selectorTipoDeVenta.getIdTipoVentaSeleccionado())
				.getResultList();

		itemsParaTipoVenta = new ArrayList<SelectItem>();

		for (TipoVenta tipoVenta : tiposDeVenta) {
			itemsParaTipoVenta.add(new SelectItem(tipoVenta.getIdTipoVenta(), tipoVenta.getDescripcion()));

		}
		
		cargaVenta.setIdTipoVentaSugeridoSeleccionado(null);

		return itemsParaTipoVenta;
	}

	public List<SelectItem> getItemsParaTipoVenta() {
		List<SelectItem> itemsParaTipoVenta = this.itemsParaTipoVenta;
		List<TipoVenta> tiposDeVenta = this.getTiposDeVenta();

		if (itemsParaTipoVenta == null) {

			itemsParaTipoVenta = new ArrayList<SelectItem>();

			for (TipoVenta tipoVenta : tiposDeVenta) {
				itemsParaTipoVenta.add(new SelectItem(tipoVenta.getIdTipoVenta(), tipoVenta.getDescripcion()));

			}

		}
		
		if (!itemsParaTipoVenta.isEmpty()) {
			
			if (cargaVenta.getTipoVentaSeleccionado() == null) {
				
				this.selectorTipoDeVenta.setIdTipoVentaSeleccionado( ((Integer)itemsParaTipoVenta.get(0).getValue()) , true );

			} else {
				
				this.selectorTipoDeVenta.setIdTipoVentaSeleccionado(cargaVenta.getTipoVentaSeleccionado().getIdTipoVenta() , true);
				
			}
			
		}
		

		return itemsParaTipoVenta;
	}

	public void setIdsTiposDePagoSeleccionados(
			List<String> idsTiposDePagoSeleccionados) {
		this.idsTiposDePagoSeleccionados = idsTiposDePagoSeleccionados;
	}

	public List<String> getIdsTiposDePagoSeleccionados() {
		return idsTiposDePagoSeleccionados;
	}

	public String persist() {

		if (this.getInstance().getPorcentajeDescuento() == null) {

			this.getInstance().setPorcentajeDescuento(0);
		}

		if (this.getInstance().getMinimoDeArticulosVendidos() == null) {

			this.getInstance().setMinimoDeArticulosVendidos(1);
		}

		String retorno = super.persist();

		for (String idTipoPago : idsTiposDePagoSeleccionados) {

			tipoPagoHome.setId(Integer.valueOf(idTipoPago));
			TipoPago tipoPago = tipoPagoHome.getInstance();

			AsignacionTipoVentaTipoPago asignacionTipoVentaTipoPago = new AsignacionTipoVentaTipoPago();
			asignacionTipoVentaTipoPago.setTipoPago(tipoPago);
			asignacionTipoVentaTipoPago.setTipoVenta(this.getInstance());

			this.getEntityManager().persist(asignacionTipoVentaTipoPago);

		}

		return retorno;
	}
	
	public String update() {

		List <AsignacionTipoVentaTipoPago> asignaciones = 
			new ArrayList<AsignacionTipoVentaTipoPago>(this.getInstance().getTiposDePagoAceptados());
		
		for (AsignacionTipoVentaTipoPago asignacion : asignaciones) {
			
			asignacion.setTipoVenta(null);
			asignacion.setTipoPago(null);
			this.getEntityManager().merge(asignacion);
			
		}
		
		for (String idTipoPago : idsTiposDePagoSeleccionados) {
			
			tipoPagoHome.setId(Integer.valueOf(idTipoPago));
			TipoPago tipoPago = tipoPagoHome.getInstance();
			tipoPagoHome.clearInstance();

			AsignacionTipoVentaTipoPago asignacionTipoVentaTipoPago = new AsignacionTipoVentaTipoPago();
			asignacionTipoVentaTipoPago.setTipoPago(tipoPago);
			asignacionTipoVentaTipoPago.setTipoVenta(this.getInstance());
			
			this.getEntityManager().persist(asignacionTipoVentaTipoPago);

		}
		
		String retorno = super.update();
		
		this.limpiarAsignacionesTipoVentaTipoPago();
		return retorno;
		
	}
	
	@SuppressWarnings("unchecked")
	private void limpiarAsignacionesTipoVentaTipoPago() {
		
		 List <AsignacionTipoVentaTipoPago>	asignacionesNulas = this.getEntityManager()
		 		.createQuery("SELECT asignacion FROM AsignacionTipoVentaTipoPago asignacion " +
				"WHERE asignacion.tipoVenta = null " +
				"AND asignacion.tipoPago = null")
				.getResultList();
		 
		 for (AsignacionTipoVentaTipoPago asignacion : asignacionesNulas) {
			 
			 this.getEntityManager().remove(asignacion);
			 
		 }
	}

}
