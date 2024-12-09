package com.example.tap2024.vistas;

import com.example.tap2024.models.ArtistaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormArt extends Stage {

    private Scene scene;
    private TextField txtnomArt;
    private Button btnGuardar;
    private VBox vbox;
    private ArtistaDAO objArt;
    private TableView<ArtistaDAO> tbvArtista;

    public FormArt(TableView<ArtistaDAO> tbv, ArtistaDAO objA) {
        this.tbvArtista = tbv;
        CrearUI();
        if (objA != null) {
            this.objArt = objA;
            txtnomArt.setText(objArt.getNomArt());
            this.setTitle("Editar artista");
        } else {
            this.objArt = new ArtistaDAO();
            this.setTitle("Agregar artista");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        txtnomArt = new TextField();
        txtnomArt.setPromptText("Nombre del artista");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarArtista());

        vbox = new VBox(txtnomArt, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 150);
    }

    private void GuardarArtista() {
        objArt.setNomArt(txtnomArt.getText());
        String msj;
        Alert.AlertType type;

        if (objArt.getIdArt() > 0) {
            objArt.UPDATE();
        } else {
            if (objArt.INSERT() > 0) {
                msj = "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrió un error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }

            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();
        }

        tbvArtista.setItems(objArt.SELECTALL());
        tbvArtista.refresh();
    }
}
