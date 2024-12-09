package com.example.tap2024.vistas;

import com.example.tap2024.models.ArtistaCancionDAO;
import com.example.tap2024.models.ArtistaDAO;
import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormArtistaCancion extends Stage {
    private Scene scene;
    private ComboBox<ArtistaDAO> cmbArtista;
    private ComboBox<CancionDAO> cmbCancion;
    private TextField txtInterpretado;
    private Button btnGuardar;
    private VBox vbox;
    private ArtistaCancionDAO objArtCan;
    private TableView <ArtistaCancionDAO> tbvArtistaCancion;


    private boolean esNuevo;

    public FormArtistaCancion(TableView<ArtistaCancionDAO> tbv , ArtistaCancionDAO objAC){
        this.tbvArtistaCancion = tbv;
        this.objArtCan = objAC != null ? objAC : new ArtistaCancionDAO();
        this.esNuevo = objAC == null;
        CrearUI();

        if(objAC != null){

            objArtCan.setIdArtistaOriginal(objAC.getIdArtista());
            objArtCan.setIdCancionOriginal(objAC.getIdCancion());

            ArtistaDAO artistaSeleccionado = objArtCan.getArtista();
            cmbArtista.getSelectionModel().select(artistaSeleccionado);

            CancionDAO cancionSeleccionada = objArtCan.getCancion();
            cmbCancion.getSelectionModel().select(cancionSeleccionada);

            txtInterpretado.setText(objArtCan.getInterpretado());
            this.setTitle("Editar Relacion Artista-Cancion");
        } else {
            this.setTitle("Agregar Relacion Artista-Cancion");
        }
        this.setScene(scene);
        this.show();
    }


    private void CrearUI() {
        cmbArtista = new ComboBox<>();
        cmbArtista.setPromptText("Seleccionar Artista");
        cmbArtista.setItems(new ArtistaDAO().SELECTALL());

        cmbCancion = new ComboBox<>();
        cmbCancion.setPromptText("Seleccionar Canción");
        cmbCancion.setItems(new CancionDAO().SELECTALL());

        txtInterpretado = new TextField();
        txtInterpretado.setPromptText("Descripción de la interpretación");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarRelacion());

        vbox = new VBox(cmbArtista, cmbCancion, txtInterpretado, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 200);
    }

    private void GuardarRelacion() {
        ArtistaDAO artistaSeleccionado = cmbArtista.getSelectionModel().getSelectedItem();
        CancionDAO cancionSeleccionada = cmbCancion.getSelectionModel().getSelectedItem();

        if (artistaSeleccionado != null && cancionSeleccionada != null) {
            objArtCan.setIdArtista(artistaSeleccionado.getIdArt());
            objArtCan.setIdCancion(cancionSeleccionada.getIdCancion());
            objArtCan.setInterpretado(txtInterpretado.getText());

            String mensaje;
            if (esNuevo) {
                if (objArtCan.INSERT() > 0) {
                    mensaje = "Relación agregada correctamente";
                } else {
                    mensaje = "Error al agregar la relación";
                }
            } else {
                objArtCan.UPDATE();
                mensaje = "Relación actualizada correctamente";
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, mensaje);
            tbvArtistaCancion.setItems(objArtCan.SELECTALL());
            tbvArtistaCancion.refresh();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Debe seleccionar un artista y una canción");
        }
    }

    private void mostrarAlerta(Alert.AlertType type, String mensaje) {
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}