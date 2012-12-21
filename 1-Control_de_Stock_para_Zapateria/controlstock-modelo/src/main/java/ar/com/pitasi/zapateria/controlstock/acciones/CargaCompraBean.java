package ar.com.pitasi.zapateria.controlstock.acciones;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Init;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import ar.com.pitasi.zapateria.controlstock.modelo.Articulo;
import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.Proveedor;
import ar.com.pitasi.zapateria.controlstock.modelo.TipoArticulo;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.AdministradorDeTarea;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.ArticuloHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.ColorHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.CompraHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.ProveedorHome;

@Stateful
@Name("cargaCompra")
@Scope(ScopeType.CONVERSATION)
public class CargaCompraBean implements CargaCompra {

	@EJB
	private AdministradorDeTarea administradorDeTarea;

	@In(create = true)
	CompraHome compraHome;
	@In(create = true)
	ArticuloHome articuloHome;
	@In(create = true)
	ProveedorHome proveedorHome;
	@In(create = true)
	ColorHome colorHome;

	@In
	FacesMessages facesMessages;

	private String condicionArticulo;
	private Date fechaDeCarga;
	
	//Cantidades definidas
	private Integer cantidadHombre39;
	private Integer cantidadHombre40;
	private Integer cantidadHombre41;
	private Integer cantidadHombre42;
	private Integer cantidadHombre43;
	private Integer cantidadHombre44;

	private Integer cantidadMujer35;
	private Integer cantidadMujer36;
	private Integer cantidadMujer37;
	private Integer cantidadMujer38;
	private Integer cantidadMujer39;
	private Integer cantidadMujer40;

	private Integer cantidadNinio27;
	private Integer cantidadNinio28;
	private Integer cantidadNinio29;
	private Integer cantidadNinio30;
	private Integer cantidadNinio31;
	private Integer cantidadNinio32;
	private Integer cantidadNinio33;
	private Integer cantidadNinio34;

	// Cantidades De base
	private Integer cantidadHombreBase39;
	private Integer cantidadHombreBase40;
	private Integer cantidadHombreBase41;
	private Integer cantidadHombreBase42;
	private Integer cantidadHombreBase43;
	private Integer cantidadHombreBase44;

	private Integer cantidadMujerBase35;
	private Integer cantidadMujerBase36;
	private Integer cantidadMujerBase37;
	private Integer cantidadMujerBase38;
	private Integer cantidadMujerBase39;
	private Integer cantidadMujerBase40;
	
	private Integer cantidadNinioBase27;
	private Integer cantidadNinioBase28;
	private Integer cantidadNinioBase29;
	private Integer cantidadNinioBase30;
	private Integer cantidadNinioBase31;
	private Integer cantidadNinioBase32;
	private Integer cantidadNinioBase33;
	private Integer cantidadNinioBase34;

	private Double costoUnitarioSugerido;
	private Double precioDeVentaSugerido;
	// private Double precioDeVentaSugeridoPorMayor;
	private Double costoUnitarioSeleccionado;
	private Double precioDeVentaSeleccionado;
	// private Double precioDeVentaSeleccionadoPorMayor;

	private Integer cantidadDeTareas;
	private final int CANTIDAD_INICIAL_DE_TAREAS = 1;

