package com.example.tap2024.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

public class ArtistaCancionDAO {
    private int idArtista;
    private int idCancion;
    private String nombreArtista;
    private String nombreCancion;
    private String interpretado;
    private int idArtistaOriginal;
    private int idCancionOriginal;

    public void setIdArtistaOriginal(int idArtista) {
        this.idArtistaOriginal = idArtista;
    }

    public void setIdCancionOriginal(int idCancion) {
        this.idCancionOriginal = idCancion;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getInterpretado() {
        return interpretado;
    }

    public void setInterpretado(String interpretado) {
        this.interpretado = interpretado;
    }

    public int INSERT() {
        String query = "INSERT INTO Cancion_Artista(idartista, idcancion, interpretado) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(query)) {
            stmt.setInt(1, this.idArtista);
            stmt.setInt(2, this.idCancion);
            stmt.setString(3, this.interpretado);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void UPDATE() {
        String query = "UPDATE Cancion_Artista SET idartista = " + this.idArtista +
                ", idcancion = " + this.idCancion +
                ", interpretado = '" + this.interpretado + "' " +
                "WHERE idartista = " + this.idArtistaOriginal +
                " AND idcancion = " + this.idCancionOriginal;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void DELETE() {
        String query = "DELETE FROM Cancion_Artista WHERE idartista = " + this.idArtista + " AND idcancion = " + this.idCancion;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<ArtistaCancionDAO> SELECTALL() {
        ObservableList<ArtistaCancionDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT ac.idArtista, a.nomArt, ac.idCancion, c.nomCancion, ac.interpretado " +
                "FROM Cancion_Artista ac " +
                "JOIN tblArtista a ON ac.idArtista = a.idArt " +
                "JOIN tblCancion c ON ac.idCancion = c.id_Cancion";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ArtistaCancionDAO relacion = new ArtistaCancionDAO();
                relacion.setIdArtista(rs.getInt("idArtista"));
                relacion.setNombreArtista(rs.getString("nomArt"));
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setNombreCancion(rs.getString("nomCancion"));
                relacion.setInterpretado(rs.getString("interpretado"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRelaciones;
    }

    public ObservableList<ArtistaCancionDAO> Select_By_Artista(int idArtista) {
        ObservableList<ArtistaCancionDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT ac.idArtista, a.nomArt, ac.idCancion, c.nomCancion, ac.interpretado " +
                "FROM Cancion_Artista ac " +
                "JOIN tblArtista a ON ac.idArtista = a.idArt " +
                "JOIN tblCancion c ON ac.idCancion = c.id_Cancion " +
                "WHERE ac.idArtista = " + idArtista;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ArtistaCancionDAO relacion = new ArtistaCancionDAO();
                relacion.setIdArtista(rs.getInt("idArtista"));
                relacion.setNombreArtista(rs.getString("nomArt"));
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setNombreCancion(rs.getString("nomCancion"));
                relacion.setInterpretado(rs.getString("interpretado"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRelaciones;
    }


    public ObservableList<ArtistaCancionDAO> Select_By_Cancion(int idCancion) {
        ObservableList<ArtistaCancionDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT ac.idArtista, a.nomArt, ac.idCancion, c.nomCancion, ac.interpretado " +
                "FROM Cancion_Artista ac " +
                "JOIN tblArtista a ON ac.idArtista = a.idArt " +
                "JOIN tblCancion c ON ac.idCancion = c.id_Cancion " +
                "WHERE ac.idCancion = " + idCancion;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ArtistaCancionDAO relacion = new ArtistaCancionDAO();
                relacion.setIdArtista(rs.getInt("idArtista"));
                relacion.setNombreArtista(rs.getString("nomArt"));
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setNombreCancion(rs.getString("nomCancion"));
                relacion.setInterpretado(rs.getString("interpretado"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRelaciones;
    }
    public ArtistaDAO getArtista() {
        ArtistaDAO artista = new ArtistaDAO();
        artista.setIdArt(this.idArtista);
        ObservableList<ArtistaDAO> listaArtista = artista.SELECTALL();
        for (ArtistaDAO alb : listaArtista) {
            if (alb.getIdArt() == this.idArtista) {
                return alb;
            }
        }
        return artista;
    }
    public CancionDAO getCancion() {
        CancionDAO cancion = new CancionDAO();
        cancion.setIdCancion(this.idCancion);
        ObservableList<CancionDAO> listaCanciones = cancion.SELECTALL();
        for (CancionDAO can : listaCanciones) {
            if (can.getIdCancion() == this.idCancion) {
                return can;
            }
        }
        return cancion;
    }

}