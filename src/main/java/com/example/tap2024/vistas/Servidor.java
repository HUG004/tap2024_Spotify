package com.example.tap2024.vistas;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Servidor {
    private TableView<TareaImpresion> tablaImpresiones;
    private Button btnAgregarTarea, btnControlSimulador;
    private boolean simuladorEncendido = true;
    private ObservableList<TareaImpresion> listaTareas = FXCollections.observableArrayList();

    public Servidor() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();

        // Crear tabla
        tablaImpresiones = new TableView<>();

        TableColumn<TareaImpresion, String> colNoArchivo = new TableColumn<>("No. Archivo");
        TableColumn<TareaImpresion, String> colNombreArchivo = new TableColumn<>("Nombre de Archivo");
        TableColumn<TareaImpresion, Integer> colNumeroHojas = new TableColumn<>("N. Hojas");
        TableColumn<TareaImpresion, String> colHoraAcceso = new TableColumn<>("Hora de Acceso");
        TableColumn<TareaImpresion, Double> colProgreso = new TableColumn<>("Progreso");

        // Configurar ancho de columnas
        colNoArchivo.setPrefWidth(90);
        colNombreArchivo.setPrefWidth(150);
        colNumeroHojas.setPrefWidth(90);
        colHoraAcceso.setPrefWidth(120);
        colProgreso.setPrefWidth(150);

        // Configurar propiedades
        colNoArchivo.setCellValueFactory(data -> data.getValue().noArchivoProperty());
        colNombreArchivo.setCellValueFactory(data -> data.getValue().nombreArchivoProperty());
        colNumeroHojas.setCellValueFactory(data -> data.getValue().numeroHojasProperty().asObject());
        colHoraAcceso.setCellValueFactory(data -> data.getValue().horaAccesoProperty());
        colProgreso.setCellValueFactory(data -> data.getValue().progresoProperty().asObject());

        // Configurar celdas personalizadas para progreso
        colProgreso.setCellFactory(column -> new TableCell<>() {
            private final ProgressBar progressBar = new ProgressBar(0);

            @Override
            protected void updateItem(Double progress, boolean empty) {
                super.updateItem(progress, empty);
                if (empty || progress == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(progress);
                    setGraphic(progressBar);
                }
            }
        });

        tablaImpresiones.getColumns().addAll(colNoArchivo, colNombreArchivo, colNumeroHojas, colHoraAcceso, colProgreso);
        tablaImpresiones.setItems(listaTareas);

        // Crear botones
        btnAgregarTarea = new Button("Agregar Tarea");
        btnControlSimulador = new Button("Apagar Simulador");

        btnAgregarTarea.setOnAction(event -> agregarTarea());
        btnControlSimulador.setOnAction(event -> controlarSimulador());

        ToolBar toolBar = new ToolBar(btnAgregarTarea, btnControlSimulador);
        root.setTop(toolBar);
        root.setCenter(tablaImpresiones);

        Scene scene = new Scene(root, 700, 400);
        scene.getStylesheets().add(getClass().getResource("/styles/servidor.css").toExternalForm());
        stage.setTitle("Servidor de Impresión");
        stage.setScene(scene);
        stage.show();

        iniciarSimulador();
    }

    private void agregarTarea() {
        String noArchivo = UUID.randomUUID().toString().substring(0, 8);
        String nombreArchivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        int numeroHojas = ThreadLocalRandom.current().nextInt(1, 51);
        String horaAcceso = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        TareaImpresion tarea = new TareaImpresion(noArchivo, nombreArchivo, numeroHojas, horaAcceso);
        listaTareas.add(tarea);
    }

    private void iniciarSimulador() {
        new Thread(() -> {
            while (true) {
                if (simuladorEncendido && !listaTareas.isEmpty()) {
                    TareaImpresion tarea = listaTareas.get(0);
                    int totalHojas = tarea.getNumeroHojas();

                    for (int i = 1; i <= totalHojas; i++) {
                        try {
                            Thread.sleep(100); // simula 100 ms por hoja
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        double progreso = (double) i / totalHojas;
                        Platform.runLater(() -> tarea.setProgreso(progreso));
                    }

                    Platform.runLater(() -> {
                        listaTareas.remove(tarea);
                    });
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void controlarSimulador() {
        simuladorEncendido = !simuladorEncendido;
        btnControlSimulador.setText(simuladorEncendido ? "Apagar Simulador" : "Prender Simulador");
    }
}

