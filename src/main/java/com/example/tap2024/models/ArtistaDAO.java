package com.example.tap2024.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArtistaDAO {
    private int idArt;
    private String nomArt;

    @Override
    public String toString() {
        return this.getNomArt();
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }

    public String getNomArt() {
        return nomArt;
    }

    public void setNomArt(String nomArt) {
        this.nomArt = nomArt;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblArtista(nomArt) VALUES('" + this.nomArt + "')";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            rowCount = 0;
        }
        return rowCount;
    }

    public void UPDATE() {
        String query = "UPDATE tblArtista SET nomArt = '" + this.nomArt + "' WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean DELETE() {
        String query = "DELETE FROM tblArtista WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ObservableList<ArtistaDAO> SELECTALL() {
        ArtistaDAO objArt;
        String query = "SELECT * FROM tblArtista";
        ObservableList<ArtistaDAO> listaArt = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objArt = new ArtistaDAO();
                objArt.idArt = res.getInt(1);
                objArt.nomArt = res.getString(2);
                listaArt.add(objArt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaArt;
    }

    public ArtistaDAO SELECTBYID() {
        ArtistaDAO objArt = null;
        String query = "SELECT * FROM tblArtista WHERE idArt = " + this.idArt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                objArt = new ArtistaDAO();
                objArt.idArt = res.getInt(1);
                objArt.nomArt = res.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objArt;
    }
}
