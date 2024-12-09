package com.example.tap2024.vistas;

import com.example.tap2024.models.VentaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaVentas extends Stage {

    private TableView<VentaDAO> tbvVentas;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaVentas() {
        CrearUI();
        this.setTitle("Lista de Ventas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnEditarVenta = new Button("Editar Venta");
        btnEditarVenta.setOnAction(actionEvent -> editarVentaSeleccionada());
        tlbMenu.getItems().add(btnEditarVenta);

        tbvVentas = new TableView<>();
        CrearTable();

        vBox = new VBox(tlbMenu, tbvVentas);
        escena = new Scene(vBox, 600, 300);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        VentaDAO objVenta = new VentaDAO();

        TableColumn<VentaDAO, Integer> tbcIdVenta = new TableColumn<>("ID Venta");
        tbcIdVenta.setCellValueFactory(new PropertyValueFactory<>("idVenta"));

        TableColumn<VentaDAO, Float> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<VentaDAO, Integer> tbcIdCliente = new TableColumn<>("ID Cliente");
        tbcIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));

        TableColumn<VentaDAO, String> tbcNombreCliente = new TableColumn<>("Nombre Cliente");
        tbcNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<VentaDAO, String> tbcFechaVenta = new TableColumn<>("Fecha Venta");
        tbcFechaVenta.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));

        tbvVentas.getColumns().addAll(tbcIdVenta, tbcPrecio, tbcIdCliente, tbcNombreCliente, tbcFechaVenta);

        tbvVentas.setItems(objVenta.SELECTALL());
    }

    private void editarVentaSeleccionada() {
        VentaDAO ventaSeleccionada = tbvVentas.getSelectionModel().getSelectedItem();

        if (ventaSeleccionada != null) {
            TextInputDialog dialogo = new TextInputDialog(String.valueOf(ventaSeleccionada.getPrecio()));
            dialogo.setTitle("Editar Venta");
            dialogo.setHeaderText("Editar Precio de la Venta");
            dialogo.setContentText("Nuevo Precio:");

            dialogo.showAndWait().ifPresent(nuevoPrecio -> {
                try {
                    float precio = Float.parseFloat(nuevoPrecio);
                    ventaSeleccionada.setPrecio(precio);
                    ventaSeleccionada.UPDATE();
                    tbvVentas.refresh();
                } catch (NumberFormatException e) {
                    mostrarAlertaError("Precio Inválido", "El precio debe ser un número válido.");
                }
            });
        } else {
            mostrarAlertaError("Advertencia", "Por favor, selecciona una venta para editar.");
        }

    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