	@Begin(join = true)
	public String comenzarCargaDeCompras() {

		this.reset();

		cantidadHombreBase39 = administradorDeTarea.getCantidadHombre(39);
		cantidadHombreBase40 = administradorDeTarea.getCantidadHombre(40);
		cantidadHombreBase41 = administradorDeTarea.getCantidadHombre(41);
		cantidadHombreBase42 = administradorDeTarea.getCantidadHombre(42);
		cantidadHombreBase43 = administradorDeTarea.getCantidadHombre(43);
		cantidadHombreBase44 = administradorDeTarea.getCantidadHombre(44);

		cantidadMujerBase35 = administradorDeTarea.getCantidadMujer(35);
		cantidadMujerBase36 = administradorDeTarea.getCantidadMujer(36);
		cantidadMujerBase37 = administradorDeTarea.getCantidadMujer(37);
		cantidadMujerBase38 = administradorDeTarea.getCantidadMujer(38);
		cantidadMujerBase39 = administradorDeTarea.getCantidadMujer(39);
		cantidadMujerBase40 = administradorDeTarea.getCantidadMujer(40);
		
		cantidadNinioBase27 = 0;
		cantidadNinioBase28 = 0;
		cantidadNinioBase29 = 0;
		cantidadNinioBase30 = 0;
		cantidadNinioBase31 = 0;
		cantidadNinioBase32 = 0;
		cantidadNinioBase33 = 0;
		cantidadNinioBase34 = 0;

		this.setCantidadDeTareas(CANTIDAD_INICIAL_DE_TAREAS);

		this.actualizarCantidades();

		return String.valueOf(ResultadoNavegacion.CARGA_COMPRAS);
	}

	public String siguiente() {
		
		if (condicionArticulo.equals("E") && articuloHome.getInstance() != null) {

			costoUnitarioSugerido = compraHome
					.obtenerUltimoCostoUnitarioParaArticulo(articuloHome
							.getInstance());

			if (costoUnitarioSugerido != null) {
				costoUnitarioSeleccionado = costoUnitarioSugerido;
			}

			precioDeVentaSugerido = compraHome
					.obtenerUltimoPrecioDeVentaParaArticulo(articuloHome
							.getInstance());

			if (precioDeVentaSugerido != null) {
				precioDeVentaSeleccionado = precioDeVentaSugerido;
			}

		}
		
		return String.valueOf(ResultadoNavegacion.CARGA_ARTICULO);
	}
	
	public void setCondicionArticulo(String condicionArticulo) {
		this.condicionArticulo = condicionArticulo;
	}

	public String guardarCompra() {

		// CASO 1: Compra de un lote de articulos que no existian en al base

		if (condicionArticulo.equals("N")) {
			Proveedor proveedor = proveedorHome.getInstance();

			articuloHome.getInstance().setProveedor(proveedor);

			// Comprueba que el articulo no esté cargado
			if (!articuloHome.existeArticulo(articuloHome.getInstance()
					.getCodigoArticulo(), proveedor, articuloHome.getInstance()
					.getTipoArticulo())) {
				articuloHome.persist();
			} else {
				articuloHome.update();
			}
		}

		Articulo articuloGenerico = articuloHome.getInstance();

		if (articuloGenerico.getTipoArticulo() == TipoArticulo.CALZADO_HOMBRE) {
			persistirComprasParaHombre(articuloGenerico);
		} else if (articuloGenerico.getTipoArticulo() == TipoArticulo.CALZADO_MUJER) {
			persistirComprasParaMujer(articuloGenerico);
		} else if (articuloGenerico.getTipoArticulo() == TipoArticulo.CALZADO_NINIO) {
			persistirComprasParaNinio(articuloGenerico);
		}

		compraHome.clearInstance();
		articuloHome.clearInstance();
		proveedorHome.clearInstance();

		return String.valueOf(ResultadoNavegacion.EXITO);
	}

	private void persistirComprasParaMujer(Articulo articulo) {

		Map<Integer, Integer> cantidadesPorNumero = new HashMap<Integer, Integer>();
		cantidadesPorNumero.put(35, cantidadMujer35);
		cantidadesPorNumero.put(36, cantidadMujer36);
		cantidadesPorNumero.put(37, cantidadMujer37);
		cantidadesPorNumero.put(38, cantidadMujer38);
		cantidadesPorNumero.put(39, cantidadMujer39);
		cantidadesPorNumero.put(40, cantidadMujer40);

		persistirCompras(articulo, cantidadesPorNumero);

	}

