package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

import ar.com.pitasi.zapateria.controlstock.modelo.TipoArticulo;

@Stateless
@Scope(ScopeType.SESSION)
public class AdministradorDeTareaBean implements AdministradorDeTarea {

	@PersistenceContext(unitName = "controlstockPersistenceUnit")
	private EntityManager entityManager;

	public Integer getCantidadHombre(Integer numero) {
		Integer cantidad = 0;

		try {
			cantidad = (Integer) this.entityManager
					.createQuery(
							"Select tarea.cantidad from Tarea tarea where "
									+ "tarea.numero = :paramNumero and tarea.tipoArticulo =:paramTipo")
					.setParameter("paramNumero", numero).setParameter(
							"paramTipo", TipoArticulo.CALZADO_HOMBRE)
					.getSingleResult();
		} catch (NoResultException nre) {
			cantidad = 0;
		} catch (NonUniqueResultException nur) {
			cantidad = 0;
		}

		return cantidad;
	}

	public Integer getCantidadMujer(Integer numero) {
		Integer cantidad = 0;

		try {
			cantidad = (Integer) this.entityManager
					.createQuery(
							"Select tarea.cantidad from Tarea tarea where "
									+ "tarea.numero = :paramNumero and tarea.tipoArticulo =:paramTipo")
					.setParameter("paramNumero", numero).setParameter(
							"paramTipo", TipoArticulo.CALZADO_MUJER)
					.getSingleResult();
		} catch (NoResultException nre) {
			cantidad = 0;
		} catch (NonUniqueResultException nur) {
			cantidad = 0;
		}

		return cantidad;
	}

}
