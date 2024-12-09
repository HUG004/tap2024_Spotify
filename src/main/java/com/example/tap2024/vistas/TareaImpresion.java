package com.example.tap2024.vistas;

import javafx.beans.property.*;

public class TareaImpresion {
    private final StringProperty noArchivo;
    private final StringProperty nombreArchivo;
    private final IntegerProperty numeroHojas;
    private final StringProperty horaAcceso;
    private final DoubleProperty progreso; // Nueva propiedad para el progreso

    public TareaImpresion(String noArchivo, String nombreArchivo, int numeroHojas, String horaAcceso) {
        this.noArchivo = new SimpleStringProperty(noArchivo);
        this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
        this.numeroHojas = new SimpleIntegerProperty(numeroHojas);
        this.horaAcceso = new SimpleStringProperty(horaAcceso);
        this.progreso = new SimpleDoubleProperty(0); // Inicia con 0% de progreso
    }

    // Getters para las propiedades
    public StringProperty noArchivoProperty() {
        return noArchivo;
    }

    public StringProperty nombreArchivoProperty() {
        return nombreArchivo;
    }

    public IntegerProperty numeroHojasProperty() {
        return numeroHojas;
    }

    public StringProperty horaAccesoProperty() {
        return horaAcceso;
    }

    public DoubleProperty progresoProperty() {
        return progreso;
    }

    // Métodos para obtener valores
    public int getNumeroHojas() {
        return numeroHojas.get();
    }

    public double getProgreso() {
        return progreso.get();
    }

    // Método para actualizar el progreso
    public void setProgreso(double value) {
        this.progreso.set(value);
    }
}