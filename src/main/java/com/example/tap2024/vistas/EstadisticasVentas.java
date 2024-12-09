package com.example.tap2024.vistas;

import com.example.tap2024.models.VentaDAO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EstadisticasVentas extends Stage {

    private VBox vBox;
    private Scene escena;
    private VentaDAO ventaDAO;

    public EstadisticasVentas() {
        ventaDAO = new VentaDAO();
        CrearUI();
        this.setTitle("Estadísticas de Ventas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vBox = new VBox();

        // Ventas del mes (Texto)
        int ventasMes = ventaDAO.obtenerVentasDelMes();
        Label lblVentasMes = new Label("Ventas realizadas este mes: " + ventasMes);

        // Artistas con más ventas (PieChart)
        PieChart chartArtistas = new PieChart(VentaDAO.obtenerArtistasConMasVentas());
        chartArtistas.setTitle("Artistas con más ventas");

        // Canciones más vendidas (BarChart)
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chartCanciones = new BarChart<>(xAxis, yAxis);
        chartCanciones.setTitle("Canciones más vendidas");
        xAxis.setLabel("Canción");
        yAxis.setLabel("Ventas");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setData(VentaDAO.obtenerCancionesMasVendidas());
        chartCanciones.getData().add(series);

        vBox.getChildren().addAll(lblVentasMes, chartArtistas, chartCanciones);
        escena = new Scene(vBox, 800, 600);
    }
}
