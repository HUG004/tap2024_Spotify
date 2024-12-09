package com.example.tap2024.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loteria extends Stage {

    private Timeline timeline;
    private HBox hboxMain, hboxButtons;
    private VBox vboxTablilla, vboxMazo;
    private Button Anterior, Siguiente, btnIniDet;
    private Scene escena;
    private List<Integer> Mazo, numDisp;
    private ImageView imgMazo;
    private Label Cont, resultado;
    private GridPane gdpTablilla;
    private GridPane[] tablas = new GridPane[5];
    private int ind_Tabla = 0;
    private Button[][][] arBtnTab = new Button[5][4][4];
    private Boolean[][][] Matriz_B = new Boolean[5][4][4];
    private List<Integer> cartasMostradas = new ArrayList<>();
    private int[][][] numerosCartas = new int[5][4][4];

    String path = "C:/Users/Usuario/IdeaProjects/tap2024/src/main/resources/images/";

    public Loteria() {
        numImages();
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView lastImgView = new ImageView(new Image(new File(path + "last.png").toURI().toString()));
        lastImgView.setFitWidth(50);
        lastImgView.setFitHeight(50);

        ImageView nextImgView = new ImageView(new Image(new File(path + "NEXT.png").toURI().toString()));
        nextImgView.setFitWidth(50);
        nextImgView.setFitHeight(50);

        for (int i = 0; i < 5; i++) {
            tablas[i] = new GridPane();
            CrearTablilla(tablas[i], i);
        }

        Cont = new Label("Contador");
        Cont.setFont(new Font("Arial", 30));
        Cont.setTextFill(Color.BLUE);
        imgMazo = new ImageView(new Image(new File(path + "dorso.png").toURI().toString()));
        imgMazo.setFitHeight(500);
        imgMazo.setFitWidth(400);
        btnIniDet = new Button("Iniciar");
        btnIniDet.getStyleClass().add("boton-iniciar");

        vboxMazo = new VBox(Cont, imgMazo);
        vboxMazo.setAlignment(Pos.CENTER);

        Anterior = new Button();
        Anterior.setGraphic(lastImgView);
        Siguiente = new Button();
        Siguiente.setGraphic(nextImgView);
        Siguiente.setOnAction(actionEvent -> nextTabla());
        Anterior.setOnAction(actionEvent -> lastTabla());

        btnIniDet.setOnAction(actionEvent -> alternarEstadoJuego());
        hboxButtons = new HBox(Anterior, Siguiente, btnIniDet);
        hboxButtons.setSpacing(50);

        gdpTablilla = tablas[ind_Tabla];
        vboxTablilla = new VBox(gdpTablilla, hboxButtons);

        hboxMain = new HBox(vboxTablilla, vboxMazo);
        hboxMain.setSpacing(190);
        hboxMain.setPadding(new Insets(20));
        escena = new Scene(hboxMain, 1200, 700);
        escena.getStylesheets().add(new File("C:\\Users\\Usuario\\IdeaProjects\\tap2024\\src\\main\\resources\\styles\\Loteria.CSS").toURI().toString());
    }

    private void CrearTablilla(GridPane listaTabla, int k) {
        List<Integer> cartasTemporales = new ArrayList<>(numDisp);
        Collections.shuffle(cartasTemporales);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Matriz_B[k][i][j] = false;
                int numeroAleatorio = cartasTemporales.remove(0);
                numerosCartas[k][i][j] = numeroAleatorio;

                ImageView imv = new ImageView(new Image(new File(path + "l" + numeroAleatorio + ".png").toURI().toString()));
                imv.setFitWidth(100);
                imv.setFitHeight(130);
                arBtnTab[k][i][j] = new Button();
                arBtnTab[k][i][j].setGraphic(imv);
                final int x = i;
                final int y = j;
                arBtnTab[k][i][j].setOnAction(actionEvent -> Marcar(k, x, y));
                listaTabla.add(arBtnTab[k][i][j], j, i);
            }
        }
    }

    private void Marcar(int tab_Ind, int x, int y) {
        int cartaEnCasilla = numerosCartas[tab_Ind][x][y];

        // Verificar si la carta ya ha salido
        if (cartasMostradas.contains(cartaEnCasilla)) {
            // Marcar la casilla y cambiar su estilo
            arBtnTab[tab_Ind][x][y].setStyle("-fx-background-color: green;");
            Matriz_B[tab_Ind][x][y] = true;

            // Verificar si todas las casillas de la tabla están marcadas
            if (verificarGanador(tab_Ind)) {
                detenerTemporizador();
                mostrarResultado(); // Mostrar mensaje de victoria
            }
        } else {
            // Mostrar alerta de que la carta aún no ha salido
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Movimiento inválido");
            alerta.setHeaderText(null);
            alerta.setContentText("¡La carta de esta casilla aún no ha salido en la baraja!");
            alerta.showAndWait();
        }
    }

    private boolean verificarGanador(int tab_Ind) {
        // Verificar si todas las casillas de la tabla están marcadas
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!Matriz_B[tab_Ind][i][j]) {
                    return false; // Si encuentra una casilla no marcada, no hay ganador
                }
            }
        }
        return true; // Todas las casillas están marcadas
    }


    private void nextTabla() {
        ind_Tabla = (ind_Tabla + 1) % 5;
        gdpTablilla = tablas[ind_Tabla];
        vboxTablilla.getChildren().setAll(gdpTablilla, hboxButtons);
        hboxMain.getChildren().set(0, vboxTablilla);
    }

    private void lastTabla() {
        ind_Tabla = (ind_Tabla == 0) ? 4 : ind_Tabla - 1;
        gdpTablilla = tablas[ind_Tabla];
        vboxTablilla.getChildren().setAll(gdpTablilla, hboxButtons);
        hboxMain.getChildren().set(0, vboxTablilla);
    }

    private void alternarEstadoJuego() {
        switch (btnIniDet.getText()) {
            case "Iniciar":
                iniciarTemporizador();
                break;
            case "Detener":
                detenerTemporizador();
                break;
            case "Resultado":
                mostrarResultado();
                break;
        }
    }

    private void iniciarTemporizador() {
        btnIniDet.setText("Detener");
        cambiarCarta();
        Cont.setText("00:05");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), evento -> actualizarContador()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void detenerTemporizador() {
        btnIniDet.setText("Iniciar");
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void mostrarResultado() {
        resultado = new Label();
        resultado.setId("font-G_P");

        // Verifica si el jugador ha marcado todas las casillas de la tabla actual
        boolean gano = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!Matriz_B[ind_Tabla][i][j]) {  // Si alguna casilla no está marcada, no ha ganado
                    gano = false;
                    break;
                }
            }
            if (!gano) break;
        }

        resultado.setText(gano ? "¡Ganaste!" : "¡Perdiste! Debes prestar más atención.");

        // Muestra una ventana con el resultado
        Stage ventanaResultado = new Stage();
        Scene res = new Scene(resultado);
        ventanaResultado.setTitle("Resultado");
        ventanaResultado.setScene(res);
        ventanaResultado.show();
    }

    private void actualizarContador() {
        if (Mazo.isEmpty()) {
            Cont.setText("00:00");
            // Al final del mazo, muestra el resultado en función de las casillas marcadas
            mostrarResultado();
            timeline.stop();
            btnIniDet.setText("Resultado");
        } else {
            String tiempoActual = Cont.getText();
            switch (tiempoActual) {
                case "00:05":
                    Cont.setText("00:04");
                    break;
                case "00:04":
                    Cont.setText("00:03");
                    break;
                case "00:03":
                    Cont.setText("00:02");
                    break;
                case "00:02":
                    Cont.setText("00:01");
                    break;
                case "00:01":
                    Cont.setText("00:00");
                    cambiarCarta();
                    Cont.setText("00:05"); // Reinicia el contador
                    break;
                default:
                    Cont.setText("00:05");
                    break;
            }
        }
    }

    private void cambiarCarta() {
        if (!Mazo.isEmpty()) {
            int numeroCarta = Mazo.remove(0);
            cartasMostradas.add(numeroCarta);
            imgMazo.setImage(new Image(path + "l" + numeroCarta + ".png"));
        }
    }
    private void numImages() {
        numDisp = new ArrayList<>();
        Mazo = new ArrayList<>();
        for (int i = 1; i <= 54; i++) {
            numDisp.add(i);
            Mazo.add(i);
        }
        Collections.shuffle(Mazo);
    }
}