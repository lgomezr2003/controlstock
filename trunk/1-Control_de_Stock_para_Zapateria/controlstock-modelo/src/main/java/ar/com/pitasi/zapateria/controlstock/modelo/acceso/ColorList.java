package ar.com.pitasi.zapateria.controlstock.modelo.acceso;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import ar.com.pitasi.zapateria.controlstock.modelo.Color;

import java.util.Arrays;

@Name("colorList")
public class ColorList extends EntityQuery<Color> {

	private static final long serialVersionUID = 8597227475944484737L;

	private static final String EJBQL = "select color from Color color";

	private static final String[] RESTRICTIONS = {
			"lower(color.nombre) like lower(concat('%',#{colorList.color.nombre},'%'))",
			"lower(color.codigoColor) like lower(concat('%',#{colorList.color.codigoColor},'%'))",};

	private Color color = new Color();

	public ColorList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Color getColor() {
		return color;
	}
}
