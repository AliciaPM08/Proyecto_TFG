package org.example.onside_fem.Francia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConPantallaP {
    @FXML
    private MenuItem volverItem;

    @FXML
    private Menu menuLigas;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private ComboBox<String> comboBoxIdioma;

    @FXML
    private Hyperlink hyperlinkAyuda;

    @FXML
    private Button btnClasificacion;

    @FXML
    private Button btnEquipo;


    private final Map<String, String> ligaPantallas = new HashMap<>();


    private final Map<String, String> seleccionPantallas = new HashMap<>();

    @FXML
    public void initialize() {
        inicializarIdioma();
        inicializarRutas();
        inicializarMenuInicio();
        inicializarLigas();
        inicializarSelecciones();
        inicializarClasifiacion();
        inicializarEquipos();
        hyperlinkAyuda.setOnAction(this::abrirAyuda);
    }

    private void inicializarIdioma() {
        comboBoxIdioma.getItems().addAll("Español", "Inglés");
        comboBoxIdioma.setValue("Español");
        comboBoxIdioma.setOnAction(e -> cambiarIdioma());
    }

    private void cambiarIdioma() {
        String idioma = comboBoxIdioma.getValue();
        Locale locale = idioma.equals("Inglés") ? new Locale("en", "US") : new Locale("es", "ES");
        System.out.println("Idioma cambiado a: " + idioma);
    }

    private void inicializarMenuInicio() {
        volverItem.setOnAction(e -> cargarPantalla("/org/example/onside_fem/PantallaPrincipal.fxml"));
    }

    private void inicializarRutas() {
        // LIGAS
        ligaPantallas.put("Finetwork Liga F", "/org/example/onside_fem/Espana/PPLigaEspanola.fxml");
        ligaPantallas.put("Liberty A-League", "/org/example/onside_fem/Australia/PPLigaAustraliana.fxml");
        ligaPantallas.put("Women's Super League", "/org/example/onside_fem/FXML/Ligas/WomensSuperLeague.fxml");
        ligaPantallas.put("Arkema Premiere League", "/org/example/onside_fem/FXML/Ligas/ArkemaPremiereLeague.fxml");
        ligaPantallas.put("Yogibo WE League", "/org/example/onside_fem/Japon/PPLigaJaponesa.fxml");
        ligaPantallas.put("National Super League", "/org/example/onside_fem/EEUU/PPLigaEEUU.fxml");

        // SELECCIONES
        seleccionPantallas.put("Alemania", "/org/example/onside_fem/FXML/Selecciones/Alemania.fxml");
        seleccionPantallas.put("Australia", "/org/example/onside_fem/FXML/Selecciones/Australia.fxml");
        seleccionPantallas.put("Brasil", "/org/example/onside_fem/FXML/Selecciones/Brasil.fxml");
        seleccionPantallas.put("Canada", "/org/example/onside_fem/FXML/Selecciones/Canada.fxml");
        seleccionPantallas.put("Colombia", "/org/example/onside_fem/FXML/Selecciones/Colombia.fxml");
        seleccionPantallas.put("España", "/org/example/onside_fem/FXML/Selecciones/España.fxml");
        seleccionPantallas.put("Estados Unidos", "/org/example/onside_fem/FXML/Selecciones/EstadosUnidos.fxml");
        seleccionPantallas.put("Francia", "/org/example/onside_fem/FXML/Selecciones/Francia.fxml");
        seleccionPantallas.put("Inglatera", "/org/example/onside_fem/FXML/Selecciones/Inglaterra.fxml"); // Corregido
        seleccionPantallas.put("Japon", "/org/example/onside_fem/FXML/Selecciones/Japon.fxml");
        seleccionPantallas.put("Nigeria", "/org/example/onside_fem/FXML/Selecciones/Nigeria.fxml");
        seleccionPantallas.put("Nueva Zelanda", "/org/example/onside_fem/FXML/Selecciones/NuevaZelanda.fxml");
        seleccionPantallas.put("Sudafrica", "/org/example/onside_fem/FXML/Selecciones/Sudafrica.fxml");
        seleccionPantallas.put("Suecia", "/org/example/onside_fem/FXML/Selecciones/Suecia.fxml");
    }

    private void inicializarLigas() {
        for (MenuItem item : menuLigas.getItems()) {
            item.setOnAction(e -> {
                String liga = ((MenuItem) e.getSource()).getText();
                String ruta = ligaPantallas.get(liga);
                if (ruta != null) {
                    System.out.println("Cargando liga: " + liga);
                    cargarPantalla(ruta);
                } else {
                    System.err.println("No se encontró la pantalla para la liga: " + liga);
                }
            });
        }
    }

    private void inicializarSelecciones() {
        for (MenuItem item : menuSelecciones.getItems()) {
            item.setOnAction(e -> {
                String seleccion = ((MenuItem) e.getSource()).getText();
                String ruta = seleccionPantallas.get(seleccion);
                if (ruta != null) {
                    System.out.println("Cargando selección: " + seleccion);
                    cargarPantalla(ruta);
                } else {
                    System.err.println("No se encontró la pantalla para la selección: " + seleccion);
                }
            });
        }
    }

    private void inicializarClasifiacion() {
        btnClasificacion.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Francia/PClasificacionFrancia.fxml"));
    }

    private void inicializarEquipos() {
        btnEquipo.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Francia/PEquiposFrancia.fxml"));
    }

    private void cargarPantalla(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Pane root = loader.load();
            Stage stage = (Stage) comboBoxIdioma.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } catch (IOException e) {
            System.err.println("Error al cargar: " + rutaFXML);
            e.printStackTrace();
        }
    }

    private void abrirAyuda(ActionEvent event) {
        try {
            File ayudaHTML = new File("src/main/resources/ayuda/ayuda_usuario.html");
            if (ayudaHTML.exists()) {
                Desktop.getDesktop().browse(ayudaHTML.toURI());
            } else {
                System.err.println("Archivo de ayuda no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
