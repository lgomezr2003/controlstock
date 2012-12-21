package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.Date;

public interface CargaCompra {

	public void init();

	public void destroy();

	public String comenzarCargaDeCompras();
	public String siguiente();
	
	public void setCondicionArticulo(String condicionArticulo);
	public String getCondicionArticulo();
	
	public Integer getCantidadHombre39();
	public void setCantidadHombre39(Integer cantidadHombre39);
	public Integer getCantidadHombre40();
	public void setCantidadHombre40(Integer cantidadHombre40);
	public Integer getCantidadHombre41();
	public void setCantidadHombre41(Integer cantidadHombre41);
	public Integer getCantidadHombre42();
	public void setCantidadHombre42(Integer cantidadHombre42);
	public Integer getCantidadHombre43();
	public void setCantidadHombre43(Integer cantidadHombre43);
	public Integer getCantidadHombre44();
	public void setCantidadHombre44(Integer cantidadHombre44);
	public Integer getCantidadMujer35();
	public void setCantidadMujer35(Integer cantidadMujer35);
	public Integer getCantidadMujer36();
	public void setCantidadMujer36(Integer cantidadMujer36);
	public Integer getCantidadMujer37();
	public void setCantidadMujer37(Integer cantidadMujer37);
	public Integer getCantidadMujer38();
	public void setCantidadMujer38(Integer cantidadMujer38);
	public Integer getCantidadMujer39();
	public void setCantidadMujer39(Integer cantidadMujer39);
	public Integer getCantidadMujer40();
	public void setCantidadMujer40(Integer cantidadMujer40);
	public Integer getCantidadNinio27();
	public void setCantidadNinio27(Integer cantidadNinio27);
	public Integer getCantidadNinio28();
	public void setCantidadNinio28(Integer cantidadNinio28);
	public Integer getCantidadNinio29();
	public void setCantidadNinio29(Integer cantidadNinio29);
	public Integer getCantidadNinio30();
	public void setCantidadNinio30(Integer cantidadNinio30);
	public Integer getCantidadNinio31();
	public void setCantidadNinio31(Integer cantidadNinio31);
	public Integer getCantidadNinio32();
	public void setCantidadNinio32(Integer cantidadNinio32);
	public Integer getCantidadNinio33();
	public void setCantidadNinio33(Integer cantidadNinio33);
	public Integer getCantidadNinio34();
	public void setCantidadNinio34(Integer cantidadNinio34);
	
	public Integer getCantidadHombreBase39();
	public void setCantidadHombreBase39(Integer cantidadHombre39);
	public Integer getCantidadHombreBase40();
	public void setCantidadHombreBase40(Integer cantidadHombre40);
	public Integer getCantidadHombreBase41();
	public void setCantidadHombreBase41(Integer cantidadHombre41);
	public Integer getCantidadHombreBase42();
	public void setCantidadHombreBase42(Integer cantidadHombre42);
	public Integer getCantidadHombreBase43();
	public void setCantidadHombreBase43(Integer cantidadHombre43);
	public Integer getCantidadHombreBase44();
	public void setCantidadHombreBase44(Integer cantidadHombre44);
	
	public Integer getCantidadMujerBase35();
	public void setCantidadMujerBase35(Integer cantidadMujer35);
	public Integer getCantidadMujerBase36();
	public void setCantidadMujerBase36(Integer cantidadMujer36);
	public Integer getCantidadMujerBase37();
	public void setCantidadMujerBase37(Integer cantidadMujer37);
	public Integer getCantidadMujerBase38();
	public void setCantidadMujerBase38(Integer cantidadMujer38);
	public Integer getCantidadMujerBase39();
	public void setCantidadMujerBase39(Integer cantidadMujer39);
	public Integer getCantidadMujerBase40();
	public void setCantidadMujerBase40(Integer cantidadMujer40);	
	
	public Integer getCantidadNinioBase27();
	public void setCantidadNinioBase27(Integer cantidadNinio27);
	public Integer getCantidadNinioBase28();
	public void setCantidadNinioBase28(Integer cantidadNinio28);
	public Integer getCantidadNinioBase29();
	public void setCantidadNinioBase29(Integer cantidadNinio29);
	public Integer getCantidadNinioBase30();
	public void setCantidadNinioBase30(Integer cantidadNinio30);
	public Integer getCantidadNinioBase31();
	public void setCantidadNinioBase31(Integer cantidadNinio31);
	public Integer getCantidadNinioBase32();
	public void setCantidadNinioBase32(Integer cantidadNinio32);
	public Integer getCantidadNinioBase33();
	public void setCantidadNinioBase33(Integer cantidadNinio33);
	public Integer getCantidadNinioBase34();
	public void setCantidadNinioBase34(Integer cantidadNinio34);
	
	public Double getCostoUnitarioSeleccionado();
	public void setCostoUnitarioSeleccionado(Double costoUnitarioSeleccionado);
	
	public String guardarCompra();
	
	public void setFechaDeCarga(Date fechaDeCarga);
	
	public Date getFechaDeCarga();
	
	public void setCantidadDeTareas(Integer cantidadDeTareas);
	public Integer getCantidadDeTareas();
	
	public Double getPrecioDeVentaSeleccionado();
	public void setPrecioDeVentaSeleccionado(Double precioDeVentaSeleccionado);
	public void actualizarCantidades();
	
}
