package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.GeneroDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaGenero extends Stage {
    private TableView<GeneroDAO> tbvGeneros;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaGenero() {
        CrearUI();
        this.setTitle("Lista de Géneros");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnAddGenero = new Button("Agregar Género");
        btnAddGenero.setOnAction(actionEvent -> new FormGenero(tbvGeneros, null));
        tlbMenu.getItems().add(btnAddGenero);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvGeneros);
        escena = new Scene(vBox, 500, 250);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        GeneroDAO objGen = new GeneroDAO();
        tbvGeneros = new TableView<>();

        TableColumn<GeneroDAO, String> tbcTipo = new TableColumn<>("Tipo de Género");
        tbcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoGenero"));

        TableColumn<GeneroDAO, String> tbcDescripcion = new TableColumn<>("Descripción");
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<GeneroDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                genero -> new FormGenero(tbvGeneros, genero),
                null
        ));

        TableColumn<GeneroDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                null,
                genero -> {
                    genero.DELETE();
                    tbvGeneros.setItems(genero.SELECTALL());
                }
        ));

        tbvGeneros.getColumns().addAll(tbcTipo, tbcDescripcion, tbcEditar, tbcEliminar);
        tbvGeneros.setItems(objGen.SELECTALL());
    }

}