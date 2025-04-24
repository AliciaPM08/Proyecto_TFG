package org.example.onside_fem.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JugadoraDAO {
    public List<Jugadora> obtenerJugadoresPorEquipoYPosicion(String equipo, String posicion) {
        List<Jugadora> jugadoras = new ArrayList<>();
        String sql = "SELECT nombre_jugadora, posicion, nombre_equipo, fecha_nacimiento FROM jugadorasequipo WHERE nombre_equipo = ? AND posicion = ?";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipo);
            stmt.setString(2, posicion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Jugadora j = new Jugadora(
                        rs.getString("nombre_jugadora"),
                        rs.getString("posicion"),
                        rs.getString("nombre_equipo"),
                        rs.getString("fecha_nacimiento")
                );
                jugadoras.add(j);
            }

            return jugadoras;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jugadoras;
    }
}