	private void persistirComprasParaHombre(Articulo articulo) {

		Map<Integer, Integer> cantidadesPorNumero = new HashMap<Integer, Integer>();
		cantidadesPorNumero.put(39, cantidadHombre39);
		cantidadesPorNumero.put(40, cantidadHombre40);
		cantidadesPorNumero.put(41, cantidadHombre41);
		cantidadesPorNumero.put(42, cantidadHombre42);
		cantidadesPorNumero.put(43, cantidadHombre43);
		cantidadesPorNumero.put(44, cantidadHombre44);

		persistirCompras(articulo, cantidadesPorNumero);

	}

	private void persistirComprasParaNinio(Articulo articulo) {

		Map<Integer, Integer> cantidadesPorNumero = new HashMap<Integer, Integer>();
		cantidadesPorNumero.put(27, cantidadNinio27);
		cantidadesPorNumero.put(28, cantidadNinio28);
		cantidadesPorNumero.put(29, cantidadNinio29);
		cantidadesPorNumero.put(30, cantidadNinio30);
		cantidadesPorNumero.put(31, cantidadNinio31);
		cantidadesPorNumero.put(32, cantidadNinio32);
		cantidadesPorNumero.put(33, cantidadNinio33);
		cantidadesPorNumero.put(34, cantidadNinio34);

		persistirCompras(articulo, cantidadesPorNumero);

	}

	private void actualizarPreciosDeComprasEnStock() {
		List<Compra> comprasEnStock = compraHome
				.obtenerComprasEnStockParaArticulo(articuloHome.getInstance());

		for (Compra compra : comprasEnStock) {

			if (precioDeVentaSeleccionado != precioDeVentaSugerido) {
				compra.setPrecioDeVenta(precioDeVentaSeleccionado);
				compraHome.setInstance(compra);
				compraHome.update();
			}

		}

	}

	@End
	private void persistirCompras(Articulo articulo,
			Map<Integer, Integer> cantidadesPorNumero) {

		for (Integer cantidad : cantidadesPorNumero.values()) {
			if (cantidad == null) {
				cantidad = 0;
			}
		}

		// Para cada numero
		for (Integer numero : cantidadesPorNumero.keySet()) {
			// guarda tantas compras como cantidad de numeros comprados
			for (int i = 0; i < (cantidadesPorNumero.get(numero)); i++) {

				Compra compraAPersistir = new Compra();
				compraAPersistir.setFecha(fechaDeCarga);
				compraAPersistir.setCostoUnitario(costoUnitarioSeleccionado);
				compraAPersistir.setArticulo(articulo);
				compraAPersistir.setNumero(numero);
				compraAPersistir.setPrecioDeVenta(precioDeVentaSeleccionado);
				// compraAPersistir.setPrecioDeVentaPorMayor(precioDeVentaSeleccionadoPorMayor);
				compraAPersistir.setColor(colorHome.getInstance());
				Integer codigoGenerado = compraHome.generarNuevoCodigo(compraAPersistir);
				compraAPersistir.setCodigoCompra(codigoGenerado);

				compraHome.setInstance(compraAPersistir);
				compraHome.persist();
			}
		}

		this.actualizarPreciosDeComprasEnStock();
		facesMessages.add("Compras guardadas correctamente");

	}

