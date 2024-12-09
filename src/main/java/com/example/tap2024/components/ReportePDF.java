package com.example.tap2024.components;

import com.example.tap2024.models.ArtistaDAO;
import com.example.tap2024.models.CancionDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ReportePDF {

    public void generarReporte() {
        Document document = new Document();

        try {
            String ruta = "C:\\Users\\Usuario\\Documents\\5to semestre\\Topicos Avanzados de Programacion\\tap2024-main\\PDF´s\\Reporte_Artistas_Canciones.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            Paragraph titulo = new Paragraph("Reporte Completo de Artistas y Canciones",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Artistas", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            PdfPTable tablaArtistas = new PdfPTable(1);
            tablaArtistas.setWidthPercentage(100);

            tablaArtistas.addCell(Encabezado("Nombre del Artista"));

            List<ArtistaDAO> listaArtistas = new ArtistaDAO().SELECTALL();
            for (ArtistaDAO artista : listaArtistas) {
                tablaArtistas.addCell(artista.getNomArt());
            }

            document.add(tablaArtistas);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Canciones", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            PdfPTable tablaCanciones = new PdfPTable(2);
            tablaCanciones.setWidthPercentage(100);

            tablaCanciones.addCell(Encabezado("Nombre de la Canción"));
            tablaCanciones.addCell(Encabezado("Costo"));

            List<CancionDAO> listaCanciones = new CancionDAO().SELECTALL();
            for (CancionDAO cancion : listaCanciones) {
                tablaCanciones.addCell(cancion.getNombre());
                tablaCanciones.addCell(String.valueOf(cancion.getCostoCancion()));
            }

            document.add(tablaCanciones);

            document.close();
            System.out.println("Reporte generado exitosamente en: " + ruta);

            File archivoReporte = new File(ruta);
            if (archivoReporte.exists()) {
                Desktop.getDesktop().open(archivoReporte);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PdfPCell Encabezado(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
        celda.setBackgroundColor(BaseColor.DARK_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(15);
        return celda;
    }
}
