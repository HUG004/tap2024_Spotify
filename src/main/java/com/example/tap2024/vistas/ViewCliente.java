package com.example.tap2024.vistas;

import com.example.tap2024.components.ReciboPDF;
import com.example.tap2024.models.CancionDAO;
import com.example.tap2024.models.ClienteDAO;
import com.example.tap2024.models.VentaCancionDAO;
import com.example.tap2024.models.VentaDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class ViewCliente extends Stage {
    private TableView<CancionDAO> tbvCanciones;
    private VBox mainContainer;
    private HBox buttonContainer;
    private Scene escena;
    private Button btnCerrarSesion, btnVerHistorial, btnVerDatosPersonales, btnAgregarVenta;
    private ClienteDAO clienteActual;

    public ViewCliente(ClienteDAO cliente) {
        this.clienteActual = cliente;
        crearUI();
        this.setTitle("Mi Música - Bienvenido " + cliente.getNomClt());
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        ImageView logo = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        Label lblTitulo = new Label("Tu Biblioteca Musical");
        lblTitulo.getStyleClass().add("title");

        tbvCanciones = new TableView<>();
        crearTabla();

        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.getStyleClass().add("btn-danger");
        btnCerrarSesion.setOnAction(event -> cerrarSesion());

        btnAgregarVenta = new Button("Agregar Compra");
        btnAgregarVenta.getStyleClass().add("btn-primary");
        btnAgregarVenta.setOnAction(event -> mostrarDialogoCompra());

        btnVerHistorial = new Button("Historial de Compras");
        btnVerHistorial.getStyleClass().add("btn-secondary");
        btnVerHistorial.setOnAction(event -> verHistorialCompras());

        btnVerDatosPersonales = new Button("Mis Datos");
        btnVerDatosPersonales.getStyleClass().add("btn-secondary");
        btnVerDatosPersonales.setOnAction(event -> verDatosPersonales());

        buttonContainer = new HBox(10, btnCerrarSesion, btnVerHistorial, btnVerDatosPersonales);
        buttonContainer.setAlignment(Pos.CENTER);

        mainContainer = new VBox(15, logo, lblTitulo, btnAgregarVenta, tbvCanciones, buttonContainer);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getStyleClass().add("background-dark");
        mainContainer.setStyle("-fx-padding: 20;");

        escena = new Scene(mainContainer, 800, 600);
        escena.getStylesheets().add(getClass().getResource("/styles/ViewCliente.css").toExternalForm());
    }

    private void crearTabla() {
        TableColumn<CancionDAO, String> tbcNombre = new TableColumn<>("Canción");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costoCancion"));

        tbvCanciones.getColumns().addAll(tbcNombre, tbcCosto);
        tbvCanciones.setItems(new CancionDAO().SELECTALL());
        tbvCanciones.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void verHistorialCompras() {
        new ViewHistorialCompras(clienteActual.getIdClt());
    }

    private void verDatosPersonales() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Datos Personales");
        alert.setHeaderText(null);
        alert.setContentText("Nombre: " + clienteActual.getNomClt() + "\nCorreo: " + clienteActual.getEmailClt());
        alert.showAndWait();
    }

    private void mostrarDialogoCompra() {
        Stage dialogo = new Stage();
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.setTitle("Selecciona Canciones");

        ListView<CancionDAO> lvCanciones = new ListView<>();
        lvCanciones.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lvCanciones.setItems(new CancionDAO().SELECTALL());

        Button btnConfirmarCompra = new Button("Confirmar");
        btnConfirmarCompra.getStyleClass().add("btn-primary");
        btnConfirmarCompra.setOnAction(event -> {
            ObservableList<CancionDAO> seleccionadas = lvCanciones.getSelectionModel().getSelectedItems();
            if (!seleccionadas.isEmpty()) {
                realizarCompra(seleccionadas);
                dialogo.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecciona al menos una canción.").showAndWait();
            }
        });

        VBox dialogContainer = new VBox(10, new Label("Canciones disponibles:"), lvCanciones, btnConfirmarCompra);
        dialogContainer.setAlignment(Pos.CENTER);
        dialogContainer.setStyle("-fx-padding: 20;");

        dialogo.setScene(new Scene(dialogContainer, 400, 300));
        dialogo.showAndWait();
    }

    private void realizarCompra(ObservableList<CancionDAO> canciones) {
        VentaDAO nuevaVenta = new VentaDAO();
        nuevaVenta.setIdCliente(clienteActual.getIdClt());
        nuevaVenta.setPrecio((float) canciones.stream().mapToDouble(CancionDAO::getCostoCancion).sum());

        if (nuevaVenta.INSERT() > 0) {
            int idVenta = nuevaVenta.getIdVenta();
            for (CancionDAO cancion : canciones) {
                VentaCancionDAO ventaCancion = new VentaCancionDAO();
                ventaCancion.setIdCancion(cancion.getIdCancion());
                ventaCancion.setIdVenta(idVenta);
                ventaCancion.setDescripcion("Compra de canción: " + cancion.getNombre());
                ventaCancion.INSERT();
            }

            generarRecibo(canciones);
        } else {
            new Alert(Alert.AlertType.ERROR, "Error al realizar la compra.").showAndWait();
        }
    }

    private void generarRecibo(ObservableList<CancionDAO> canciones) {
        ReciboPDF reciboPDF = new ReciboPDF();
        String rutaRecibo = "Recibo_" + clienteActual.getNomClt() + "_" + System.currentTimeMillis() + ".pdf";
        try {
            reciboPDF.generarRecibo(clienteActual, canciones, rutaRecibo);
            Desktop.getDesktop().open(new File(rutaRecibo));
            new Alert(Alert.AlertType.INFORMATION, "Compra realizada con éxito. Recibo generado.").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error al generar el recibo PDF.").showAndWait();
        }
    }

    private void cerrarSesion() {
        new Login();
        this.close();
    }
}
