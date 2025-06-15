package org.example.onside_fem.Selecciones;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.onside_fem.BBDD.JugadoraDAO;
import org.example.onside_fem.BBDD.JugadoraSeleccion;
import org.example.onside_fem.BBDD.Ranking;
import org.example.onside_fem.BBDD.RankingDAO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class ConCanadaP {
    @FXML
    private MenuItem volverItem;

    @FXML private MenuItem menuInicio;

    @FXML private MenuItem menuAlemania, menuAustralia, menuBrasil, menuCanada, menuColombia, menuEspana, menuUSA, menuFrancia, menuInglaterra, menuNigeria, menuNZelanda, menuSudafrica, menuSuecia;

    @FXML
    private Menu menuLigas;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private ComboBox<String> comboBoxIdiomas;

    @FXML
    private Hyperlink hyperlinkAyuda;

    private final Map<String, String> ligaPantallas = new HashMap<>();

    @FXML Label labelEntrenador, labelEquipacion, labelRanking, labelPalmares, labelConvocatoria, footer;

    private final Map<String, String> seleccionPantallas = new HashMap<>();

    @FXML private TableView<Ranking> tableRanking;
    @FXML private TableColumn<Ranking, String> colSeleccion;
    @FXML private TableColumn<Ranking, Integer> colNRanking;

    @FXML private ListView<String> listPorteras;
    @FXML private ListView<String> listDefensas;
    @FXML private ListView<String> listCentros;
    @FXML private ListView<String> listDelanteras;

    @FXML private Label lblNombre;
    @FXML private Label lblPosicion;
    @FXML private Label lblEquipo;
    @FXML private Label lblGoles;
    @FXML private Label lblPJugados;
    @FXML private Label lblImbatidas;
    @FXML private ImageView imgJugadora;

    @FXML private Tab tabPortera, tabCentro, tabDelantera, tabDefensas;

    private RankingDAO rankingDAO = new RankingDAO();

    private static final Map<String, String> mapaPosiciones = new HashMap<>();

    static {
        mapaPosiciones.put("PO", "Portera");
        mapaPosiciones.put("DF", "Defensas");
        mapaPosiciones.put("CC", "Centrocampistas");
        mapaPosiciones.put("DL", "Delantera");
    }

    private JugadoraDAO jugadoraDAO;

    private Locale localeActual;
    private ResourceBundle recursos;


    @FXML
    public void initialize() {
        comboBoxIdiomas.getItems().addAll("Español", "English");
        comboBoxIdiomas.setValue("Español");
        comboBoxIdiomas.setOnAction(e -> cambiarIdioma());
        cambiarIdioma(); // Para cargar por defecto
    }


    private void cambiarIdioma() {
        String idioma = comboBoxIdiomas.getValue();
        localeActual = idioma.equals("English") ? new Locale("en", "US") : new Locale("es", "ES");

        try {
            recursos = ResourceBundle.getBundle("idiomas.messages", localeActual);
            traducirUI();
            inicializarRutas();
            inicializarMenuInicio();
            inicializarLigas();
            inicializarSelecciones();
            inicializarRanking();

            if (jugadoraDAO == null) jugadoraDAO = new JugadoraDAO();
            cargarJugadores();
            hyperlinkAyuda.setOnAction(this::abrirAyuda);

        } catch (MissingResourceException e) {
            System.err.println("Archivo de idioma no encontrado.");
        }

        limpiarPanelJugadora(); // Por si acaso
    }

    /**
     * Limpia el panel de la informacion de la juagdora una vez que se cambie de idioma
     */
    private void limpiarPanelJugadora() {
        lblNombre.setText("");
        lblPosicion.setText("");
        lblEquipo.setText("");
        lblPJugados.setText("");
        lblGoles.setText("");
        lblImbatidas.setText("");
        imgJugadora.setImage(null);
    }

    private void traducirUI() {
        menuInicio.setText(recursos.getString("menu.inicio"));
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
        volverItem.setText(recursos.getString("menu.volver"));
        hyperlinkAyuda.setText(recursos.getString("menu.ayuda"));

        menuLigas.setText(recursos.getString("menu.ligas"));
        menuSelecciones.setText(recursos.getString("menu.selecciones"));
        volverItem.setText(recursos.getString("menu.volver"));
        hyperlinkAyuda.setText(recursos.getString("menu.ayuda"));

        colSeleccion.setText(recursos.getString("col.seleccion"));
        colNRanking.setText(recursos.getString("col.ranking"));

        tabPortera.setText(recursos.getString("tab.portera"));
        tabDefensas.setText(recursos.getString("tab.defensas"));
        tabCentro.setText(recursos.getString("tab.centrocampistas"));
        tabDelantera.setText(recursos.getString("tab.delantera"));

        lblNombre.setText(recursos.getString("jugadora.nombre"));
        lblPosicion.setText(recursos.getString("jugadora.posicion"));
        lblEquipo.setText(recursos.getString("jugadora.equipo"));
        lblGoles.setText(recursos.getString("jugadora.goles"));
        lblPJugados.setText(recursos.getString("jugadora.partidos"));
        lblImbatidas.setText(recursos.getString("jugadora.imbatidas"));

        labelEntrenador.setText(recursos.getString("label.entrenadorCA"));
        labelConvocatoria.setText(recursos.getString("label.convocatoria"));
        labelRanking.setText(recursos.getString("label.ranking"));
        labelPalmares.setText(recursos.getString("label.palmares"));
        labelEquipacion.setText(recursos.getString("label.equipacion"));
    }



    private void inicializarMenuInicio() {
        volverItem.setOnAction(e -> cargarPantalla("/org/example/onside_fem/PantallaPrincipal.fxml"));
    }

    /**
     * Inicializa los mapas de rutas FXML para ligas y selecciones, en función del idioma.
     */
    private void inicializarRutas() {
        ligaPantallas.clear();
        seleccionPantallas.clear();

        ligaPantallas.put("Finetwork Liga F", "/org/example/onside_fem/Espana/PPLigaEspanola.fxml");
        ligaPantallas.put("Liberty A-League", "/org/example/onside_fem/Australia/PPLigaAustraliana.fxml");
        ligaPantallas.put("Women's Super League", "/org/example/onside_fem/Inglaterra/PPLigaInglaterra.fxml");
        ligaPantallas.put("Arkema Premiere League", "/org/example/onside_fem/Francia/PPLigaFrancia.fxml");
        ligaPantallas.put("Yogibo WE League", "/org/example/onside_fem/Japon/PPLigaJaponesa.fxml");
        ligaPantallas.put("National Super League", "/org/example/onside_fem/EEUU/PPLigaEEUU.fxml");

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

    private void inicializarRanking() {
        colSeleccion.setCellValueFactory(new PropertyValueFactory<>("nombre_seleccion"));
        colNRanking.setCellValueFactory(new PropertyValueFactory<>("ranking_fifa"));

        List<Ranking> listaRanking = rankingDAO.obtenerRanking();


        Map<String, String> traduccionesPaises = new HashMap<>();
        traduccionesPaises.put("Alemania", recursos.getString("pais.Alemania"));
        traduccionesPaises.put("Australia", recursos.getString("pais.Australia"));
        traduccionesPaises.put("Brasil", recursos.getString("pais.Brasil"));
        traduccionesPaises.put("Canada", recursos.getString("pais.Canada"));
        traduccionesPaises.put("Colombia", recursos.getString("pais.Colombia"));
        traduccionesPaises.put("España", recursos.getString("pais.España"));
        traduccionesPaises.put("Estados Unidos", recursos.getString("pais.Estados"));
        traduccionesPaises.put("Francia", recursos.getString("pais.Francia"));
        traduccionesPaises.put("Inglaterra", recursos.getString("pais.Inglaterra"));
        traduccionesPaises.put("Nigeria", recursos.getString("pais.Nigeria"));
        traduccionesPaises.put("Nueva Zelanda", recursos.getString("pais.Nueva"));
        traduccionesPaises.put("Sudafrica", recursos.getString("pais.Sudafrica"));
        traduccionesPaises.put("Suecia", recursos.getString("pais.Suecia"));

        for (Ranking r : listaRanking) {
            String pais = r.getNombre_seleccion().trim();
            if (traduccionesPaises.containsKey(pais)) {
                r.setNombre_seleccion(traduccionesPaises.get(pais));
            }
        }

        tableRanking.getItems().setAll(listaRanking);
    }


    private void cargarJugadores() {
        cargarListaJugadoras("PO", listPorteras);
        cargarListaJugadoras("DF", listDefensas);
        cargarListaJugadoras("CC", listCentros);
        cargarListaJugadoras("DL", listDelanteras);
    }

    private void cargarListaJugadoras(String posicion, ListView<String> listView) {
        // Limpiar la lista antes de cargar
        listView.getItems().clear();

        List<JugadoraSeleccion> jugadoras = jugadoraDAO.obtenerJugadoresPorSeleccionYPosicion("Canada", posicion);
        for (JugadoraSeleccion j : jugadoras) {
            listView.getItems().add(j.getNombre_jugadora());
        }

        // El listener solo debe añadirse una vez, no cada vez que cargas la lista
        if (listView.getProperties().get("listenerAdded") == null) {
            listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    JugadoraSeleccion j = jugadoraDAO.obtenerJugadoraSeleccionPorNombre("Canada", newVal);
                    mostrarInfoEnPanel(j, "Canada");
                }
            });
            listView.getProperties().put("listenerAdded", true);
        }
    }


    private void mostrarInfoEnPanel(JugadoraSeleccion jugadora, String seleccion) {
        lblNombre.setText(recursos.getString("jugadora.nombre") + ": " + jugadora.getNombre_jugadora());
        lblPosicion.setText(recursos.getString("jugadora.posicion") + ": " + obtenerNombrePosicion(jugadora.getPosicion()));
        lblPJugados.setText(recursos.getString("jugadora.partidos") + ": " + jugadora.getPartidos_juagados());
        lblGoles.setText(recursos.getString("jugadora.goles") + ": " + jugadora.getGoles());
        lblImbatidas.setText(recursos.getString("jugadora.imbatidas") + ": " + jugadora.getImbatidas());

        lblNombre.setVisible(true);
        lblNombre.setManaged(true);

        lblEquipo.setVisible(true);
        lblEquipo.setManaged(true);

        lblPosicion.setVisible(true);
        lblPosicion.setManaged(true);

        lblPJugados.setVisible(true);
        lblPJugados.setManaged(true);

        lblGoles.setVisible(true);
        lblGoles.setManaged(true);

        lblImbatidas.setVisible(true);
        lblImbatidas.setManaged(true);

        String baseRuta = "/Imagenes/Selecciones/Jugadoras/Canada/" + jugadora.getNombre_jugadora();
        InputStream inputStream = getClass().getResourceAsStream(baseRuta + ".png");

        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("/Imagenes/default_jugadora.png");
        }

        if (inputStream != null) {
            imgJugadora.setImage(new Image(inputStream));
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
            File ayudaHTML = new File("org/example/onside_fem/ayuda.html");
            if (ayudaHTML.exists()) {
                Desktop.getDesktop().browse(ayudaHTML.toURI());
            } else {
                System.err.println("Archivo de ayuda no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String obtenerNombrePosicion(String abreviatura) {
        try {
            return recursos.getString("pos." + abreviatura);
        } catch (MissingResourceException e) {
            return abreviatura;
        }
    }
}
