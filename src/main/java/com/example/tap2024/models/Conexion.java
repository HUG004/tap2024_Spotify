package com.example.tap2024.models;
import java.sql.DriverManager;
import java.sql.Connection;
public class Conexion {
    static private String DB="spotify";
    static private String USER="admin";
    static private String PASSWORD="1234567890";
    static private String HOST="localhost";
    static private String  PORT="3306";
    public static Connection conexion;

    public static void crearConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+":"+PORT+"/"+DB,USER,PASSWORD);
            System.out.println("Conexion exitosa a la Base de Datos");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
