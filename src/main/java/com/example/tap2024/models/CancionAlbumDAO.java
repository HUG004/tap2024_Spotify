package com.example.tap2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionAlbumDAO {
    private int idCancion, idAlbum, idAlbumOriginal, idCancionOriginal;
    private String descripcion, nombreAlbum, nombreCancion;

    public int getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(int idCancion) {
        this.idCancion = idCancion;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdAlbumOriginal() {
        return idAlbumOriginal;
    }

    public void setIdAlbumOriginal(int idAlbumOriginal) {
        this.idAlbumOriginal = idAlbumOriginal;
    }

    public int getIdCancionOriginal() {
        return idCancionOriginal;
    }

    public void setIdCancionOriginal(int idCancionOriginal) {
        this.idCancionOriginal = idCancionOriginal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public int INSERT(){
        int rowcount;
        String query = "INSERT INTO Album_Cancion(idCancion, idAlbum, descripcion)"+
                "VALUES (" + this.idCancion + ", " + this.idAlbum + ", '" + this.descripcion + "')";
        try{
            Statement stmt = Conexion.conexion.createStatement();
            rowcount = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            rowcount = 0;
        }

        return rowcount;
    }

    public void UPDATE(){
        String query = "UPDATE Album_Cancion SET idAlbum = " +this.idAlbum +
                ", idCancion = "+ this.idCancion +
                ", descripcion = '"+ this.descripcion +
                "' WHERE idCancion = "+ this.idCancion +
                " AND idAlbum = " + this.idAlbum;
        try{
            Statement stmt = Conexion.conexion.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM Album_Cancion WHERE idCancion = ? AND idAlbum = ?";
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(query)) {
            stmt.setInt(1, this.idCancion);
            stmt.setInt(2, this.idAlbum);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<CancionAlbumDAO> SELECTALL() {
        ObservableList<CancionAlbumDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT ab.idAlbum, a.nombre, ab.idCancion, c.nomCancion, ab.descripcion " +
                "FROM Album_Cancion ab " +
                "JOIN tblAlbum a ON ab.idAlbum = a.idAlbum " +
                "JOIN tblCancion c ON ab.idCancion = c.id_Cancion";
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                CancionAlbumDAO relacion = new CancionAlbumDAO();
                relacion.setIdAlbum(rs.getInt("idAlbum"));
                relacion.setNombreAlbum(rs.getString("nombre"));
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setNombreCancion(rs.getString("nomCancion"));
                relacion.setDescripcion(rs.getString("descripcion"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRelaciones;
    }

    public ObservableList<CancionAlbumDAO> SELECT_BY_ALBUM(int idAlbum) {
        ObservableList<CancionAlbumDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT ab.idAlbum, a.nombre, ab.idCancion, c.nomCancion, ab.descripcion " +
                "FROM Album_Cancion ab " +
                "JOIN tblAlbum a ON ab.idAlbum = a.idAlbum " +
                "JOIN tblCancion c ON ab.idCancion = c.id_Cancion " +
                "WHERE ab.idAlbum = " + idAlbum;

        System.out.println(query);
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                CancionAlbumDAO relacion = new CancionAlbumDAO();
                relacion.setIdAlbum(rs.getInt("idAlbum"));
                relacion.setNombreAlbum(rs.getString("nombre"));
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setNombreCancion(rs.getString("nomCancion"));
                relacion.setDescripcion(rs.getString("descripcion"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRelaciones;
    }

    public ObservableList<CancionAlbumDAO> SELECT_BY_CANCION(int idCancion) {
        ObservableList<CancionAlbumDAO> listaRelaciones = FXCollections.observableArrayList();
        String query = "SELECT ab.idAlbum, a.nombre, ab.idCancion, c.nomCancion, ab.descripcion " +
                "FROM Album_Cancion ab " +
                "JOIN tblAlbum a ON ab.idAlbum = a.idAlbum " +
                "JOIN tblCancion c ON ab.idCancion = c.id_Cancion " +
                "WHERE ab.idCancion = " + idCancion;
        try {
            Statement stmt = Conexion.conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                CancionAlbumDAO relacion = new CancionAlbumDAO();
                relacion.setIdAlbum(rs.getInt("idAlbum"));
                relacion.setNombreAlbum(rs.getString("nombre"));
                relacion.setIdCancion(rs.getInt("idCancion"));
                relacion.setNombreCancion(rs.getString("nomCancion"));
                relacion.setDescripcion(rs.getString("descripcion"));
                listaRelaciones.add(relacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRelaciones;
    }

    public CancionDAO getCancion(){
        CancionDAO cancion = new CancionDAO();
        cancion.setIdCancion(this.idCancion);
        ObservableList<CancionDAO> listaCanciones = cancion.SELECTALL();
        for(CancionDAO can : listaCanciones){
            if(can.getIdCancion() == this.idCancion){
                return can;
            }
        }
        return cancion;
    }

    public AlbumDAO getAlbum(){
        AlbumDAO album = new AlbumDAO();
        album.setIdAlbum(this.idAlbum);
        ObservableList<AlbumDAO> listaAlbums = album.SELECTALL();
        for(AlbumDAO alb : listaAlbums){
            if(alb.getIdAlbum() == this.idAlbum){
                return alb;
            }
        }
        return album;
    }
}