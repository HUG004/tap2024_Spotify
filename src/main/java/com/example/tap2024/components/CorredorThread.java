package com.example.tap2024.components;

import javafx.scene.control.ProgressBar;

public class CorredorThread extends Thread{

    String name;
    private ProgressBar pgbCorredor;
    public CorredorThread(String name, ProgressBar pgbCorredor){
        super(name);    //sirve para hacer referencia del construnctor de la clase padre
        this.pgbCorredor = pgbCorredor;
    }
    @Override
    public void run(){
        super.run();
        double avance  = 0;
        while(avance <= 1) {
            avance += Math.random() / 10;

            try {
                Thread.sleep((long) (Math.random() * 2000));      //Math.random da un valor entre 0 y 1
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.pgbCorredor.setProgress(avance);
        }
    }
}
