package com.example.tap2024.vistas;

import com.example.tap2024.models.ClienteDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IngresoDatosCliente extends Stage {
    private TextField txtNombre;
    private PasswordField txtContraseña;
    private Button btnContinuar;
    private VBox vbox;
    private Scene escena;

    public IngresoDatosCliente() {
        crearUI();
        this.setTitle("Ingreso de Datos - Cliente");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        ImageView logo = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        Label lblTitulo = new Label("Ingreso de Datos");
        lblTitulo.getStyleClass().add("title");

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.getStyleClass().add("input");

        txtContraseña = new PasswordField();
        txtContraseña.setPromptText("Contraseña");
        txtContraseña.getStyleClass().add("input");

        btnContinuar = new Button("Continuar");
        btnContinuar.getStyleClass().add("btn-primary");
        btnContinuar.setOnAction(event -> abrirInterfazCliente());

        vbox = new VBox(15, logo, lblTitulo, txtNombre, txtContraseña, btnContinuar);
        vbox.setAlignment(Pos.CENTER);
        vbox.getStyleClass().add("background-dark");

        escena = new Scene(vbox, 350, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/IngresoCliente.css").toExternalForm());
    }

    private void abrirInterfazCliente() {
        ClienteDAO cliente = buscarCliente(txtNombre.getText(), txtContraseña.getText());

        if (cliente != null) {
            new ViewCliente(cliente);
            this.close();
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Cliente no encontrado");
            alerta.setHeaderText(null);
            alerta.setContentText("No se encontró el cliente. Intenta de nuevo.");
            alerta.showAndWait();
        }
    }

    private ClienteDAO buscarCliente(String nombre, String contraseña) {
        ClienteDAO clienteDAO = new ClienteDAO();
        ObservableList<ClienteDAO> listaClientes = clienteDAO.SELECTALL();

        for (ClienteDAO cliente : listaClientes) {
            if (cliente.getNomClt().equalsIgnoreCase(nombre) && cliente.getContraseña().equals(contraseña)) {
                return cliente;
            }
        }
        return null;
    }
}
