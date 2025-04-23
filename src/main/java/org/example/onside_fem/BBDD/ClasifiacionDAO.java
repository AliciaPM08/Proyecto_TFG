package org.example.onside_fem.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClasifiacionDAO {
    public static List<Clasificacion> obtenerEquiposPorLiga(String nombreLiga) {
        List<Clasificacion> lista = new ArrayList<>();

        String sql = "SELECT nombre_equipo, posicion, puntos, partidos_jugados, victorias, empates, derrotas, goles_favor, goles_contra FROM clasificacion WHERE nombre_liga = ? ORDER BY posicion ASC";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreLiga);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Clasificacion(
                        rs.getString("nombre_equipo"),
                        rs.getInt("posicion"),
                        rs.getInt("puntos"),
                        rs.getInt("partidos_jugados"),
                        rs.getInt("victorias"),
                        rs.getInt("empates"),
                        rs.getInt("derrotas"),
                        rs.getInt("goles_favor"),
                        rs.getInt("goles_contra")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