	public void actualizarCantidades() {
		this.cantidadHombre39 = cantidadHombreBase39 * cantidadDeTareas;
		this.cantidadHombre40 = cantidadHombreBase40 * cantidadDeTareas;
		this.cantidadHombre41 = cantidadHombreBase41 * cantidadDeTareas;
		this.cantidadHombre42 = cantidadHombreBase42 * cantidadDeTareas;
		this.cantidadHombre43 = cantidadHombreBase43 * cantidadDeTareas;
		this.cantidadHombre44 = cantidadHombreBase44 * cantidadDeTareas;

		this.cantidadMujer35 = cantidadMujerBase35 * cantidadDeTareas;
		this.cantidadMujer36 = cantidadMujerBase36 * cantidadDeTareas;
		this.cantidadMujer37 = cantidadMujerBase37 * cantidadDeTareas;
		this.cantidadMujer38 = cantidadMujerBase38 * cantidadDeTareas;
		this.cantidadMujer39 = cantidadMujerBase39 * cantidadDeTareas;
		this.cantidadMujer40 = cantidadMujerBase40 * cantidadDeTareas;
		
		this.cantidadNinio27 = cantidadNinioBase27 * cantidadDeTareas;
		this.cantidadNinio28 = cantidadNinioBase28 * cantidadDeTareas;
		this.cantidadNinio29 = cantidadNinioBase29 * cantidadDeTareas;
		this.cantidadNinio30 = cantidadNinioBase30 * cantidadDeTareas;
		this.cantidadNinio31 = cantidadNinioBase31 * cantidadDeTareas;
		this.cantidadNinio32 = cantidadNinioBase32 * cantidadDeTareas;
		this.cantidadNinio33 = cantidadNinioBase33 * cantidadDeTareas;
		this.cantidadNinio34 = cantidadNinioBase34 * cantidadDeTareas;
	}

	public String getCondicionArticulo() {
		return condicionArticulo;
	}

	public Integer getCantidadHombre39() {
		return cantidadHombre39;
	}

	public void setCantidadHombre39(Integer cantidadHombre39) {
		this.cantidadHombre39 = cantidadHombre39;
	}

	public Integer getCantidadHombre40() {
		return cantidadHombre40;
	}

	public void setCantidadHombre40(Integer cantidadHombre40) {
		this.cantidadHombre40 = cantidadHombre40;
	}

	public Integer getCantidadHombre41() {
		return cantidadHombre41;
	}

	public void setCantidadHombre41(Integer cantidadHombre41) {
		this.cantidadHombre41 = cantidadHombre41;
	}

	public Integer getCantidadHombre42() {
		return cantidadHombre42;
	}

	public void setCantidadHombre42(Integer cantidadHombre42) {
		this.cantidadHombre42 = cantidadHombre42;
	}

	public Integer getCantidadHombre43() {
		return cantidadHombre43;
	}

	public void setCantidadHombre43(Integer cantidadHombre43) {
		this.cantidadHombre43 = cantidadHombre43;
	}

	public Integer getCantidadHombre44() {
		return cantidadHombre44;
	}

	public void setCantidadHombre44(Integer cantidadHombre44) {
		this.cantidadHombre44 = cantidadHombre44;
	}

	public Integer getCantidadMujer35() {
		return cantidadMujer35;
	}

	public void setCantidadMujer35(Integer cantidadMujer35) {
		this.cantidadMujer35 = cantidadMujer35;
	}

	public Integer getCantidadMujer36() {
		return cantidadMujer36;
	}

	public void setCantidadMujer36(Integer cantidadMujer36) {
		this.cantidadMujer36 = cantidadMujer36;
	}

	public Integer getCantidadMujer37() {
		return cantidadMujer37;
	}

	public void setCantidadMujer37(Integer cantidadMujer37) {
		this.cantidadMujer37 = cantidadMujer37;
	}

	public Integer getCantidadMujer38() {
		return cantidadMujer38;
	}

	public void setCantidadMujer38(Integer cantidadMujer38) {
		this.cantidadMujer38 = cantidadMujer38;
	}

	public Integer getCantidadMujer39() {
		return cantidadMujer39;
	}

	public void setCantidadMujer39(Integer cantidadMujer39) {
		this.cantidadMujer39 = cantidadMujer39;
	}

	public Integer getCantidadMujer40() {
		return cantidadMujer40;
	}

	public void setCantidadMujer40(Integer cantidadMujer40) {
		this.cantidadMujer40 = cantidadMujer40;
	}

	public Integer getCantidadNinio27() {
		return cantidadNinio27;
	}

	public void setCantidadNinio27(Integer cantidadNinio27) {
		this.cantidadNinio27 = cantidadNinio27;
	}

	public Integer getCantidadNinio28() {
		return cantidadNinio28;
	}

