package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import javax.persistence.NoResultException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import ar.com.pitasi.zapateria.controlstock.modelo.Color;

@Name("colorHome")
public class ColorHome extends EntityHome<Color> {

	private static final long serialVersionUID = -7884223024343626239L;

	private String codigoSeleccionado;

	public void setColorIdColor(Integer id) {
		setId(id);
		
		if (getInstance() != null) {
			this.codigoSeleccionado = String.valueOf(this.getInstance().getCodigoColor());			
		}
	}

	public Integer getColorIdColor() {
		return (Integer) getId();
	}

	@Override
	protected Color createInstance() {
		Color color = new Color();
		color.setCodigoColor(crearNuevoCodigo());
		return color;
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

	public Color getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public String persist () {
		String retorno = super.persist();
		this.setId(null);
		return retorno;
	}

	public void setCodigoSeleccionado(String codigoSeleccionado) {
		this.codigoSeleccionado = codigoSeleccionado;
		Color colorEncontrado;
		
		try {
			colorEncontrado = (Color) this.getEntityManager().createQuery("Select color from Color color " +
			"where color.codigoColor = :paramCodigo").setParameter("paramCodigo", Integer.valueOf(codigoSeleccionado))
			.getSingleResult();		
			
			setInstance(colorEncontrado);
		} catch (NoResultException nre) {
			//TODO: log error
		} catch (NumberFormatException nfe) {
			//TODO: log error
		}
	}

	public String getCodigoSeleccionado() {
		return codigoSeleccionado;
	}
	
	private Integer crearNuevoCodigo() {
		
		final int INCREMENTO = 1;
		Integer ultimoCodigo = 0;
		
		try {
			ultimoCodigo = (Integer) this.getEntityManager().createQuery("Select max(color.codigoColor) from " +
					"Color color").getSingleResult();

		}
		
		catch (NoResultException nre) {

			ultimoCodigo = 0;
			
		}
		
		if (ultimoCodigo == null ) {
			
			ultimoCodigo = 0;
			
		}
		
		Integer nuevoCodigo = ultimoCodigo + INCREMENTO;
		
		return nuevoCodigo;
		
	}

}
