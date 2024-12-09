package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.ArtistaCancionDAO;
import com.example.tap2024.models.ArtistaDAO;
import com.example.tap2024.models.CancionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaArtistaCancion extends Stage {

    private TableView<ArtistaCancionDAO> tbvArtistaCancion;
    private ComboBox<ArtistaDAO> cbArtistas;
    private ComboBox<CancionDAO> cbCanciones;
    private Button btnFiltrarPorArtista, btnFiltrarPorCancion, btnVerTodos, btnAgregarRelacion;
    private VBox vbox;
    private Scene escena;

    private TableColumn<ArtistaCancionDAO, String> tbcEditar;
    private TableColumn<ArtistaCancionDAO, String> tbcEliminar;

    public ListaArtistaCancion() {
        CrearUI();
        this.setTitle("Lista de Canciones por Artista");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        cbArtistas = new ComboBox<>();
        ArtistaDAO objArtista = new ArtistaDAO();
        cbArtistas.setItems(objArtista.SELECTALL());
        cbArtistas.setPromptText("Seleccione un Artista");

        cbCanciones = new ComboBox<>();
        CancionDAO objCancion = new CancionDAO();
        cbCanciones.setItems(objCancion.SELECTALL());
        cbCanciones.setPromptText("Seleccione una Canción");

        btnFiltrarPorArtista = new Button("Filtrar por Artista");
        btnFiltrarPorArtista.getStyleClass().add("button");
        btnFiltrarPorArtista.setOnAction(e -> FiltrarPorArtista());

        btnFiltrarPorCancion = new Button("Filtrar por Canción");
        btnFiltrarPorCancion.getStyleClass().add("button");
        btnFiltrarPorCancion.setOnAction(e -> FiltrarPorCancion());

        btnVerTodos = new Button("Ver Todos");
        btnVerTodos.getStyleClass().add("button");
        btnVerTodos.setOnAction(e -> VerTodos());

        btnAgregarRelacion = new Button("Agregar Relación");
        btnAgregarRelacion.getStyleClass().add("button");
        btnAgregarRelacion.setOnAction(e -> AbrirFormulario());

        CrearTable();

        vbox = new VBox(10, cbArtistas, cbCanciones, btnFiltrarPorArtista, btnFiltrarPorCancion, btnVerTodos, btnAgregarRelacion, tbvArtistaCancion);
        vbox.setPadding(new Insets(10));
        escena = new Scene(vbox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        tbvArtistaCancion = new TableView<>();
        tbvArtistaCancion.getStyleClass().add("table");

        TableColumn<ArtistaCancionDAO, String> colArtista = new TableColumn<>("Artista");
        colArtista.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreArtista()));

        TableColumn<ArtistaCancionDAO, String> colCancion = new TableColumn<>("Canción");
        colCancion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreCancion()));

        TableColumn<ArtistaCancionDAO, String> colInterpretado = new TableColumn<>("Interpretado");
        colInterpretado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getInterpretado()));

        tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                relacion -> new FormArtistaCancion(tbvArtistaCancion, relacion),
                relacion -> {
                    relacion.DELETE();
                    tbvArtistaCancion.setItems(relacion.SELECTALL());
                }
        ));

        tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                relacion -> new FormArtistaCancion(tbvArtistaCancion, relacion),
                relacion -> {
                    relacion.DELETE();
                    tbvArtistaCancion.setItems(relacion.SELECTALL());
                }
        ));

        tbvArtistaCancion.getColumns().addAll(colArtista, colCancion, colInterpretado, tbcEditar, tbcEliminar);
        VerTodos();
    }

    private void AbrirFormulario() {
        FormArtistaCancion form = new FormArtistaCancion(tbvArtistaCancion, null);
        form.show();
    }

    private void FiltrarPorArtista() {
        if (cbArtistas.getValue() != null) {
            ArtistaCancionDAO objRelaciones = new ArtistaCancionDAO();
            ObservableList<ArtistaCancionDAO> listaRelaciones = objRelaciones.Select_By_Artista(cbArtistas.getValue().getIdArt());
            tbvArtistaCancion.setItems(listaRelaciones);
            tbvArtistaCancion.getColumns().removeAll(tbcEditar, tbcEliminar);
        }
    }

    private void FiltrarPorCancion() {
        if (cbCanciones.getValue() != null) {
            ArtistaCancionDAO objRelaciones = new ArtistaCancionDAO();
            ObservableList<ArtistaCancionDAO> listaRelaciones = objRelaciones.Select_By_Cancion(cbCanciones.getValue().getIdCancion());
            tbvArtistaCancion.setItems(listaRelaciones);
            tbvArtistaCancion.getColumns().removeAll(tbcEditar, tbcEliminar);
        }
    }

    private void VerTodos() {
        ArtistaCancionDAO objRelaciones = new ArtistaCancionDAO();
        tbvArtistaCancion.setItems(objRelaciones.SELECTALL());
        if (!tbvArtistaCancion.getColumns().contains(tbcEditar)) {
            tbvArtistaCancion.getColumns().add(tbcEditar);
        }
        if (!tbvArtistaCancion.getColumns().contains(tbcEliminar)) {
            tbvArtistaCancion.getColumns().add(tbcEliminar);
        }
    }
}