	public void setCantidadNinio28(Integer cantidadNinio28) {
		this.cantidadNinio28 = cantidadNinio28;
	}

	public Integer getCantidadNinio29() {
		return cantidadNinio29;
	}

	public void setCantidadNinio29(Integer cantidadNinio29) {
		this.cantidadNinio29 = cantidadNinio29;
	}

	public Integer getCantidadNinio30() {
		return cantidadNinio30;
	}

	public void setCantidadNinio30(Integer cantidadNinio30) {
		this.cantidadNinio30 = cantidadNinio30;
	}

	public Integer getCantidadNinio31() {
		return cantidadNinio31;
	}

	public void setCantidadNinio31(Integer cantidadNinio31) {
		this.cantidadNinio31 = cantidadNinio31;
	}

	public Integer getCantidadNinio32() {
		return cantidadNinio32;
	}

	public void setCantidadNinio32(Integer cantidadNinio32) {
		this.cantidadNinio32 = cantidadNinio32;
	}

	public Integer getCantidadNinio33() {
		return cantidadNinio33;
	}

	public void setCantidadNinio33(Integer cantidadNinio33) {
		this.cantidadNinio33 = cantidadNinio33;
	}

	public Integer getCantidadNinio34() {
		return cantidadNinio34;
	}

	public void setCantidadNinio34(Integer cantidadNinio34) {
		this.cantidadNinio34 = cantidadNinio34;
	}

	public Integer getCantidadHombreBase39() {
		return cantidadHombreBase39;
	}

	public void setCantidadHombreBase39(Integer cantidadHombre39) {
		this.cantidadHombreBase39 = cantidadHombre39;
	}

	public Integer getCantidadHombreBase40() {
		return cantidadHombreBase40;
	}

	public void setCantidadHombreBase40(Integer cantidadHombre40) {
		this.cantidadHombreBase40 = cantidadHombre40;
	}

	public Integer getCantidadHombreBase41() {
		return cantidadHombreBase41;
	}

	public void setCantidadHombreBase41(Integer cantidadHombre41) {
		this.cantidadHombreBase41 = cantidadHombre41;
	}

	public Integer getCantidadHombreBase42() {
		return cantidadHombreBase42;
	}

	public void setCantidadHombreBase42(Integer cantidadHombre42) {
		this.cantidadHombreBase42 = cantidadHombre42;
	}

	public Integer getCantidadHombreBase43() {
		return cantidadHombreBase43;
	}

	public void setCantidadHombreBase43(Integer cantidadHombre43) {
		this.cantidadHombreBase43 = cantidadHombre43;
	}

	public Integer getCantidadHombreBase44() {
		return cantidadHombreBase44;
	}

	public void setCantidadHombreBase44(Integer cantidadHombre44) {
		this.cantidadHombreBase44 = cantidadHombre44;
	}

	public Integer getCantidadMujerBase35() {
		return cantidadMujerBase35;
	}

	public void setCantidadMujerBase35(Integer cantidadMujer35) {
		this.cantidadMujerBase35 = cantidadMujer35;
	}

	public Integer getCantidadMujerBase36() {
		return cantidadMujerBase36;
	}

	public void setCantidadMujerBase36(Integer cantidadMujer36) {
		this.cantidadMujerBase36 = cantidadMujer36;
	}

	public Integer getCantidadMujerBase37() {
		return cantidadMujerBase37;
	}

	public void setCantidadMujerBase37(Integer cantidadMujer37) {
		this.cantidadMujerBase37 = cantidadMujer37;
	}

	public Integer getCantidadMujerBase38() {
		return cantidadMujerBase38;
	}

	public void setCantidadMujerBase38(Integer cantidadMujer38) {
		this.cantidadMujerBase38 = cantidadMujer38;
	}

	public Integer getCantidadMujerBase39() {
		return cantidadMujerBase39;
	}

	public void setCantidadMujerBase39(Integer cantidadMujer39) {
		this.cantidadMujerBase39 = cantidadMujer39;
	}

