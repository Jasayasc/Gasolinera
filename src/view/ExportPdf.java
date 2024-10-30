package view;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Venta;


public class ExportPdf {
    public void createPdf(ArrayList<Venta> lista){
        int nroFactura = (int)(Math.random()*1000)+1;
        String path = "ventas_"+nroFactura+".pdf";
        try {
            //Creacion del pdf con itext
            PdfWriter pdfWriter = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdfDocument);
            float threeColum = 135f;
            float threeColum150 = threeColum + 150f;
            float threeColum300 = threeColum150 + 150f;
            float columnWidths[] = {threeColum, threeColum300 + 100f, 180f};

            //Creo un objeto para la imagen
            String imgSrc = "gas-pump.png";
            ImageData data = ImageDataFactory.create(imgSrc);
            Image image = new Image(data);

            //Creo una tabla de 3 colunas ya definidas
            Table table = new Table(columnWidths);

            //En la primera columna coloco una im
            table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));

            //Creo una tabla dentro de la columna 2 para colocar varias lineas de texto
            Table tabla2 = new Table(1);

            //Añadimos Celdas a la tabla
            tabla2.addCell(getHeaderTextCell("ESTACION DE SERVICIO", 20));
            tabla2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            tabla2.addCell(getHeaderTextCell("Gasolinera Primax S.A.S", 16));
            tabla2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            tabla2.addCell(getHeaderTextCell("Bogota D.C, Colombia", 16));
            tabla2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            tabla2.addCell(getHeaderTextCell("NIT: 12345-7", 16));

            //Añadimos la tabla en la columna 2 de la tabla principal
            table.addCell(new Cell().add(tabla2).setBorder(Border.NO_BORDER));

            //Creo una tabla dentro de la columna 3 para colocar varias lineas de texto
            Table tabla3 = new Table(1);

            tabla3.addCell(new Cell().add(new Paragraph("\n\n")).setBorder(Border.NO_BORDER));
            tabla3.addCell(getHeaderTextCell("Factura Nro. ", 12));
            tabla3.addCell(getHeaderTextCell(String.valueOf(nroFactura), 12));

            table.addCell(new Cell().add(tabla3).setBorder(Border.NO_BORDER));

            //Se realiza una tabla con el fin de dividir una seccion para darle estilo
            Border border = new SolidBorder(ColorConstants.ORANGE, 2f);
            Table divider = new Table(new float[]{190f * 3});
            divider.setBorder(border);

            //Se realiza una tabla con el fin de dividir una seccion para darle estilo
            Border borderW = new SolidBorder(ColorConstants.WHITE, 2f);
            Table dividerW = new Table(new float[]{190f * 3});
            dividerW.setBorder(borderW);

            //Creamos la tabla de informacion del comprador
            Table info = new Table(new float[]{threeColum150, threeColum300});
            info.addCell(getHeaderTextCell("Fecha: ", 12));
            info.addCell(getInfoTextCell(lista.get(0).getFecha(), 12));
            info.addCell(getHeaderTextCell("Nombre: ", 12));
            info.addCell(getInfoTextCell(lista.get(0).getNombre(), 12));
            info.addCell(getHeaderTextCell("Direccion: ", 12));
            info.addCell(getInfoTextCell(lista.get(0).getDireccion(), 12));

            //Cremos una tabla para la informacion de la venta
            Table cabecera = new Table(new float[]{285f, 285f, 285f});
            cabecera.addCell(getVentaTextCell("Descripcion", 14));
            cabecera.addCell(getVentaTextCell("Cantidad", 14));
            cabecera.addCell(getVentaTextCell("Valor Total", 14));

            //Cremos una tabla para la informacion de la venta
            Table venta = new Table(new float[]{285f, 285f, 285f});
            for (Venta ventas : lista) {
                venta.addCell(getInfoVentaTextCell(ventas.getTipo(), 14));
                venta.addCell(getInfoVentaTextCell(String.valueOf(ventas.getCantidad()), 14));
                venta.addCell(getInfoVentaTextCell(String.valueOf(ventas.getValor()), 14));
            }

            //Añadimos todo lo creado al documento
            document.add(table);
            document.add(divider);
            document.add(new Paragraph("\n"));
            document.add(info);
            document.add(dividerW);
            document.add(cabecera);
            document.add(new Paragraph(""));
            document.add(dividerW);
            document.add(venta);
            document.close();

        } catch (FileNotFoundException | MalformedURLException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("pdf generado");
    }
    
    
   //Metodos que le da estilo a las letras
    static Cell getHeaderTextCell(String string, int size) {
        Text text = new Text(string);
        try {
            text.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        } catch (IOException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Cell().add(new Paragraph(text).setFontSize(size)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER);
    }

    static Cell getVentaTextCell(String string, int size) {
        Text text = new Text(string);
        try {
            text.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_BOLD));
        } catch (IOException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Cell().add(new Paragraph(text).setFontSize(size)).setTextAlignment(TextAlignment.CENTER);
    }

    static Cell getInfoTextCell(String string, int size) {
        Text text = new Text(string);
        try {
            text.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC));
        } catch (IOException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Cell().add(new Paragraph(text).setFontSize(size)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    
    static Cell getInfoVentaTextCell(String string, int size) {
        Text text = new Text(string);
        try {
            text.setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC));
        } catch (IOException ex) {
            Logger.getLogger(ExportPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Cell().add(new Paragraph(text).setFontSize(size)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER);
    }
}
