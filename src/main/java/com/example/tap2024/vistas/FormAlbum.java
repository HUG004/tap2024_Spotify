package com.example.tap2024.vistas;

import com.example.tap2024.models.AlbumDAO;
import com.example.tap2024.models.ArtistaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FormAlbum extends Stage {

    private Scene scene;
    private TextField txtNombre;
    private TextField txtAño;
    private ComboBox<ArtistaDAO> cmbArtista;
    private Button btnGuardar, btnCargarImagen;
    private VBox vbox;
    private AlbumDAO objAlbum;
    private TableView<AlbumDAO> tbvAlbum;
    private byte[] imagenBytes;

    public FormAlbum(TableView<AlbumDAO> tbv, AlbumDAO objA) {
        this.tbvAlbum = tbv;
        CrearUI();
        if (objA != null) {
            this.objAlbum = objA;
            txtNombre.setText(objAlbum.getNombre());
            txtAño.setText(objAlbum.getAñoSalida());
            for (ArtistaDAO artista : cmbArtista.getItems()) {
                if (artista.getIdArt() == objAlbum.getIdArt()) {
                    cmbArtista.setValue(artista);
                    break;
                }
            }
            this.setTitle("Editar Álbum");
        } else {
            this.objAlbum = new AlbumDAO();
            this.setTitle("Agregar Álbum");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del Álbum");

        txtAño = new TextField();
        txtAño.setPromptText("Año de Salida");

        cmbArtista = new ComboBox<>();
        cmbArtista.setPromptText("Selecciona un Artista");
        cmbArtista.setItems(new ArtistaDAO().SELECTALL());

        btnCargarImagen = new Button("Cargar Imagen");
        btnCargarImagen.setOnAction(e -> cargarImagen());

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarAlbum());

        vbox = new VBox(txtNombre, txtAño, cmbArtista, btnCargarImagen, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 250);
    }

    private void cargarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(this);
        if (file != null) {
            try {
                imagenBytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void GuardarAlbum() {
        objAlbum.setNombre(txtNombre.getText());
        objAlbum.setAñoSalida(txtAño.getText());
        if (cmbArtista.getValue() != null) {
            objAlbum.setIdArt(cmbArtista.getValue().getIdArt());
        }
        objAlbum.setImagen(imagenBytes);

        String msj;
        Alert.AlertType type;

        if (!objAlbum.esNuevo()) {
            objAlbum.UPDATE();
            msj = "Álbum actualizado correctamente";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objAlbum.INSERT() > 0) {
                msj = "Álbum insertado correctamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tbvAlbum.setItems(objAlbum.SELECTALL());
        tbvAlbum.refresh();
    }
}
