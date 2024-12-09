package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.VentaCancionDAO;
import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.VentaDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaVentaCancion extends Stage {
    private TableView<VentaCancionDAO> tbvVentaCancion;
    private ComboBox<VentaDAO> cbVenta;
    private ComboBox<CancionDAO> cbCancion;
    private Button btnGenerarRelacion;
    private VBox vbox;
    private Scene escena;

    private TableColumn<VentaCancionDAO, String> tbcEditar;
    private TableColumn<VentaCancionDAO, String> tbcEliminar;

    public ListaVentaCancion() {
        CrearUI();
        this.setTitle("Lista de Ventas por Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        cbVenta = new ComboBox<>();
        VentaDAO objVenta = new VentaDAO();
        cbVenta.setItems(objVenta.SELECTALL());
        cbVenta.setPromptText("Seleccione una Venta");

        cbCancion = new ComboBox<>();
        CancionDAO objCancion = new CancionDAO();
        cbCancion.setItems(objCancion.SELECTALL());
        cbCancion.setPromptText("Seleccione una Cancion");

        btnGenerarRelacion = new Button("Generar Relación");
        btnGenerarRelacion.setOnAction(e -> AbrirForm());

        CrearTable();
        vbox = new VBox(10, cbVenta, cbCancion, btnGenerarRelacion, tbvVentaCancion);
        vbox.setPadding(new Insets(10));
        escena = new Scene(vbox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());

    }

    private void CrearTable() {
        tbvVentaCancion = new TableView<>();

        TableColumn<VentaCancionDAO, String> colVenta = new TableColumn<>("Venta");
        colVenta.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getIdVenta())));

        TableColumn<VentaCancionDAO, String> colCancion = new TableColumn<>("Cancion");
        colCancion.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getIdCancion())));

        TableColumn<VentaCancionDAO, String> colDescripcion = new TableColumn<>("Descripcion");
        colDescripcion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));

        tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                relacion -> new FormVentaCancion(tbvVentaCancion, relacion),
                relacion -> {
                    relacion.DELETE();
                    tbvVentaCancion.setItems(relacion.SELECTALL());
                }
        ));

        tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                relacion -> new FormVentaCancion(tbvVentaCancion, relacion),
                relacion -> {
                    relacion.DELETE();
                    tbvVentaCancion.setItems(relacion.SELECTALL());
                }
        ));

        tbvVentaCancion.getColumns().addAll(colVenta, colCancion, colDescripcion, tbcEditar, tbcEliminar);
        VerTodos();
    }

    private void VerTodos() {
        VentaCancionDAO objRelaciones = new VentaCancionDAO();
        tbvVentaCancion.setItems(objRelaciones.SELECTALL());
    }

    private void AbrirForm() {
        FormVentaCancion form = new FormVentaCancion(tbvVentaCancion, null);
        form.show();
    }
}