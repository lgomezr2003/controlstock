package ar.com.pitasi.zapateria.controlstock.acciones;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Init;
import javax.ejb.Remove;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import ar.com.pitasi.zapateria.controlstock.modelo.Compra;
import ar.com.pitasi.zapateria.controlstock.modelo.RelacionCompraCodigoUnico;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.CompraHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.CompraList;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.RelacionCompraCodigoUnicoHome;
import ar.com.pitasi.zapateria.controlstock.modelo.acceso.RelacionCompraCodigoUnicoList;
import ar.com.pitasi.zapateria.controlstock.util.FormateadorDeCodigos;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Name("generadorDeEtiquetas")
@Scope(ScopeType.CONVERSATION)
public class GeneradorDeEtiquetasBean implements GeneradorDeEtiquetas {

	private HttpServletResponse response;
	private List<Compra> comprasSeleccionadas = new ArrayList<Compra>();
	private Boolean seleccionandoCompras;
	private List<RelacionCompraCodigoUnico> codigosGenerados = new ArrayList<RelacionCompraCodigoUnico>();

	@In(create = true)
	RelacionCompraCodigoUnicoHome relacionCompraCodigoUnicoHome;

	@In(create = true)
	CompraHome compraHome;
	@In(create = true)
	CompraList compraList;
	@In(create = true)
	RelacionCompraCodigoUnicoList relacionCompraCodigoUnicoList;
	@In(create=true)
	FormateadorDeCodigos formateadorDeCodigos;

	@In
	FacesMessages facesMessages;

	@Begin(join = true)
	public String comenzarSeleccion() {

		this.comprasSeleccionadas.clear();
		setSeleccionandoCompras(true);
		return String.valueOf(ResultadoNavegacion.LISTA_COMPRAS);

	}

	public String seleccionarCompraParaImpresion(Compra compra) {
		
		comprasSeleccionadas.add(compra);
		return String.valueOf(ResultadoNavegacion.LISTA_COMPRAS);
	}

	public String deseleccionarCompraParaImpresion(Compra compra) {
		
		comprasSeleccionadas.remove(compra);
		return String.valueOf(ResultadoNavegacion.LISTA_COMPRAS);
	}

	public void setSeleccionandoCompras(Boolean seleccionandoCompras) {
		this.seleccionandoCompras = seleccionandoCompras;
	}

	public Boolean getSeleccionandoCompras() {
		return seleccionandoCompras;
	}

	@End
	public String generarEtiquetas() {

		for (Compra compra : comprasSeleccionadas) {

			if (!relacionCompraCodigoUnicoHome
					.existeCodigoUnicoParaCompra(compra)) {

				StringBuilder constructorDeCodigo = new StringBuilder();

				constructorDeCodigo.append(compra.getArticulo().getProveedor().getCodigoFabricante());
				constructorDeCodigo.append(formateadorDeCodigos.formatearCodigo(compra.getArticulo().getCodigoArticulo(),5));
				constructorDeCodigo.append(compra.getNumero());
				constructorDeCodigo.append(formateadorDeCodigos.formatearCodigo(String.valueOf(compra.getColor().getCodigoColor()),2));
				constructorDeCodigo.append(formateadorDeCodigos.formatearCodigo(String.valueOf(compra.getCodigoCompra()),3));

				String codigoUnico = constructorDeCodigo.toString();

				RelacionCompraCodigoUnico relacionCompraCodigoUnico = new RelacionCompraCodigoUnico();
				relacionCompraCodigoUnico.setCompra(compra);
				relacionCompraCodigoUnico.setCodigoUnico(codigoUnico);

				codigosGenerados.add(relacionCompraCodigoUnico);

				relacionCompraCodigoUnicoHome
						.setInstance(relacionCompraCodigoUnico);
				relacionCompraCodigoUnicoHome.persist();

			}

			else {
				codigosGenerados.add(relacionCompraCodigoUnicoHome
						.obtenerRelacionCodigoUnicoParaCompra(compra));
			}
		}

		if (comprasSeleccionadas.isEmpty()) {
			facesMessages
					.add("No se selecciono ninguna compra. Vuelva a intentarlo.");
		} else {

			try {
//				this.generarPDFConCodigosDeBarra(codigosGenerados);
				this.generarPDFConEtiquetas(codigosGenerados);
				
				facesMessages.add("Etiquetas generadas correctamente");

			} catch (IOException e) {
				facesMessages
						.add("Ocurrio un error generando el PDF. Intentelo nuevamente.");
				e.printStackTrace();
			}
		}

		// TODO: Pasar parametro al listado de codigos para que muestre solo los
		// generados
		comprasSeleccionadas.clear();
		return String.valueOf(ResultadoNavegacion.LISTA_ETIQUETAS);
	}

