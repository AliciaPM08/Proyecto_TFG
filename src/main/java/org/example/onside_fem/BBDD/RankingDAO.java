package org.example.onside_fem.BBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankingDAO {

    public List<Ranking> obtenerRanking() {
        List<Ranking> lista = new ArrayList<>();
        String sql = "SELECT nombre_seleccion, ranking_fifa FROM selecciones ORDER BY ranking_fifa ASC";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Ranking(rs.getString("nombre_seleccion"), rs.getInt("ranking_fifa")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
