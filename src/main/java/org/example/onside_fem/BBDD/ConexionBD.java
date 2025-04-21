package org.example.onside_fem.BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexion de la base de datos
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/onsidefem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Obtiene una conexión a la base de datos.
     * @return Una conexión a la base de datos, o null si no se pudo establecer la conexión.
     */
    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
            return null;
        }
    }
}