	private void generarPDFConCodigosDeBarra(
			List<RelacionCompraCodigoUnico> codigosGenerados)
			throws IOException {

		response = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();

		File temp = File.createTempFile("codigos", ".tmp");
		temp.deleteOnExit();

		Document documentoPDF = new Document();

		try {

			final int NUMERO_DE_COLUMNAS = 3;
			PdfWriter writer = PdfWriter.getInstance(documentoPDF,
					new FileOutputStream(temp));
			documentoPDF.open();
			PdfContentByte cb = writer.getDirectContent();

			PdfPTable paginaPDF = new PdfPTable(NUMERO_DE_COLUMNAS); // tres
																		// columnas

			paginaPDF.getDefaultCell().setPadding(0f); // sin espacios
			paginaPDF.getDefaultCell().setBorder(Rectangle.NO_BORDER);// sin
																		// bordes
			paginaPDF.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_LEFT);// alineado
			// de
			// izquierda
			// a
			// derecha
			paginaPDF.setWidthPercentage(110f);

			/*
			 * Ciclo para generar cada codigo de barras
			 */
			int numeroDeCodigosDeBarra = comprasSeleccionadas.size();
			int indiceCodigo = 0;

			for (int i = 0; i < numeroDeCodigosDeBarra; i++) {

				PdfPTable tablaPDF = new PdfPTable(1);// hacemos una tabla para
														// el
				// codigo que haremos
				tablaPDF.getDefaultCell().setBorder(Rectangle.NO_BORDER);// sin
				// borde
				tablaPDF.getDefaultCell().setPadding(40f); // medidas
															// solicitadas

				Barcode128 codigoDeBarras = new Barcode128();// usamos la clase
				// Barcode128 de
				// iText para
				// generar la imagen
				codigoDeBarras.setX(1f);// puedes modificar estas medidas para
										// que
				// veas como queda tu codigo de barras
				// (mas grande, mas ancho, etcetera)
				codigoDeBarras.setN(0.5f);
				codigoDeBarras.setChecksumText(true);
				codigoDeBarras.setGenerateChecksum(true);
				codigoDeBarras.setSize(5f);
				codigoDeBarras.setTextAlignment(Element.ALIGN_CENTER);// alineado
				// al centro
				codigoDeBarras.setBaseline(9f);

				// este es el valor quer tendra el codigo de barra
				codigoDeBarras.setCode(codigosGenerados.get(indiceCodigo)
						.getCodigoUnico());

				indiceCodigo++;

				codigoDeBarras.setBarHeight(40f);// altura del codigo de barras

				Image imagenCodigoDeBarras = codigoDeBarras
						.createImageWithBarcode(cb, Color.black, Color.BLACK);// convertimos
																				// este
																				// codigo
																				// en
				// una imagen
				Chunk cbc = new Chunk(imagenCodigoDeBarras, 0, 0);// la imagen
																	// del
				// codigo de barras
				// la ponemos en un
				// chunk

				Phrase p = new Phrase(cbc);// este chunk lo ponemos en un
				// phrase.

				PdfPCell celda = new PdfPCell(p); // creamos una celda que
													// contenga
				// la frase P

				celda.setPaddingTop(23f); // medidas necesarias
				celda.setPaddingBottom(3f);
				celda.setPaddingLeft(0f);
				celda.setPaddingRight(5f);
				celda.setBorder(Rectangle.NO_BORDER);
				celda.setVerticalAlignment(Element.ALIGN_TOP);
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);

				tablaPDF.addCell(celda);// agregamos la celda a la tabla
				paginaPDF.addCell(tablaPDF); // la tabla a la tabla principal

				if (i == numeroDeCodigosDeBarra - 1) {
					paginaPDF.completeRow();
				}

			}

