package com.example.tap2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class BuscaMinas {
    private int gridSize = 10; // Tamaño de la cuadrícula
    private int[][] grid; // Matriz para las bombas
    private Button[][] btns; // Botones para el campo minado
    private int bombCount; // Número de bombas
    private int celdasDescubiertas; // Contador de celdas descubiertas

    public BuscaMinas() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        TextField txtBombCount = new TextField();
        Button btnCreate = new Button("Crear campo minado");

        btnCreate.setOnAction(actionEvent -> { // Evento de crear campo minado
            String input = txtBombCount.getText().trim(); // Elimina espacios en blanco

            if (input.isEmpty()) {
                showAlert("Error", "Por favor, ingresa un número válido de bombas.");
                return;
            }

            try {
                bombCount = Integer.parseInt(input); // Convertir a entero
            } catch (NumberFormatException e) {
                showAlert("Error", "El valor ingresado no es un número válido.");
                return;
            }

            if (bombCount < 1 || bombCount >= gridSize * gridSize) {
                showAlert("Error", "El número de bombas debe ser mayor a 0 y menor que el total de casillas.");
                return;
            }

            celdasDescubiertas = 0; // Reinicia el contador de celdas descubiertas
            createMineField(bombCount); // Crea el campo minado
            stage.setScene(createGameScene()); // Cambia a la escena del juego
        });

        vbox.getChildren().addAll(txtBombCount, btnCreate);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void createMineField(int bombCount) { // Crear campo de minas
        grid = new int[gridSize][gridSize];
        btns = new Button[gridSize][gridSize];
        Random random = new Random();

        for (int i = 0; i < bombCount; i++) { // Colocar bombas aleatorias
            int fila, columna;
            do {
                fila = random.nextInt(gridSize);
                columna = random.nextInt(gridSize);
            } while (grid[fila][columna] == 1); // Evita colocar más de una bomba en la misma casilla
            grid[fila][columna] = 1; // Indica una bomba
        }
    }

    private Scene createGameScene() {
        GridPane gridPane = new GridPane();

        for (int fila = 0; fila < gridSize; fila++) {
            for (int columna = 0; columna < gridSize; columna++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                btns[fila][columna] = button;

                final int filaActual = fila;
                final int columnaActual = columna;

                // Evento de clic izquierdo para descubrir la casilla
                button.setOnAction(event -> ClickIzquierdo(filaActual, columnaActual));

                // Evento de clic derecho para marcar la casilla
                button.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        ClickDerecho(button);
                    }
                });

                gridPane.add(button, columna, fila); // Añade el botón a la cuadrícula
            }
        }
        return new Scene(gridPane);
    }

    private void ClickIzquierdo(int fila, int columna) {
        if (btns[fila][columna].isDisabled()) return; // Evita redescubrir casillas

        if (grid[fila][columna] == 1) {
            btns[fila][columna].setText("B"); // Muestra bomba
            showAlert("Has perdido ):", "Descubriste una bomba.");
            endgame();
        } else {
            int bombasAdjacentes = countBombasAdjacentes(fila, columna);
            btns[fila][columna].setText(String.valueOf(bombasAdjacentes));
            btns[fila][columna].setDisable(true);
            celdasDescubiertas++;
            checarCondicionVictoria();

            if (bombasAdjacentes == 0) {
                abrirCasillasAdyacentes(fila, columna); // Llama a recursión
            }
        }
    }

    private int countBombasAdjacentes(int fila, int columna) {
        int count = 0;
        // Contar bombas adyacentes en las 8 casillas posibles
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                if (nuevaFila >= 0 && nuevaFila < gridSize &&
                        nuevaColumna >= 0 && nuevaColumna < gridSize &&
                        grid[nuevaFila][nuevaColumna] == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    private void abrirCasillasAdyacentes(int fila, int columna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;

                if (nuevaFila >= 0 && nuevaFila < gridSize &&
                        nuevaColumna >= 0 && nuevaColumna < gridSize) {

                    if (!btns[nuevaFila][nuevaColumna].isDisabled()) {
                        ClickIzquierdo(nuevaFila, nuevaColumna); // Llamada recursiva
                    }
                }
            }
        }
    }

    private void ClickDerecho(Button button) {
        if (button.getStyle().contains("-fx-background-color: yellow;")) {
            button.setText(""); // Desmarca la casilla
            button.setStyle(""); // Limpia el estilo
        } else {
            button.setText("M"); // Marca la casilla
            button.setStyle("-fx-background-color: yellow;"); // Cambia color a amarillo
        }
    }

    private void checarCondicionVictoria() {
        int totalCells = gridSize * gridSize;
        if (celdasDescubiertas == totalCells - bombCount) {
            showAlert("¡Felicidades!", "Has ganado el juego");
            endgame();
        }
    }

    private void showAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void endgame() { // Desactiva todos los botones
        for (int fila = 0; fila < gridSize; fila++) {
            for (int columna = 0; columna < gridSize; columna++) {
                btns[fila][columna].setDisable(true);
            }
        }
    }
}
