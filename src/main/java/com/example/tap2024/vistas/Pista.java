package com.example.tap2024.vistas;

import com.example.tap2024.components.CorredorThread;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Pista extends Stage {

    private GridPane gdpPista;
    private ProgressBar[] pgbCarriles;
    private Button btnIniciar;
    private Scene escena;
    private Label[] lblcorredores;
    private CorredorThread[] thrCorredores;
    private String[] strCorredores = {"Juanpi", "juanpa", "Ian", "Emilio", "Rafa"};
    private VBox vBox;

    public Pista(){
        CrearUI();
        this.setTitle("Pista de Atletismo");
        this.setScene(escena);
        this.show();

    }

    private void CrearUI(){
        lblcorredores= new Label[5];
        pgbCarriles = new ProgressBar[5];
        gdpPista = new GridPane();
        for (int i =0; i< 5; i++){
            lblcorredores[i] = new Label(strCorredores[i]);
            gdpPista.add(lblcorredores[i],0,i);
            pgbCarriles[i] = new ProgressBar(0);
            gdpPista.add(pgbCarriles[i],1,i);

        }
        btnIniciar = new Button("Iniciar Carrera");
        btnIniciar.setOnAction(actionEvent ->IniciarCarrera() );
        vBox = new VBox(gdpPista,btnIniciar);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        escena = new Scene(vBox, 300,200);
    }
    private void IniciarCarrera(){
        thrCorredores = new CorredorThread[5];
        for (int i = 0; i < 5; i++){
            thrCorredores[i] = new CorredorThread(strCorredores[i], pgbCarriles[i]);
            thrCorredores[i].start();
        }
    }
}
