package org.example.onside_fem.BBDD;

import java.sql.Date;
import java.time.LocalDate;

public class Jugadora {
    private String nombre;
    private String posicion;
    private String nombre_equipo;
    private String fecha;


    public Jugadora(String nombre, String posicion, String fecha, String nombre_equipo) {
        this.nombre = nombre;
        this.nombre_equipo = nombre_equipo;
        this.posicion = posicion;
        this.fecha = fecha;

    }

    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
    public String getNombre_equipo() {
        return nombre_equipo;
    }
    public String getFecha() {
        return fecha;
    }
}
