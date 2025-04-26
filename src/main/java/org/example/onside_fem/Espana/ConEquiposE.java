package org.example.onside_fem.Espana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConEquiposE {
    @FXML
    private MenuItem volverItem;

    @FXML
    private Menu menuLiga;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private ComboBox<String> comboBoxIdioma;

    @FXML
    private Hyperlink hyperlinkAyuda;

    @FXML
    private Button btnClasificacion;

    @FXML
    private Pane rootPane;


    private final Map<String, String> ligaPantallas = new HashMap<>();

    private final Map<String, String> seleccionPantallas = new HashMap<>();

    private final Map<String, String> equipoPantallas = new HashMap<>();

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
        ligaPantallas.put("Liberty A-League", "/org/example/onside_fem/FXML/Ligas/LibertyALeague.fxml");
        ligaPantallas.put("Women's Super League", "/org/example/onside_fem/FXML/Ligas/WomensSuperLeague.fxml");
        ligaPantallas.put("Arkema Premiere League", "/org/example/onside_fem/FXML/Ligas/ArkemaPremiereLeague.fxml");
        ligaPantallas.put("Yogibo WE League", "/org/example/onside_fem/FXML/Ligas/YogiboWELeague.fxml");
        ligaPantallas.put("National Super League", "/org/example/onside_fem/FXML/Ligas/NationalSuperLeague.fxml");

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
        for (MenuItem item : menuLiga.getItems()) {
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
        btnClasificacion.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Espana/PClasificacionEspana.fxml"));
    }


    private void inicializarEquipos() {
        equipoPantallas.put("ivBilbao", "/org/example/onside_fem/Espana/PBilbao.fxml");
        equipoPantallas.put("ivAtletico", "/org/example/onside_fem/Espana/PAtleti.fxml");
        equipoPantallas.put("ivBarcelona", "/org/example/onside_fem/Espana/PBarsa.fxml");
        equipoPantallas.put("ivTenerife", "/org/example/onside_fem/Espana/PTenerife.fxml");
        equipoPantallas.put("ivDepor", "/org/example/onside_fem/Espana/Equipos/Depor.fxml");
        equipoPantallas.put("ivEibar", "/org/example/onside_fem/Espana/Equipos/Eibar.fxml");
        equipoPantallas.put("ivEspanyol", "/org/example/onside_fem/Espana/Equipos/Espanyol.fxml");
        equipoPantallas.put("ivGranada", "/org/example/onside_fem/Espana/Equipos/Granada.fxml");
        equipoPantallas.put("ivLBadalona", "/org/example/onside_fem/Espana/Equipos/LBadalona.fxml");
        equipoPantallas.put("ivLevante", "/org/example/onside_fem/Espana/Equipos/Levante.fxml");
        equipoPantallas.put("ivMadridCFF", "/org/example/onside_fem/Espana/Equipos/MadridCFF.fxml");
        equipoPantallas.put("ivMadrid", "/org/example/onside_fem/Espana/Equipos/RealMadrid.fxml");
        equipoPantallas.put("ivRealSociedad", "/org/example/onside_fem/Espana/Equipos/RealSociedad.fxml");
        equipoPantallas.put("ivSevilla", "/org/example/onside_fem/Espana/Equipos/Sevilla.fxml");
        equipoPantallas.put("ivValencia", "/org/example/onside_fem/Espana/Equipos/Valencia.fxml");
        equipoPantallas.put("ivBetis", "/org/example/onside_fem/Espana/Equipos/Betis.fxml");

        // Agrega listeners dinámicamente
        for (javafx.scene.Node node : rootPane.getChildren()) {
            if (node instanceof ImageView) {
                String id = node.getId();
                if (id != null && id.startsWith("iv") && equipoPantallas.containsKey(id)) {
                    node.setOnMouseClicked(e -> cargarPantalla(equipoPantallas.get(id)));
                    node.setStyle("-fx-cursor: hand;"); // Opcional: cambia el cursor al pasar por encima
                }
            }
        }
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
