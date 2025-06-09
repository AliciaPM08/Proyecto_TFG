package org.example.onside_fem.Espana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConEquiposP {
    @FXML
    private MenuItem volverItem;

    @FXML
    private Menu menuLiga;

    @FXML
    private Menu menuInicio;

    @FXML
    private Menu menuSelecciones;



    @FXML
    private ComboBox<String> comboBoxIdiomas;

    @FXML
    private Hyperlink hyperlinkAyuda;

    @FXML
    private Button btnClasificacion;

    @FXML
    private Pane rootPane;

    @FXML private MenuItem menuAlemania;
    @FXML private MenuItem menuAustralia;
    @FXML private MenuItem menuBrasil;
    @FXML private MenuItem menuCanada;
    @FXML private MenuItem menuColombia;
    @FXML private MenuItem menuEspana;
    @FXML private MenuItem menuEstados;
    @FXML private MenuItem menuFrancia;
    @FXML private MenuItem menuInglaterra;
    @FXML private MenuItem menuNigeria;
    @FXML private MenuItem menuNZelanda;
    @FXML private MenuItem menuSudafrica;
    @FXML private MenuItem menuSuecia;

    @FXML private Label footer;

    private ResourceBundle recursos;
    private Locale localeActual = new Locale("es", "ES");


    private final Map<String, String> ligaPantallas = new HashMap<>();

    private final Map<String, String> seleccionPantallas = new HashMap<>();

    private final Map<String, String> equipoPantallas = new HashMap<>();

    @FXML
    public void initialize() {
        inicializarIdioma();
        recursos = ResourceBundle.getBundle("idiomas.messages", localeActual);
        traducirUI();

        inicializarRutas();
        inicializarMenuInicio();
        inicializarLigas();
        inicializarSelecciones();
        inicializarClasifiacion();
        inicializarEquipos();
        hyperlinkAyuda.setOnAction(this::abrirAyuda);
    }

    private void inicializarIdioma() {
        comboBoxIdiomas.getItems().addAll("Español", "English");
        comboBoxIdiomas.setValue("Español");
        comboBoxIdiomas.setOnAction(e -> cambiarIdioma());
    }

    private void cambiarIdioma() {
        String idiomaSeleccionado = comboBoxIdiomas.getValue();
        if (idiomaSeleccionado.equals("English")) {
            localeActual = new Locale("en", "US");
        } else {
            localeActual = new Locale("es", "ES");
        }

        try {
            recursos = ResourceBundle.getBundle("idiomas.messages", localeActual);
            traducirUI();
        } catch (MissingResourceException e) {
            System.err.println("Archivo de idioma no encontrado.");
        }
    }

    private void traducirUI() {
        //Menu
        menuInicio.setText(recursos.getString("menu.inicio"));
        menuLiga.setText(recursos.getString("menu.ligas"));
        menuSelecciones.setText(recursos.getString("menu.selecciones"));
        menuAlemania.setText(recursos.getString("menu.alemania"));
        menuAustralia.setText(recursos.getString("menu.australia"));
        menuBrasil.setText(recursos.getString("menu.brasil"));
        menuCanada.setText(recursos.getString("menu.canada"));
        menuColombia.setText(recursos.getString("menu.colombia"));
        menuEspana.setText(recursos.getString("menu.espana"));
        menuEstados.setText(recursos.getString("menu.unidos"));
        menuFrancia.setText(recursos.getString("menu.francia"));
        menuInglaterra.setText(recursos.getString("menu.inglaterra"));
        menuNigeria.setText(recursos.getString("menu.nigeria"));
        menuNZelanda.setText(recursos.getString("menu.nzelanda"));
        menuSudafrica.setText(recursos.getString("menu.sudafrica"));
        menuSuecia.setText(recursos.getString("menu.suecia"));
        volverItem.setText(recursos.getString("menu.volver"));
        hyperlinkAyuda.setText(recursos.getString("menu.ayuda"));

        //Boton
        btnClasificacion.setText(recursos.getString("btn.clasificacion"));


        //Footer
        footer.setText(recursos.getString("footer.copy"));


    }

    private void inicializarMenuInicio() {
        volverItem.setOnAction(e -> cargarPantalla("/org/example/onside_fem/PantallaPrincipal.fxml"));
    }

    private void inicializarRutas() {
        // LIGAS
        ligaPantallas.put("Finetwork Liga F", "/org/example/onside_fem/Espana/PPLigaEspanola.fxml");
        ligaPantallas.put("Liberty A-League", "/org/example/onside_fem/Australia/PPLigaAustraliana.fxml");
        ligaPantallas.put("Women's Super League", "/org/example/onside_fem/Inglaterra/PPLigaInglaterra.fxml");
        ligaPantallas.put("Arkema Premiere League", "/org/example/onside_fem/Francia/PPLigaFrancia.fxml");
        ligaPantallas.put("Yogibo WE League", "/org/example/onside_fem/Japon/PPLigaJaponesa.fxml");
        ligaPantallas.put("National Super League", "/org/example/onside_fem/EEUU/PPLigaEEUU.fxml");

        // SELECCIONES
        seleccionPantallas.put("Alemania", "/org/example/onside_fem/Selecciones/PAlemania.fxml");
        seleccionPantallas.put("Germany", "/org/example/onside_fem/Selecciones/PAlemania.fxml");
        seleccionPantallas.put("Australia", "/org/example/onside_fem/Selecciones/PAustralia.fxml");
        seleccionPantallas.put("Brasil", "/org/example/onside_fem/Selecciones/PBrasil.fxml");
        seleccionPantallas.put("Brazil", "/org/example/onside_fem/Selecciones/PBrasil.fxml");
        seleccionPantallas.put("Canada", "/org/example/onside_fem/Selecciones/PCanada.fxml");
        seleccionPantallas.put("Colombia", "/org/example/onside_fem/Selecciones/PColombia.fxml");
        seleccionPantallas.put("España", "/org/example/onside_fem/Selecciones/PEspana.fxml");
        seleccionPantallas.put("Spain", "/org/example/onside_fem/Selecciones/PEspana.fxml");
        seleccionPantallas.put("Estados Unidos", "/org/example/onside_fem/Selecciones/PEEUU.fxml");
        seleccionPantallas.put("USA", "/org/example/onside_fem/Selecciones/PEEUU.fxml");
        seleccionPantallas.put("Francia", "/org/example/onside_fem/Selecciones/PFrancia.fxml");
        seleccionPantallas.put("France", "/org/example/onside_fem/Selecciones/PFrancia.fxml");
        seleccionPantallas.put("Inglaterra", "/org/example/onside_fem/Selecciones/PInglaterra.fxml");
        seleccionPantallas.put("England", "/org/example/onside_fem/Selecciones/PInglaterra.fxml");
        seleccionPantallas.put("Nigeria", "/org/example/onside_fem/Selecciones/PNigeria.fxml");
        seleccionPantallas.put("Nueva Zelanda", "/org/example/onside_fem/Selecciones/PNuevaZelanda.fxml");
        seleccionPantallas.put("New Zeland", "/org/example/onside_fem/Selecciones/PNuevaZelanda.fxml");
        seleccionPantallas.put("Sudafrica", "/org/example/onside_fem/Selecciones/PSudafrica.fxml");
        seleccionPantallas.put("Suecia", "/org/example/onside_fem/Selecciones/PSuecia.fxml");
        seleccionPantallas.put("Sweden", "/org/example/onside_fem/Selecciones/PSuecia.fxml");
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
        equipoPantallas.put("ivDepor", "/org/example/onside_fem/Espana/PDepor.fxml");
        equipoPantallas.put("ivEibar", "/org/example/onside_fem/Espana/PEibar.fxml");
        equipoPantallas.put("ivEspanyol", "/org/example/onside_fem/Espana/PEspayol.fxml");
        equipoPantallas.put("ivGranada", "/org/example/onside_fem/Espana/PGranada.fxml");
        equipoPantallas.put("ivLBadalona", "/org/example/onside_fem/Espana/PLevanteB.fxml");
        equipoPantallas.put("ivLevante", "/org/example/onside_fem/Espana/PLevante.fxml");
        equipoPantallas.put("ivMadridCFF", "/org/example/onside_fem/Espana/PMadrid.fxml");
        equipoPantallas.put("ivMadrid", "/org/example/onside_fem/Espana/PRMadrid.fxml");
        equipoPantallas.put("ivRealSociedad", "/org/example/onside_fem/Espana/PRSociedad.fxml");
        equipoPantallas.put("ivSevilla", "/org/example/onside_fem/Espana/PSevilla.fxml");
        equipoPantallas.put("ivValencia", "/org/example/onside_fem/Espana/PValecia.fxml");
        equipoPantallas.put("ivBetis", "/org/example/onside_fem/Espana/PBetis.fxml");

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
            Stage stage = (Stage) comboBoxIdiomas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } catch (IOException e) {
            System.err.println("Error al cargar: " + rutaFXML);
            e.printStackTrace();
        }
    }

    private void abrirAyuda(ActionEvent event) {
        try {
            File ayudaHTML = new File("/org/example/onside_fem/ayuda.html");
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
