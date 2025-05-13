package org.example.onside_fem.BBDD;

public class JugadoraSeleccion {
    private String nombre_jugadora;
    private String nombre_seleccion;
    private String posicion;
    private int partidos_jugados;
    private int goles;
    private int imbatidas;

    public JugadoraSeleccion(String nombre_jugadora, String nombre_seleccion, String posicion, int partidos_jugados, int goles, int imbatidas) {
        this.nombre_jugadora = nombre_jugadora;
        this.nombre_seleccion = nombre_seleccion;
        this.posicion = posicion;
        this.partidos_jugados = partidos_jugados;
        this.goles = goles;
        this.imbatidas = imbatidas;
    }

    public String getNombre_jugadora() {
        return nombre_jugadora;
    }

    public String getNombre_seleccion() {
        return nombre_seleccion;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getPartidos_juagados() {
        return partidos_jugados;
    }

    public int getGoles() {
        return goles;
    }

    public int getImbatidas() {
        return imbatidas;
    }
}
