package org.example.onside_fem.BBDD;

public class Clasificacion {
    private String nombre;
    private int posicion;
    private int puntos;
    private int partidosJugados;
    private int victorias;
    private int empates;
    private int perdidos;
    private int golesFavor;
    private int golesContra;

    public Clasificacion(String nombre, int posicion, int puntos, int partidosJugados,
                  int victorias, int empates, int perdidos, int golesFavor, int golesContra) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.puntos = puntos;
        this.partidosJugados = partidosJugados;
        this.victorias = victorias;
        this.empates = empates;
        this.perdidos = perdidos;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getPosicion() { return posicion; }
    public int getPuntos() { return puntos; }
    public int getPartidosJugados() { return partidosJugados; }
    public int getVictorias() { return victorias; }
    public int getEmpates() { return empates; }
    public int getPerdidos() { return perdidos; }
    public int getGolesFavor() { return golesFavor; }
    public int getGolesContra() { return golesContra; }
}
