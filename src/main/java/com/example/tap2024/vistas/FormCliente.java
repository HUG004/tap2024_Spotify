package com.example.tap2024.vistas;

import com.example.tap2024.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCliente extends Stage {

    private Scene scene;

    private TextField txtnomClt;
    private TextField txttelClt;
    private TextField txtemailClt;
    private PasswordField txtContraseña;
    private Button btnGuardar;
    private VBox vbox;
    private ClienteDAO objCte;
    private TableView<ClienteDAO> tbvCliente;

    public FormCliente(TableView<ClienteDAO> tbv, ClienteDAO objC) {
        this.tbvCliente = tbv;
        CrearUI();
        if(objC != null) {
            this.objCte = objC;
            txtnomClt.setText(objCte.getNomClt());
            txtemailClt.setText(objCte.getEmailClt());
            txttelClt.setText(objCte.getTelClt());
            this.setTitle("Editar cliente");
        }
        else {
            this.objCte = new ClienteDAO();
            this.setTitle("Agregar cliente");
        }
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        txtnomClt = new TextField();
        txtnomClt.setPromptText("Nombre del cliente");

        txtemailClt = new TextField();
        txtemailClt.setPromptText("Email del cliente");

        txttelClt = new TextField();
        txttelClt.setPromptText("Teléfono del cliente");

        txtContraseña = new PasswordField();
        txtContraseña.setPromptText("Contraseña del cliente");

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(actionEvent -> GuardarCliente());

        vbox = new VBox(txtnomClt, txtemailClt, txttelClt, txtContraseña, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        scene = new Scene(vbox, 300, 200);
    }

    private void GuardarCliente() {
        objCte.setEmailClt(txtemailClt.getText());
        objCte.setNomClt(txtnomClt.getText());
        objCte.setTelClt(txttelClt.getText());
        objCte.setContraseña(txtContraseña.getText());

        String msj;
        Alert.AlertType type;

        if (objCte.getIdClt() > 0) {
            objCte.UPDATE();
            msj = "Cliente actualizado";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objCte.INSERT() > 0) {
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
        tbvCliente.setItems(objCte.SELECTALL());
        tbvCliente.refresh();
    }
}
