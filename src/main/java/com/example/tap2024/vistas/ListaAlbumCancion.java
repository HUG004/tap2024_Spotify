package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.ArtistaCancionDAO;
import com.example.tap2024.models.CancionAlbumDAO;
import com.example.tap2024.models.AlbumDAO;
import com.example.tap2024.models.CancionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaAlbumCancion extends Stage {
    private TableView<CancionAlbumDAO> tbvCancionAlbum;
    private ComboBox<AlbumDAO> cbAlbum;
    private ComboBox<CancionDAO> cbCancion;
    private Button btnFiltroAlbum, btnFiltroCancion, btnTodos, btnAgregar;
    private VBox vbox;
    private Scene escena;

    private TableColumn<CancionAlbumDAO, String> tbcEditar;
    private TableColumn<CancionAlbumDAO, String> tbcEliminar;

    public ListaAlbumCancion() {
        CrearUI();
        this.setTitle("Lista de Albums por Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        cbAlbum = new ComboBox<>();
        AlbumDAO objAlbum = new AlbumDAO();
        cbAlbum.setItems(objAlbum.SELECTALL());
        cbAlbum.setPromptText("Seleccione un Album");

        cbCancion = new ComboBox<>();
        CancionDAO objCancion = new CancionDAO();
        cbCancion.setItems(objCancion.SELECTALL());
        cbCancion.setPromptText("Seleccione un Cancion");

        btnFiltroAlbum = new Button("Filtrar por Album");
        btnFiltroAlbum.getStyleClass().add("button");
        btnFiltroAlbum.setOnAction(e -> FiltrarPorAlbum());

        btnFiltroCancion = new Button("Filtrar por Cancion");
        btnFiltroCancion.getStyleClass().add("button");
        btnFiltroCancion.setOnAction(e -> FiltrarPorCancion());

        btnTodos = new Button("Ver todos");
        btnTodos.getStyleClass().add("button");
        btnTodos.setOnAction(e -> VerTodos());

        btnAgregar = new Button("Agregar relacion");
        btnAgregar.getStyleClass().add("button");
        btnAgregar.setOnAction(e -> AbrirForm());

        CrearTable();
        vbox = new VBox(10, cbAlbum, cbCancion, btnFiltroAlbum, btnFiltroCancion, btnTodos, btnAgregar, tbvCancionAlbum);
        vbox.setPadding(new Insets(10));
        escena = new Scene(vbox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        tbvCancionAlbum = new TableView<>();
        tbvCancionAlbum.getStyleClass().add("table");

        TableColumn<CancionAlbumDAO, String> colAlbum = new TableColumn<>("Album");
        colAlbum.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreAlbum()));

        TableColumn<CancionAlbumDAO, String> colCancion = new TableColumn<>("Cancion");
        colCancion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreCancion()));

        TableColumn<CancionAlbumDAO, String> colDescrip = new TableColumn<>("Descripcion");
        colDescrip.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));

        tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                relacion -> new FormAlbumCancion(tbvCancionAlbum, relacion),
                relacion -> {
                    relacion.DELETE();
                    tbvCancionAlbum.setItems(relacion.SELECTALL());
                }
        ));

        tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                relacion -> new FormAlbumCancion(tbvCancionAlbum, relacion),
                relacion -> {
                    relacion.DELETE();
                    tbvCancionAlbum.setItems(relacion.SELECTALL());
                }
        ));

        tbvCancionAlbum.getColumns().addAll(colAlbum, colCancion, colDescrip, tbcEditar, tbcEliminar);
        VerTodos();
    }

    private void FiltrarPorAlbum() {
        if (cbAlbum.getValue() != null) {
            CancionAlbumDAO objRelaciones = new CancionAlbumDAO();
            ObservableList<CancionAlbumDAO> RelacionesLista = objRelaciones.SELECT_BY_ALBUM(cbAlbum.getValue().getIdAlbum());
            tbvCancionAlbum.setItems(RelacionesLista);
            tbvCancionAlbum.getColumns().removeAll(tbcEditar, tbcEliminar);
        }
    }

    private void FiltrarPorCancion() {
        if (cbCancion.getValue() != null) {
            CancionAlbumDAO objRelaciones = new CancionAlbumDAO();
            ObservableList<CancionAlbumDAO> RelacionesLista = objRelaciones.SELECT_BY_CANCION(cbCancion.getValue().getIdCancion());
            tbvCancionAlbum.setItems(RelacionesLista);
            tbvCancionAlbum.getColumns().removeAll(tbcEditar, tbcEliminar);
        }
    }

    private void VerTodos() {
        CancionAlbumDAO objRelaciones = new CancionAlbumDAO();
        tbvCancionAlbum.setItems(objRelaciones.SELECTALL());
        if (!tbvCancionAlbum.getColumns().contains(tbcEditar)) {
            tbvCancionAlbum.getColumns().add(tbcEditar);
        }
        if (!tbvCancionAlbum.getColumns().contains(tbcEliminar)) {
            tbvCancionAlbum.getColumns().add(tbcEliminar);
        }
    }

    private void AbrirForm() {
        FormAlbumCancion form = new FormAlbumCancion(tbvCancionAlbum, null);
        form.show();
    }
}