package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.TipoPago;

@Name("tipoPagoHome")
public class TipoPagoHome extends EntityHome<TipoPago> {

	private static final long serialVersionUID = 5068255930521360452L;

	private List<SelectItem> itemsParaTipoPago;
	
	private List<TipoPago> tiposDePagoSeleccionables = new ArrayList <TipoPago> ();

	@In(create = true)
	TipoVentaHome tipoVentaHome;

	public void setTipoPagoIdTipoPago(Integer id) {
		setId(id);
	}

	public Integer getTipoPagoIdTipoPago() {
		return (Integer) getId();
	}

	@Override
	protected TipoPago createInstance() {
		TipoPago tipoPago = new TipoPago();
		tipoPago.setRecargo(0);
		return tipoPago;
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

	public TipoPago getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	@SuppressWarnings("unchecked")
	public List<TipoPago> getTiposDePago() {
		List<TipoPago> tiposDePago = this.getEntityManager().createQuery(
				"Select t from TipoPago t").getResultList();
		return tiposDePago;
	}

	@SuppressWarnings("unchecked")
	public List<TipoPago> getTiposDePagoParaTipoDeVenta(int idTipoVenta) {

		tiposDePagoSeleccionables = this.getEntityManager()
				.createQuery(
						"Select a.tipoPago from AsignacionTipoVentaTipoPago a where a.tipoVenta.idTipoVenta = :paramTipoVenta")
				.setParameter("paramTipoVenta", idTipoVenta).getResultList();
		
		return tiposDePagoSeleccionables;
	}
	
	public void activarSeleccionPorDefecto() {
		
		if (!tiposDePagoSeleccionables.isEmpty()) {
			this.setTipoPagoIdTipoPago(tiposDePagoSeleccionables.get(0).getIdTipoPago());
		}
		
	}

	public List<SelectItem> getItemsParaTipoPago() {
		List<SelectItem> itemsParaTipoPago = this.itemsParaTipoPago;
		List<TipoPago> tiposDePago = this.getTiposDePago();

		if (itemsParaTipoPago == null) {

			itemsParaTipoPago = new ArrayList<SelectItem>();

			for (TipoPago tipoPago : tiposDePago) {
				itemsParaTipoPago.add(new SelectItem(tipoPago.getIdTipoPago(),
						tipoPago.getSigla()));

			}

		}

		return itemsParaTipoPago;
	}

	public List<SelectItem> getItemsParaTipoPagoParaTipoDeVenta(int idTipoVenta) {

		List<SelectItem> itemsParaTipoPago = new ArrayList<SelectItem>();
		List<TipoPago> tiposDePago = this
				.getTiposDePagoParaTipoDeVenta(idTipoVenta);

		itemsParaTipoPago = new ArrayList<SelectItem>();

		for (TipoPago tipoPago : tiposDePago) {
			itemsParaTipoPago.add(new SelectItem(tipoPago.getIdTipoPago(),
					tipoPago.getSigla()));

		}
		
		return itemsParaTipoPago;
	}

	public String persist() {

		if (this.getInstance().getRecargo() == null) {

			this.getInstance().setRecargo(0);
		}

		return super.persist();
	}
}
