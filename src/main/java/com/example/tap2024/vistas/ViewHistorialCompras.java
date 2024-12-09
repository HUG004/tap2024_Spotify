package com.example.tap2024.vistas;

import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.VentaDAO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewHistorialCompras extends Stage {
    private TableView<CancionDAO> tbvHistorial;
    private VBox vBox;
    private Scene escena;

    public ViewHistorialCompras(int idCliente) {
        CrearUI(idCliente);
        this.setTitle("Historial de Compras");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(int idCliente) {
        tbvHistorial = new TableView<>();
        CrearTable(idCliente);

        vBox = new VBox(10, tbvHistorial);
        escena = new Scene(vBox, 400, 300);
    }

    private void CrearTable(int idCliente) {
        VentaDAO ventaDAO = new VentaDAO();
        ObservableList<CancionDAO> historial = ventaDAO.obtenerHistorialCompras(idCliente);

        if (!historial.isEmpty()) {
            TableColumn<CancionDAO, String> tbcNombre = new TableColumn<>("Nombre");
            tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo Canción");
            tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

            tbvHistorial.getColumns().addAll(tbcNombre, tbcCosto);
            tbvHistorial.setItems(historial);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No hay compras registradas para este cliente.").showAndWait();
        }
    }
}
