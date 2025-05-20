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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConNigeriaP {
    @FXML
    private MenuItem volverItem;

    @FXML
    private Menu menuLigas;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private ComboBox<String> comboBoxIdiomas;

    @FXML
    private Hyperlink hyperlinkAyuda;

    private final Map<String, String> ligaPantallas = new HashMap<>();


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

    private RankingDAO rankingDAO = new RankingDAO();

    private static final Map<String, String> mapaPosiciones = new HashMap<>();

    static {
        mapaPosiciones.put("PO", "Portera");
        mapaPosiciones.put("DF", "Defensas");
        mapaPosiciones.put("CC", "Centrocampistas");
        mapaPosiciones.put("DL", "Delantera");
    }

    private JugadoraDAO jugadoraDAO;

    @FXML
    public void initialize() {
        inicializarIdioma();
        inicializarRutas();
        inicializarMenuInicio();
        inicializarLigas();
        inicializarSelecciones();
        inicializarRanking();
        jugadoraDAO = new JugadoraDAO();
        cargarJugadores();
        hyperlinkAyuda.setOnAction(this::abrirAyuda);
    }

    private void inicializarIdioma() {
        comboBoxIdiomas.getItems().addAll("Español", "Inglés");
        comboBoxIdiomas.setValue("Español");
        comboBoxIdiomas.setOnAction(e -> cambiarIdioma());
    }

    private void cambiarIdioma() {
        String idioma = comboBoxIdiomas.getValue();
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

    private void inicializarRanking() {
        colSeleccion.setCellValueFactory(new PropertyValueFactory<>("nombre_seleccion"));
        colNRanking.setCellValueFactory(new PropertyValueFactory<>("ranking_fifa"));

        List<Ranking> listaRanking = rankingDAO.obtenerRanking();
        tableRanking.getItems().setAll(listaRanking);
    }

    private void cargarJugadores() {
        cargarListaJugadoras("PO", listPorteras);
        cargarListaJugadoras("DF", listDefensas);
        cargarListaJugadoras("CC", listCentros);
        cargarListaJugadoras("DL", listDelanteras);
    }

    private void cargarListaJugadoras(String posicion, ListView<String> listView) {
        List<JugadoraSeleccion> jugadoras = jugadoraDAO.obtenerJugadoresPorSeleccionYPosicion("Nigeria", posicion);
        for (JugadoraSeleccion j : jugadoras) {
            listView.getItems().add(j.getNombre_jugadora());
        }

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                JugadoraSeleccion j = jugadoraDAO.obtenerJugadoraSeleccionPorNombre("Nigeria", newVal);
                mostrarInfoEnPanel(j, "Nigeria");
            }
        });
    }

    private void mostrarInfoEnPanel(JugadoraSeleccion jugadora, String seleccion) {
        lblNombre.setText("Nombre: " + jugadora.getNombre_jugadora());
        lblEquipo.setText("Seleccion: " + jugadora.getNombre_seleccion());
        lblPosicion.setText("Posición: " + obtenerNombrePosicion(jugadora.getPosicion()));
        lblPJugados.setText("Partidos Jugados: " + jugadora.getPartidos_juagados());
        lblGoles.setText("Goles:  " + jugadora.getGoles());
        lblImbatidas.setText("Porterias a cero: " + jugadora.getImbatidas());

        String baseRuta = "/Imagenes/Selecciones/Jugadoras/Nigeria/" + jugadora.getNombre_jugadora();
        InputStream inputStream = getClass().getResourceAsStream(baseRuta + ".jpg");

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

    private String obtenerNombrePosicion(String abreviatura) {
        return mapaPosiciones.getOrDefault(abreviatura, "Desconocido");
    }
}