			documentoPDF.add(paginaPDF);
			documentoPDF.close();

			response.setContentType("application/pdf");
			OutputStream out = response.getOutputStream();

			FileInputStream FOS = new FileInputStream(temp);

			while (FOS.available() > 0) {
				out.write(FOS.read());
			}

			out.flush();
			out.close();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(ex.getMessage());
			out.close();
		} catch (DocumentException ex) {
			ex.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(ex.getMessage());
			out.close();
		}

	}

	private void generarPDFConEtiquetas(
			List<RelacionCompraCodigoUnico> codigosGenerados)
			throws IOException {

		response = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();

		File temp = File.createTempFile("codigos", ".tmp");
		temp.deleteOnExit();

		Document documentoPDF = new Document();

		try {

			final int NUMERO_DE_COLUMNAS = 3;
			PdfWriter writer = PdfWriter.getInstance(documentoPDF,
					new FileOutputStream(temp));
			documentoPDF.open();

			PdfPTable paginaPDF = new PdfPTable(NUMERO_DE_COLUMNAS); // tres
																		// columnas

			paginaPDF.getDefaultCell().setPadding(0f); // sin espacios
			paginaPDF.getDefaultCell().setBorder(Rectangle.RECTANGLE);
			paginaPDF.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_LEFT);// alineado
			// de
			// izquierda
			// a
			// derecha
			paginaPDF.setWidthPercentage(110f);

			/*
			 * Ciclo para generar cada codigo de barras
			 */
			int numeroDeCodigosDeBarra = comprasSeleccionadas.size();
			int indiceCodigo = 0;

			for (int i = 0; i < numeroDeCodigosDeBarra; i++) {

				PdfPTable tablaPDF = new PdfPTable(1);// hacemos una tabla para
														// el
				// codigo que haremos
				tablaPDF.getDefaultCell().setBorder(Rectangle.RECTANGLE);
				tablaPDF.getDefaultCell().setPadding(60f); // medidas solicitadas
				
				Chunk cbc = new Chunk(codigosGenerados.get(indiceCodigo).getCodigoUnico());
				
				indiceCodigo++;
				Phrase p = new Phrase(cbc);// este chunk lo ponemos en un phrase.
				PdfPCell celda = new PdfPCell(p); // creamos una celda que contenga la frase P

				celda.setPaddingTop(22f); // medidas necesarias
				celda.setPaddingBottom(22f);
				celda.setPaddingLeft(0f);
				celda.setPaddingRight(5f);
				celda.setBorder(Rectangle.RECTANGLE);
				celda.setVerticalAlignment(Element.ALIGN_CENTER);
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);

				tablaPDF.addCell(celda);// agregamos la celda a la tabla
				paginaPDF.addCell(tablaPDF); // la tabla a la tabla principal

				if (i == numeroDeCodigosDeBarra - 1) {
					paginaPDF.completeRow();
				}

			}

			documentoPDF.add(paginaPDF);
			documentoPDF.close();

			response.setContentType("application/pdf");
			OutputStream out = response.getOutputStream();

			FileInputStream FOS = new FileInputStream(temp);

			while (FOS.available() > 0) {
				out.write(FOS.read());
			}

			out.flush();
			out.close();

			FacesContext.getCurrentInstance().responseComplete();

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(ex.getMessage());
			out.close();
		} catch (DocumentException ex) {
			ex.printStackTrace();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println(ex.getMessage());
			out.close();
		}

	}

	@Remove
	@Destroy
	public void destroy() {
	}

	@Init
	public void init() {
	}

	public Boolean estaSeleccionada(Compra compra) {
		return comprasSeleccionadas.contains(compra);
	}

	public String seleccionarTodo(List<Compra> compras) {

		comprasSeleccionadas.addAll(compras);
		return String.valueOf(ResultadoNavegacion.LISTA_COMPRAS);

	}

}
