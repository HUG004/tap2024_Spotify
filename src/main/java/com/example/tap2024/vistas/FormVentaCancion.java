package com.example.tap2024.vistas;

import com.example.tap2024.models.VentaCancionDAO;
import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.VentaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormVentaCancion extends Stage {
    private Scene scene;
    private ComboBox<VentaDAO> cmbVenta;
    private ComboBox<CancionDAO> cmbCancion;
    private TextField txtDescripcion;
    private Button btnGuardar;
    private VBox vbox;
    private VentaCancionDAO objVentaCancion;
    private TableView<VentaCancionDAO> tbvVentaCancion;

    private boolean esNuevo;

    public FormVentaCancion(TableView<VentaCancionDAO> tbv, VentaCancionDAO objVC) {
        this.tbvVentaCancion = tbv;
        this.objVentaCancion = objVC != null ? objVC : new VentaCancionDAO();
        this.esNuevo = objVC == null;
        CrearUI();

        if (objVC != null) {
            objVentaCancion.setIdVentaOriginal(objVC.getIdVenta());
            objVentaCancion.setIdCancionOriginal(objVC.getIdCancion());

            VentaDAO ventaSeleccionada = buscarVentaPorId(objVC.getIdVenta());
            cmbVenta.getSelectionModel().select(ventaSeleccionada);

            CancionDAO cancionSeleccionada = buscarCancionPorId(objVC.getIdCancion());
            cmbCancion.getSelectionModel().select(cancionSeleccionada);

            txtDescripcion.setText(objVentaCancion.getDescripcion());
            this.setTitle("Editar Relación Venta-Canción");
        } else {
            this.setTitle("Agregar Relación Venta-Canción");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        cmbVenta = new ComboBox<>();
        cmbVenta.setPromptText("Seleccionar Venta");
        cmbVenta.setItems(new VentaDAO().SELECTALL());

        cmbCancion = new ComboBox<>();
        cmbCancion.setPromptText("Seleccionar Canción");
        cmbCancion.setItems(new CancionDAO().SELECTALL());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción de la relación");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarRelacion());

        vbox = new VBox(cmbVenta, cmbCancion, txtDescripcion, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 200);
    }

    private void GuardarRelacion() {
        VentaDAO ventaSeleccionada = cmbVenta.getSelectionModel().getSelectedItem();
        CancionDAO cancionSeleccionada = cmbCancion.getSelectionModel().getSelectedItem();

        if (ventaSeleccionada != null && cancionSeleccionada != null) {
            objVentaCancion.setIdVenta(ventaSeleccionada.getIdVenta());
            objVentaCancion.setIdCancion(cancionSeleccionada.getIdCancion());
            objVentaCancion.setDescripcion(txtDescripcion.getText());

            String mensaje;
            if (esNuevo) {
                if (objVentaCancion.INSERT() > 0) {
                    mensaje = "Relación agregada correctamente";
                } else {
                    mensaje = "Error al agregar la relación";
                }
            } else {
                objVentaCancion.UPDATE();
                mensaje = "Relación actualizada correctamente";
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, mensaje);
            tbvVentaCancion.setItems(objVentaCancion.SELECTALL());
            tbvVentaCancion.refresh();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar una venta y una canción");
        }
    }

    private VentaDAO buscarVentaPorId(int idVenta) {
        for (VentaDAO venta : new VentaDAO().SELECTALL()) {
            if (venta.getIdVenta() == idVenta) {
                return venta;
            }
        }
        return null;
    }

    private CancionDAO buscarCancionPorId(int idCancion) {
        for (CancionDAO cancion : new CancionDAO().SELECTALL()) {
            if (cancion.getIdCancion() == idCancion) {
                return cancion;
            }
        }
        return null;
    }

    private void mostrarAlerta(Alert.AlertType type, String mensaje) {
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}