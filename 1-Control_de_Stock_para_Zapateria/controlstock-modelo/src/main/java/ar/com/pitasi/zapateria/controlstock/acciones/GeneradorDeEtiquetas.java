package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.List;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;

public interface GeneradorDeEtiquetas {
	
	public void init();

	public void destroy();
	
	public String comenzarSeleccion();
	public String seleccionarCompraParaImpresion(Compra compra);
	public String deseleccionarCompraParaImpresion(Compra compra);
	public void setSeleccionandoCompras(Boolean seleccionandoCompras);
	public Boolean getSeleccionandoCompras();
	public String generarEtiquetas();
	public Boolean estaSeleccionada(Compra compra);
	public String seleccionarTodo(List <Compra> compras);
}
