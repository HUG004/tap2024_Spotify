package com.example.tap2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Stage {
    private Button btnAdmin, btnCliente;
    private Label lblMensaje;
    private VBox vbox;
    private Scene escena;

    public Login() {
        crearUI();
        this.setTitle("Bienvenido - Spotify");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        ImageView logo = new ImageView(new Image(getClass().getResource("/images/logo.png").toExternalForm()));
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        Label lblTitulo = new Label("Spotify");
        lblTitulo.getStyleClass().add("title");

        lblMensaje = new Label("Selecciona tu rol para continuar:");
        lblMensaje.getStyleClass().add("subtitle");

        btnAdmin = new Button("Administrador");
        btnAdmin.setOnAction(event -> abrirInterfazAdmin());
        btnAdmin.getStyleClass().add("btn-primary");

        btnCliente = new Button("Cliente");
        btnCliente.setOnAction(event -> abrirInterfazCliente());
        btnCliente.getStyleClass().add("btn-secondary");

        vbox = new VBox(20, logo, lblTitulo, lblMensaje, btnAdmin, btnCliente);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("background-dark");

        escena = new Scene(vbox, 400, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Login.css").toExternalForm());
    }

    private void abrirInterfazAdmin() {
        new Spotify();
        this.close();
    }

    private void abrirInterfazCliente() {
        new IngresoDatosCliente();
        this.close();
    }
}