	public Integer getCantidadMujerBase40() {
		return cantidadMujerBase40;
	}

	public void setCantidadMujerBase40(Integer cantidadMujer40) {
		this.cantidadMujerBase40 = cantidadMujer40;
	}

	public Integer getCantidadNinioBase27() {
		return cantidadNinioBase27;
	}

	public void setCantidadNinioBase27(Integer cantidadNinio27) {
		this.cantidadNinioBase27 = cantidadNinio27;
	}

	public Integer getCantidadNinioBase28() {
		return cantidadNinioBase28;
	}

	public void setCantidadNinioBase28(Integer cantidadNinio28) {
		this.cantidadNinioBase28 = cantidadNinio28;
	}

	public Integer getCantidadNinioBase29() {
		return cantidadNinioBase29;
	}

	public void setCantidadNinioBase29(Integer cantidadNinio29) {
		this.cantidadNinioBase29 = cantidadNinio29;
	}

	public Integer getCantidadNinioBase30() {
		return cantidadNinioBase30;
	}

	public void setCantidadNinioBase30(Integer cantidadNinio30) {
		this.cantidadNinioBase30 = cantidadNinio30;
	}

	public Integer getCantidadNinioBase31() {
		return cantidadNinioBase31;
	}

	public void setCantidadNinioBase31(Integer cantidadNinio31) {
		this.cantidadNinioBase31 = cantidadNinio31;
	}

	public Integer getCantidadNinioBase32() {
		return cantidadNinioBase32;
	}

	public void setCantidadNinioBase32(Integer cantidadNinio32) {
		this.cantidadNinioBase32 = cantidadNinio32;
	}

	public Integer getCantidadNinioBase33() {
		return cantidadNinioBase33;
	}

	public void setCantidadNinioBase33(Integer cantidadNinio33) {
		this.cantidadNinioBase33 = cantidadNinio33;
	}

	public Integer getCantidadNinioBase34() {
		return cantidadNinioBase34;
	}

	public void setCantidadNinioBase34(Integer cantidadNinio34) {
		this.cantidadNinioBase34 = cantidadNinio34;
	}

	public void setFechaDeCarga(Date fechaDeCarga) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaDeCarga);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		this.fechaDeCarga = calendar.getTime();
	}

	public Date getFechaDeCarga() {
		return fechaDeCarga;
	}

	public void setCantidadDeTareas(Integer cantidadDeTareas) {

		if (cantidadDeTareas <= 0 || cantidadDeTareas == null) {
			this.cantidadDeTareas = CANTIDAD_INICIAL_DE_TAREAS;
		}

		else {
			this.cantidadDeTareas = cantidadDeTareas;
		}
	}

	public Integer getCantidadDeTareas() {
		return cantidadDeTareas;
	}

	public Double getCostoUnitarioSeleccionado() {
		return costoUnitarioSeleccionado;
	}

	public void setCostoUnitarioSeleccionado(Double costoUnitarioSeleccionado) {
		this.costoUnitarioSeleccionado = costoUnitarioSeleccionado;
	}

	public Double getPrecioDeVentaSeleccionado() {
		return precioDeVentaSeleccionado;
	}

	public void setPrecioDeVentaSeleccionado(Double precioDeVentaSeleccionado) {
		this.precioDeVentaSeleccionado = precioDeVentaSeleccionado;
	}

	private void reset() {
		articuloHome.setCodigoSeleccionado("");
		proveedorHome.setCodigoSeleccionado("");
		colorHome.setCodigoSeleccionado("");
		this.condicionArticulo = "N";
		this.setPrecioDeVentaSeleccionado(0.0);
		this.precioDeVentaSugerido = 0.0;
		this.setFechaDeCarga(new Date());
		this.setCostoUnitarioSeleccionado(0.0);
		this.costoUnitarioSugerido = 0.0;
	}

	@Init
	public void init() {

	}

	@Remove
	@Destroy
	public void destroy() {
	}
}
