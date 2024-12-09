package com.example.tap2024.vistas;

import com.example.tap2024.components.ButtonCell;
import com.example.tap2024.models.AlbumDAO;
import com.example.tap2024.models.ArtistaDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;

public class ListaAlbum extends Stage {
    private TableView<AlbumDAO> tbvAlbum;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaAlbum() {
        CrearUI();
        this.setTitle("Lista de Álbumes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        Button btnAddAlbum = new Button("Agregar Álbum");
        btnAddAlbum.setOnAction(actionEvent -> new FormAlbum(tbvAlbum, null));
        tlbMenu.getItems().add(btnAddAlbum);

        CrearTable();
        vBox = new VBox(tlbMenu, tbvAlbum);
        escena = new Scene(vBox, 600, 400);
    }

    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();
        tbvAlbum = new TableView<>();

        TableColumn<AlbumDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<AlbumDAO, String> tbcAño = new TableColumn<>("Año de Salida");
        tbcAño.setCellValueFactory(new PropertyValueFactory<>("añoSalida"));

        TableColumn<AlbumDAO, String> tbcArtista = new TableColumn<>("Artista");
        tbcArtista.setCellValueFactory(c -> {
            ArtistaDAO artista = new ArtistaDAO();
            artista.setIdArt(c.getValue().getIdArt());
            artista = artista.SELECTBYID();
            return new SimpleStringProperty(artista.getNomArt());
        });

        TableColumn<AlbumDAO, ImageView> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellValueFactory(c -> {
            byte[] imgBytes = c.getValue().getImagen();
            if (imgBytes != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);
                ImageView imgView = new ImageView(new Image(bis));
                imgView.setFitWidth(50);
                imgView.setFitHeight(50);
                return new SimpleObjectProperty<>(imgView);
            } else {
                return new SimpleObjectProperty<>(null);
            }
        });

        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(col -> new ButtonCell<>(
                "Editar",
                album -> new FormAlbum(tbvAlbum, album),
                album -> {
                    album.DELETE();
                    tbvAlbum.setItems(album.SELECTALL());
                }
        ));

        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new ButtonCell<>(
                "Eliminar",
                null,
                album -> {
                    boolean eliminado = album.DELETE();
                    if (eliminado) {
                        tbvAlbum.setItems(album.SELECTALL());
                    } else {
                        mostrarError("Error al eliminar", "No se puede eliminar este album porque está relacionado con otros datos.");
                    }
                }
        ));


        tbvAlbum.getColumns().addAll(tbcNombre, tbcAño, tbcArtista, tbcImagen, tbcEditar, tbcEliminar);
        tbvAlbum.setItems(objAlbum.SELECTALL());
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

