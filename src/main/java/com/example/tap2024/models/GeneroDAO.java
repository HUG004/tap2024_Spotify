package com.example.tap2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneroDAO {
    private String tipoGenero;
    private String descripcion;

    @Override
    public String toString() {
        return this.tipoGenero;
    }

    public String getTipoGenero() {
        return tipoGenero;
    }

    public void setTipoGenero(String tipoGenero) {
        this.tipoGenero = tipoGenero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean esNuevo() {
        boolean esNuevo = true;
        String query = "SELECT COUNT(*) FROM tblGenero WHERE Tipo_Genero = '" + this.tipoGenero + "'";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next() && rs.getInt(1) > 0) {
                esNuevo = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esNuevo;
    }

    public int INSERT() {
        int result = 0;
        try {
            String query = "INSERT INTO tblGenero(Tipo_Genero, descrip) VALUES('" + this.tipoGenero + "', '" + this.descripcion + "')";
            Statement stmt = Conexion.conexion.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void UPDATE() {
        try {
            String query = "UPDATE tblGenero SET descrip = '" + this.descripcion + "' WHERE Tipo_Genero = '" + this.tipoGenero + "'";
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        try {
            String query = "DELETE FROM tblGenero WHERE Tipo_Genero = '" + this.tipoGenero + "'";
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<GeneroDAO> SELECTALL() {
        ObservableList<GeneroDAO> listaGeneros = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM tblGenero";
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                GeneroDAO genero = new GeneroDAO();
                genero.setTipoGenero(rs.getString("Tipo_Genero"));
                genero.setDescripcion(rs.getString("descrip"));
                listaGeneros.add(genero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaGeneros;
    }

}