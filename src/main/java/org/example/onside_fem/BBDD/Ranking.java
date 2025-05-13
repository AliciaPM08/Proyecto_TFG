package org.example.onside_fem.BBDD;

import java.util.List;

public class Ranking {
    private String nombre_seleccion;
    private int ranking_fifa;

    public Ranking(String nombre_seleccion, int ranking_fifa) {
        this.nombre_seleccion = nombre_seleccion;
        this.ranking_fifa = ranking_fifa;
    }

    public String getNombre_seleccion() {
        return nombre_seleccion;
    }

    public void setNombre_seleccion(String nombre_seleccion) {
        this.nombre_seleccion = nombre_seleccion;
    }

    public int getRanking_fifa() {
        return ranking_fifa;
    }

    public void setRanking_fifa(int ranking_fifa) {
        this.ranking_fifa = ranking_fifa;
    }

}
