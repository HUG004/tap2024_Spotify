package com.example.tap2024.vistas;

import com.example.tap2024.models.GeneroDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormGenero extends Stage {

    private Scene scene;
    private TextField txtTipoGenero;
    private TextField txtDescripcion;
    private Button btnGuardar;
    private VBox vbox;
    private GeneroDAO objGen;
    private TableView<GeneroDAO> tbvGenero;

    public FormGenero(TableView<GeneroDAO> tbv, GeneroDAO objG) {
        this.tbvGenero = tbv;
        CrearUI();
        if (objG != null) {
            this.objGen = objG;
            txtTipoGenero.setText(objGen.getTipoGenero());
            txtDescripcion.setText(objGen.getDescripcion());
            this.setTitle("Editar Género");
        } else {
            this.objGen = new GeneroDAO();
            this.setTitle("Agregar Género");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        txtTipoGenero = new TextField();
        txtTipoGenero.setPromptText("Tipo de Género");

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripción");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarGenero());

        vbox = new VBox(txtTipoGenero, txtDescripcion, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 150);
    }

    private void GuardarGenero() {
        objGen.setTipoGenero(txtTipoGenero.getText());
        objGen.setDescripcion(txtDescripcion.getText());

        String msj;
        Alert.AlertType type;

        if (!objGen.esNuevo()) {
            objGen.UPDATE();
            msj = "Género actualizado correctamente";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objGen.INSERT() > 0) {
                msj = "Género insertado correctamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrió un error al insertar, intente de nuevo";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        tbvGenero.setItems(objGen.SELECTALL());
        tbvGenero.refresh();
    }
}