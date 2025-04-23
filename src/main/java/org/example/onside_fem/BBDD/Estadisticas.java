package org.example.onside_fem.BBDD;

public class Estadisticas {
    private int golesAnotados;
    private int golesRecibidos;
    private int tarjetasAmarillas;
    private int tarjetasRojas;

    public Estadisticas(int golesAnotados, int golesRecibidos, int tarjetasAmarillas, int tarjetasRojas) {
        this.golesAnotados = golesAnotados;
        this.golesRecibidos = golesRecibidos;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
    }

    public int getGolesAnotados() { return golesAnotados; }
    public int getGolesRecibidos() { return golesRecibidos; }
    public int getTarjetasAmarillas() { return tarjetasAmarillas; }
    public int getTarjetasRojas() { return tarjetasRojas; }
}


