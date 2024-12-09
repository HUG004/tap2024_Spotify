package com.example.tap2024.components;

import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.ClienteDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;

public class ReciboPDF {

    public void generarRecibo(ClienteDAO cliente, ObservableList<CancionDAO> cancionesSeleccionadas, String ruta) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            Paragraph titulo = new Paragraph("Recibo de Compra",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Información del Cliente", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            document.add(new Paragraph("Nombre: " + cliente.getNomClt()));
            document.add(new Paragraph("Correo: " + cliente.getEmailClt()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Detalles de la Compra", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);

            tabla.addCell(encabezadoCelda("Canción"));
            tabla.addCell(encabezadoCelda("Costo"));

            float total = 0;
            for (CancionDAO cancion : cancionesSeleccionadas) {
                tabla.addCell(cancion.getNombre());
                tabla.addCell("$" + cancion.getCostoCancion());
                total += cancion.getCostoCancion();
            }

            document.add(tabla);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Fecha de Compra: " + java.time.LocalDateTime.now()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Total: $" + total));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("¡Gracias por tu compra!", FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK)));

            document.close();
            System.out.println("Recibo generado exitosamente en: " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el recibo PDF.");
        }
    }

    private PdfPCell encabezadoCelda(String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
        celda.setBackgroundColor(BaseColor.DARK_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        return celda;
    }
}
