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

    public Jugadora obtenerJugadoraPorNombre(String equipo, String nombre) {
        String sql = "SELECT nombre_jugadora, posicion, nombre_equipo, fecha_nacimiento FROM jugadorasequipo WHERE nombre_equipo = ? AND nombre_jugadora = ?";
        try (Connection conn = ConexionBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipo);
            stmt.setString(2, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Jugadora(
                        rs.getString("nombre_jugadora"),
                        rs.getString("posicion"),
                        rs.getString("nombre_equipo"),
                        rs.getString("fecha_nacimiento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<JugadoraSeleccion> obtenerJugadoresPorSeleccionYPosicion(String seleccion, String posicion) {
        List<JugadoraSeleccion> jugadoras = new ArrayList<>();
        String sql = "SELECT nombre_jugadora, nombre_seleccion,posicion, partidos_jugados, goles, imbatidas FROM jugadorasseleccion WHERE nombre_seleccion = ? AND posicion = ?";

        try (Connection conn = ConexionBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, seleccion);
            stmt.setString(2, posicion);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JugadoraSeleccion j = new JugadoraSeleccion(
                        rs.getString("nombre_jugadora"),
                        rs.getString("nombre_seleccion"),
                        rs.getString("posicion"),
                        rs.getInt("partidos_jugados"),
                        rs.getInt("goles"),
                        rs.getInt("imbatidas")
                );
                jugadoras.add(j);
            }

            return jugadoras;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jugadoras;
    }

    public JugadoraSeleccion obtenerJugadoraSeleccionPorNombre(String seleccion, String nombre) {
        String sql = "SELECT nombre_jugadora, nombre_seleccion, posicion,partidos_jugados, goles, imbatidas FROM jugadorasseleccion WHERE nombre_seleccion = ? AND nombre_jugadora = ?";
        try (Connection conn = ConexionBD.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, seleccion);
            stmt.setString(2, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new JugadoraSeleccion(
                       rs.getString("nombre_jugadora"),
                        rs.getString("nombre_seleccion"),
                        rs.getString("posicion"),
                        rs.getInt("partidos_jugados"),
                        rs.getInt("goles"),
                        rs.getInt("imbatidas")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
