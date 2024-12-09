package com.example.tap2024.vistas;

import com.example.tap2024.components.ReportePDF;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Spotify extends Stage {

    private Scene escena;
    private VBox vbox;
    private HBox hbox;
    private Label lbl_spoti;
    private GridPane grid;
    private Button btn_Clt, btn_Art, btn_Alb, btn_Ven,btn_Gen, btn_Can,btn_AxC,btnAlb_C,btnV_C, btnEstadisticas, btnRepTotal;
    private Button btnCerrarSesion;

    public Spotify() {
        CrearUI();
        this.setTitle("Spotify");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        ImageView logo = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logo.setFitWidth(100);
        logo.setFitHeight(100);
        logo.setPreserveRatio(true);

        HBox logoContainer = new HBox(logo);
        logoContainer.setAlignment(Pos.TOP_LEFT);
        logoContainer.setPadding(new Insets(10));

        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setOnAction(event -> cerrarSesion());
        btnCerrarSesion.getStyleClass().add("btn-danger");

        btn_Clt = new Button("Cliente");
        btn_Clt.setOnAction(actionEvent -> new ListaClientes());
        btn_Clt.getStyleClass().add("btn-primary");

        btn_Art = new Button("Artista");
        btn_Art.setOnAction(actionEvent -> new ListaArt());
        btn_Art.getStyleClass().add("btn-primary");

        btn_Ven = new Button("Ventas");
        btn_Ven.setOnAction(actionEvent -> new ListaVentas());
        btn_Ven.getStyleClass().add("btn-primary");

        btn_Gen = new Button("Género");
        btn_Gen.setOnAction(actionEvent -> new ListaGenero());
        btn_Gen.getStyleClass().add("btn-primary");

        btn_Alb = new Button("Álbum");
        btn_Alb.setOnAction(actionEvent -> new ListaAlbum());
        btn_Alb.getStyleClass().add("btn-primary");

        btn_Can = new Button("Canción");
        btn_Can.setOnAction(actionEvent -> new ListaCancion());
        btn_Can.getStyleClass().add("btn-primary");

        btn_AxC = new Button("Relación Artista - Canción");
        btn_AxC.setOnAction(actionEvent -> new ListaArtistaCancion());
        btn_AxC.getStyleClass().add("btn-secondary");

        btnAlb_C = new Button("Relación Álbum - Canción");
        btnAlb_C.setOnAction(actionEvent -> new ListaAlbumCancion());
        btnAlb_C.getStyleClass().add("btn-secondary");

        btnV_C = new Button("Relación Venta - Canción");
        btnV_C.setOnAction(actionEvent -> new ListaVentaCancion());
        btnV_C.getStyleClass().add("btn-secondary");

        btnEstadisticas = new Button("Estadísticas");
        btnEstadisticas.setOnAction(actionEvent -> new EstadisticasVentas());
        btnEstadisticas.getStyleClass().add("btn-success");

        btnRepTotal = new Button("Reporte total de artistas y canciones");
        btnRepTotal.setOnAction(actionEvent -> {
            ReportePDF reportePDF = new ReportePDF();
            reportePDF.generarReporte();
        });
        btnRepTotal.getStyleClass().add("btn-success");

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.setAlignment(Pos.CENTER);

        grid.add(btn_Clt, 0, 0);
        grid.add(btn_Art, 0, 1);
        grid.add(btn_Ven, 0, 2);
        grid.add(btn_Gen, 1, 0);
        grid.add(btn_Alb, 1, 1);
        grid.add(btn_Can, 1, 2);
        grid.add(btn_AxC, 2, 0);
        grid.add(btnAlb_C, 2, 1);
        grid.add(btnV_C, 2, 2);

        lbl_spoti = new Label("Administrador");
        lbl_spoti.getStyleClass().add("title");

        HBox header = new HBox(10, logoContainer, lbl_spoti);
        header.setAlignment(Pos.CENTER_LEFT);

        hbox = new HBox(20);
        hbox.getChildren().addAll(grid, btnEstadisticas, btnRepTotal);
        hbox.setAlignment(Pos.CENTER);

        vbox = new VBox(20);
        vbox.getChildren().addAll(header, hbox, btnCerrarSesion);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getStyleClass().add("background-black");

        escena = new Scene(vbox, 1150, 400);
        escena.getStylesheets().add(getClass().getResource("/styles/Listas.css").toExternalForm());
    }


        private void cerrarSesion() {
        new Login();
        this.close();
    }
}