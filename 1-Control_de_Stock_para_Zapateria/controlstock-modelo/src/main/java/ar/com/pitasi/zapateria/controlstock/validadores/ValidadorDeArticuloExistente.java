package ar.com.pitasi.zapateria.controlstock.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.Component;

import ar.com.pitasi.zapateria.controlstock.modelo.acceso.ArticuloHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.ProveedorHome;

public class ValidadorDeArticuloExistente implements Validator {

	public void validate(FacesContext context, UIComponent toValidate,
			Object value) {

		String codigoDeArticuloNuevo = (String) toValidate.getAttributes().get(
				"codigoArticuloNuevo");
		UIInput codigoInput = (UIInput) context.getViewRoot().findComponent(
				codigoDeArticuloNuevo);
		String codigo = (String) codigoInput.getSubmittedValue();

		ArticuloHome articuloHome = (ArticuloHome) Component
				.getInstance("articuloHome");
		ProveedorHome proveedorHome = (ProveedorHome) Component
				.getInstance("proveedorHome");

		if (articuloHome.existeArticulo(codigo, proveedorHome.getInstance(),
				articuloHome.getInstance().getTipoArticulo())) {

			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_WARN);
			message.setSummary(" ");
			message.setDetail(" El codigo de articulo seleccionado ya existe para este proveedor y este tipo de articulo. Si continua la informacion se sobreescribira.");
			context.addMessage(codigoInput.getClientId(context), message);
			throw new ValidatorException(message);
		}

	}

}
