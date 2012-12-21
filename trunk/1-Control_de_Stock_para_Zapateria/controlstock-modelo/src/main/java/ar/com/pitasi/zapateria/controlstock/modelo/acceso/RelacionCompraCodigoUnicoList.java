package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.RelacionCompraCodigoUnico;

@Name("relacionCompraCodigoUnicoList")
public class RelacionCompraCodigoUnicoList extends EntityQuery<RelacionCompraCodigoUnico> {

	private static final String EJBQL = "select relacionCompraCodigoUnico from RelacionCompraCodigoUnico relacionCompraCodigoUnico";

	private static final String[] RESTRICTIONS = {"relacionCompraCodigoUnico.compra = #{relacionCompraCodigoUnicoList.relacionCompraCodigoUnico.compra}",
		"relacionCompraCodigoUnico.codigoUnico = #{relacionCompraCodigoUnicoList.relacionCompraCodigoUnico.codigoUnico}",
		"relacionCompraCodigoUnico.compra.fecha = #{relacionCompraCodigoUnicoList.fechaDeCompra}",
		"relacionCompraCodigoUnico.compra.articulo.codigoArticulo like " +
		"concat('%',#{relacionCompraCodigoUnicoList.codigoArticulo},'%')"};

	private RelacionCompraCodigoUnico relacionCompraCodigoUnico = new RelacionCompraCodigoUnico();
	private Date fechaDeCompra;
	private String codigoArticulo;

	private List <RelacionCompraCodigoUnico> resultadoRelaciones;

	public RelacionCompraCodigoUnicoList() {
		
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public List<RelacionCompraCodigoUnico> getResultList() {
		
		if (resultadoRelaciones == null) {
			resultadoRelaciones = super.getResultList();
		}
		
		return resultadoRelaciones;
	}
	
	public RelacionCompraCodigoUnico getRelacionCompraCodigoUnico() {
		return relacionCompraCodigoUnico;
	}

	public void setFechaDeCompra(Date fechaDeCompra) {
		this.fechaDeCompra = fechaDeCompra;
	}

	public Date getFechaDeCompra() {
		return fechaDeCompra;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}
}
