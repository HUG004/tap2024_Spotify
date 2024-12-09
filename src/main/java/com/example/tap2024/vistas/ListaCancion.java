package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.ArtistaDAO;
import com.example.tap2024.models.CancionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaCancion extends Stage {
    private TableView<CancionDAO> tbvCanciones;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaCancion() {
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnAddCancion = new Button("Agregar Canción");
        btnAddCancion.getStyleClass().add("button");
        btnAddCancion.setOnAction(actionEvent -> new FormCancion(tbvCanciones, null));
        tlbMenu.getItems().add(btnAddCancion);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvCanciones);
        escena = new Scene(vBox, 600, 300);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.CSS").toExternalForm());
    }

    private void CrearTable() {
        CancionDAO objCan = new CancionDAO();
        tbvCanciones = new TableView<>();

        TableColumn<CancionDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo Canción");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

        TableColumn<CancionDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(cellData -> {
            CancionDAO cancion = cellData.getValue();
            return cancion.getGeneroID() != null ? new SimpleStringProperty(cancion.getGeneroID()) : null;
        });

        TableColumn<CancionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                Cancion -> new FormCancion(tbvCanciones, Cancion),
                Cancion -> {
                    Cancion.DELETE();
                    tbvCanciones.setItems(Cancion.SELECTALL());
                }
        ));

        TableColumn<CancionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                null,
                cancion -> {
                    boolean eliminado = cancion.DELETE();
                    if (eliminado) {
                        tbvCanciones.setItems(cancion.SELECTALL());
                    } else {
                        mostrarError("Error al eliminar", "No se puede eliminar esta canción porque está relacionada con otros datos.");
                    }
                }
        ));

        tbvCanciones.getColumns().addAll(tbcNombre, tbcCosto, tbcGenero, tbcEditar,tbcEliminar);
        tbvCanciones.setItems(objCan.SELECTALL());
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}