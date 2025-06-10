package org.example.onside_fem.Otros;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class ConPantallaPrincipal {
    @FXML
    private Menu menuLigas;

    @FXML private MenuItem menuAlemania, menuAustralia, menuBrasil, menuCanada, menuColombia, menuEspana, menuUSA, menuFrancia, menuInglaterra, menuNigeria, menuNZelanda, menuSudafrica, menuSuecia;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private ComboBox<String> comboBoxIdiomas;

    @FXML
    private Hyperlink hyperlinkAyuda;

    private final Map<String, String> ligaPantallas = new HashMap<>();


    private final Map<String, String> seleccionPantallas = new HashMap<>();

    private ResourceBundle recursos;
    private Locale localeActual;

    @FXML
    private Label footer;

    @FXML
    private Label labelEventos,labelEuro,labelEuroF,labelSorteoE,labelAE,labelBE;

    @FXML
    private Label labelCE,labelDE,labelAmerica,labelFechaA,labelSA,labelAA,labelAB;

    /**
     * Metodo invocado automáticamente por JavaFX tras la carga del FXML.
     * Inicializa los elementos del comboBox de idiomas y establece el idioma por defecto.
     */
    @FXML
    public void initialize() {
        comboBoxIdiomas.getItems().addAll("Español", "English");
        comboBoxIdiomas.setValue("Español");
        comboBoxIdiomas.setOnAction(e -> cambiarIdioma());
        cambiarIdioma();
    }

    private void cambiarIdioma() {
        String idioma = comboBoxIdiomas.getValue();
        localeActual = idioma.equals("English") ? new Locale("en", "US") : new Locale("es", "ES");

        try {
            recursos = ResourceBundle.getBundle("idiomas.messages", localeActual);
            traducirUI();
            inicializarRutas();
            inicializarLigas();
            inicializarSelecciones();
            hyperlinkAyuda.setOnAction(this::abrirAyuda);
        }catch (MissingResourceException e) {
            System.err.println("Archivo de idioma no encontrado.");
        }
    }

    /**
     * Traduce todos los textos visibles de la interfaz usando el archivo de idioma seleccionado.
     */
    private void traducirUI() {
        menuLigas.setText(recursos.getString("menu.ligas"));
        menuSelecciones.setText(recursos.getString("menu.selecciones"));
        menuAlemania.setText(recursos.getString("menu.alemania"));
        menuAustralia.setText(recursos.getString("menu.australia"));
        menuBrasil.setText(recursos.getString("menu.brasil"));
        menuCanada.setText(recursos.getString("menu.canada"));
        menuColombia.setText(recursos.getString("menu.colombia"));
        menuEspana.setText(recursos.getString("menu.espana"));
        menuUSA.setText(recursos.getString("menu.unidos"));
        menuFrancia.setText(recursos.getString("menu.francia"));
        menuInglaterra.setText(recursos.getString("menu.inglaterra"));
        menuNigeria.setText(recursos.getString("menu.nigeria"));
        menuNZelanda.setText(recursos.getString("menu.nzelanda"));
        menuSudafrica.setText(recursos.getString("menu.sudafrica"));
        menuSuecia.setText(recursos.getString("menu.suecia"));
        hyperlinkAyuda.setText(recursos.getString("menu.ayuda"));

        labelEventos.setText(recursos.getString("label.eventos"));
        labelEuro.setText(recursos.getString("label.tituloE"));
        labelEuroF.setText(recursos.getString("label.fechaE"));
        labelSorteoE.setText(recursos.getString("label.sorteoE"));
        labelAE.setText(recursos.getString("label.GrupoAE"));
        labelBE.setText(recursos.getString("label.GrupoBE"));
        labelCE.setText(recursos.getString("label.GrupoCE"));
        labelDE.setText(recursos.getString("label.GrupoDE"));
        labelAmerica.setText(recursos.getString("label.tituloA"));
        labelFechaA.setText(recursos.getString("label.fechaA"));
        labelSA.setText(recursos.getString("label.sorteoA"));
        labelAA.setText(recursos.getString("label.GrupoAA"));
        labelAB.setText(recursos.getString("label.GrupoBA"));

        footer.setText(recursos.getString("footer.copy"));
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
        seleccionPantallas.put("Australia", "/org/example/onside_fem/Selecciones/PAustralia.fxml");
        seleccionPantallas.put("Brasil", "/org/example/onside_fem/Selecciones/PBrasil.fxml");
        seleccionPantallas.put("Canada", "/org/example/onside_fem/Selecciones/PCanada.fxml");
        seleccionPantallas.put("Colombia", "/org/example/onside_fem/Selecciones/PColombia.fxml");
        seleccionPantallas.put("España", "/org/example/onside_fem/Selecciones/PEspana.fxml");
        seleccionPantallas.put("Estados Unidos", "/org/example/onside_fem/Selecciones/PEEUU.fxml");
        seleccionPantallas.put("Francia", "/org/example/onside_fem/Selecciones/PFrancia.fxml");
        seleccionPantallas.put("Inglaterra", "/org/example/onside_fem/Selecciones/PInglaterra.fxml");
        seleccionPantallas.put("Nigeria", "/org/example/onside_fem/Selecciones/PNigeria.fxml");
        seleccionPantallas.put("Nueva Zelanda", "/org/example/onside_fem/Selecciones/PNuevaZelanda.fxml");
        seleccionPantallas.put("Sudafrica", "/org/example/onside_fem/Selecciones/PSudafrica.fxml");
        seleccionPantallas.put("Suecia", "/org/example/onside_fem/Selecciones/PSuecia.fxml");
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
            URL ayudaURL = getClass().getResource("/org/example/onside_fem/ayuda.html");
            if (ayudaURL != null) {
                Desktop.getDesktop().browse(ayudaURL.toURI());
            } else {
                System.err.println("Archivo de ayuda no encontrado.");
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
