package ar.com.pitasi.zapateria.controlstock.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.Validator;

import org.jboss.seam.Component;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.RelacionCompraCodigoUnicoHome;

public class ValidadorDeArticuloVendido implements Validator {

	public void validate(FacesContext context, UIComponent toValidate,
			Object value) {
		
		String codigoDeArticuloId = (String) toValidate.getAttributes().get("codigoDeArticuloId");
		UIInput codigoInput = (UIInput) context.getViewRoot().findComponent(codigoDeArticuloId);
		String codigo = (String) codigoInput.getSubmittedValue();
		
		RelacionCompraCodigoUnicoHome relacionCompraCodigoUnicoHome = (RelacionCompraCodigoUnicoHome) Component.getInstance("relacionCompraCodigoUnicoHome"); 
		Compra compraAValidar = relacionCompraCodigoUnicoHome.obtenerCompraPorCodigoUnico(codigo);
		 
		 if( compraAValidar != null && !compraAValidar.getVendida() ){

		    	FacesMessage message = new FacesMessage();
		    	message.setSeverity(FacesMessage.SEVERITY_ERROR);
		    	message.setSummary(" ");
		    	message.setDetail("Este articulo no ha sido vendido. Intente con un nuevo articulo");
		    	context.addMessage(codigoInput.getClientId(context),message);
		    	throw new ValidatorException(message);
		 }
		
	}

}
