package org.example.onside_fem.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadisticasDAO {
    public Estadisticas obtenerEstadisticas(String equipo, String liga) {
        String query = "SELECT goles_anotados, goles_recibidos, tarjetas_amarillas, tarjetas_rojas " +
                "FROM estadisticasequipo " +
                "WHERE nombre_equipo = ? AND nombre_liga = ?";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, equipo);
            stmt.setString(2, liga);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Estadisticas(
                        rs.getInt("goles_anotados"),
                        rs.getInt("goles_recibidos"),
                        rs.getInt("tarjetas_amarillas"),
                        rs.getInt("tarjetas_rojas")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
