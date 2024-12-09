package com.example.tap2024.models;

import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

public class ClienteDAO {
    private int idClt;
    private String nomClt;
    private String telClt;
    private String emailClt;
    private String contraseña;

    public int getIdClt() {
        return idClt;
    }

    public void setIdClt(int idClt) {
        this.idClt = idClt;
    }

    public String getNomClt() {
        return nomClt;
    }

    public void setNomClt(String nomClt) {
        this.nomClt = nomClt;
    }

    public String getTelClt() {
        return telClt;
    }

    public void setTelClt(String telClt) {
        this.telClt = telClt;
    }

    public String getEmailClt() {
        return emailClt;
    }

    public void setEmailClt(String emailClt) {
        this.emailClt = emailClt;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int INSERT() {
        int rowCount;
        String query = "INSERT INTO tblcliente(nomClt, telClt, emailClt, contraseña)" +
                " VALUES('" + this.nomClt + "','" + this.telClt + "','" + this.emailClt + "','" + this.contraseña + "')";
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
        String query = "UPDATE tblcliente SET nomClt = '" + this.nomClt + "', telClt = '" + this.telClt + "', " +
                "emailClt = '" + this.emailClt + "', contraseña = '" + this.contraseña + "' WHERE idClt = " + this.idClt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM tblcliente WHERE idClt = " + this.idClt;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ClienteDAO> SELECTALL() {
        ClienteDAO objCte;
        String query = "SELECT * FROM tblCliente";
        ObservableList<ClienteDAO> listaC = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objCte = new ClienteDAO();
                objCte.idClt = res.getInt("idClt");
                objCte.nomClt = res.getString("nomClt");
                objCte.telClt = res.getString("telClt");
                objCte.emailClt = res.getString("emailClt");
                objCte.contraseña = res.getString("contraseña");
                listaC.add(objCte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaC;
    }

    @Override
    public String toString() {
        return String.valueOf(idClt);
    }
}
