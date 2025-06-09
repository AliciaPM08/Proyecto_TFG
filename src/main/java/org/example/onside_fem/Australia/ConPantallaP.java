package org.example.onside_fem.Australia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConPantallaP {
    @FXML
    private MenuItem volverItem;

    @FXML
    private Menu menuLigas;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private ComboBox<String> comboBoxIdiomas;

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

    @FXML private Menu menuInicio;

    @FXML
    private Hyperlink hyperlinkAyuda;

    @FXML
    private Button btnClasificacion;

    @FXML
    private Button btnEquipo;

    @FXML private Label labelTitulo1, labelTitulo2, labelTitulo3, labelNoticia1, labelNoticia2, labelNoticia3;

    @FXML private Label labelNoticia;
    @FXML private Label footer;


    private final Map<String, String> ligaPantallas = new HashMap<>();


    private final Map<String, String> seleccionPantallas = new HashMap<>();

    private ResourceBundle recursos;
    private Locale localeActual = new Locale("es", "ES");

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
        menuLigas.setText(recursos.getString("menu.ligas"));
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
        btnEquipo.setText(recursos.getString("btn.equipo"));
        btnClasificacion.setText(recursos.getString("btn.clasificacion"));


        //Footer
        footer.setText(recursos.getString("footer.copy"));

        //Labels
        labelNoticia.setText(recursos.getString("label.Noticia"));
        labelTitulo1.setText(recursos.getString("label.titulo1AUS"));
        labelTitulo2.setText(recursos.getString("label.titulo2AUS"));
        labelTitulo3.setText(recursos.getString("label.titulo3AUS"));
        labelNoticia1.setText(recursos.getString("label.noticia1AUS"));
        labelNoticia2.setText(recursos.getString("label.noticia2AUS"));
        labelNoticia3.setText(recursos.getString("label.noticia3AUS"));
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
        btnClasificacion.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Australia/PClasificacionAustralia.fxml"));
    }

    private void inicializarEquipos() {
        btnEquipo.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Australia/PEquiposAustralia.fxml"));
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
