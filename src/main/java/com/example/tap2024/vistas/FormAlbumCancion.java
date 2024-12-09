package com.example.tap2024.vistas;

import com.example.tap2024.models.CancionAlbumDAO;
import com.example.tap2024.models.AlbumDAO;
import com.example.tap2024.models.CancionDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormAlbumCancion extends Stage {
    private Scene scene;
    private ComboBox<AlbumDAO> cmbAlbum;
    private ComboBox<CancionDAO> cmbCancion;
    private TextField txtDescripcion;
    private Button btnGuardar;
    private VBox vbox;
    private CancionAlbumDAO objAlbCan;
    private TableView<CancionAlbumDAO> tbvAlbumCancion;
    private boolean esNuevo;

    public FormAlbumCancion(TableView<CancionAlbumDAO> tbv, CancionAlbumDAO objAC) {
        this.tbvAlbumCancion = tbv;
        this.objAlbCan = objAC != null ? objAC : new CancionAlbumDAO();
        this.esNuevo = objAC == null;
        CrearUI();

        if(objAC != null) {
            objAlbCan.setIdAlbumOriginal(objAC.getIdAlbum());
            objAlbCan.setIdCancion(objAC.getIdCancion());

            AlbumDAO AlbumSeleccionado = objAlbCan.getAlbum();
            cmbAlbum.getSelectionModel().select(AlbumSeleccionado);

            CancionDAO CancionSeleccionado = objAlbCan.getCancion();
            cmbCancion.getSelectionModel().select(CancionSeleccionado);

            txtDescripcion.setText(objAlbCan.getDescripcion());
            this.setTitle("Editar Relacion Album - Cancion");
        }else{
            this.setTitle("Crear Relacion Album - Cancion");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {

        cmbAlbum = new ComboBox<>();
        cmbAlbum.setPromptText("Seleccionar Album");
        cmbAlbum.setItems(new AlbumDAO().SELECTALL());

        cmbCancion = new ComboBox<>();
        cmbCancion.setPromptText("Seleccionar Cancion");
        cmbCancion.setItems(new CancionDAO().SELECTALL());

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripcion");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarRelacion());

        vbox = new VBox(cmbAlbum, cmbCancion, txtDescripcion, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox,300,200);
    }

    private void GuardarRelacion() {

        AlbumDAO albumSeleccionado = cmbAlbum.getSelectionModel().getSelectedItem();
        CancionDAO cancionSeleccionado = cmbCancion.getSelectionModel().getSelectedItem();

        if(albumSeleccionado != null && cancionSeleccionado != null) {
            objAlbCan.setIdAlbum(albumSeleccionado.getIdAlbum());
            objAlbCan.setIdCancion(cancionSeleccionado.getIdCancion());
            objAlbCan.setDescripcion(txtDescripcion.getText());

            String mensaje;
            if(esNuevo){
                if(objAlbCan.INSERT() > 0){
                    mensaje = "Relacion agregada correctamente.";
                } else{
                    mensaje = "Error al agregar la relación.";
                }
            }else{
                objAlbCan.UPDATE();
                mensaje = "Relacion actualizada correctamente.";
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, mensaje);
            tbvAlbumCancion.setItems(objAlbCan.SELECTALL());
            tbvAlbumCancion.refresh();
        }else{
            mostrarAlerta(Alert.AlertType.INFORMATION, "Debe seleccionar un Album y una Canción");
        }
    }

    private void mostrarAlerta(Alert.AlertType type, String mensaje) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensaje del sistema");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
