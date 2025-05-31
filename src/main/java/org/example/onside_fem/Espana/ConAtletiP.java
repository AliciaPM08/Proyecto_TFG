package org.example.onside_fem.Espana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.onside_fem.BBDD.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ConAtletiP {
    @FXML
    private MenuItem volverItem;

    @FXML
    private Menu menuLigas;

    @FXML
    private Menu menuSelecciones;

    @FXML
    private MenuItem menuInicio;

    @FXML
    private ComboBox<String> comboBoxIdiomas;

    @FXML
    private Hyperlink hyperlinkAyuda;

    @FXML
    private Button btnEquipo;

    @FXML
    private Button btnClasifiacion;

    @FXML private MenuItem menuAlemania;
    @FXML private MenuItem menuAustralia;
    @FXML private MenuItem menuBrasil;
    @FXML private MenuItem menuCanada;
    @FXML private MenuItem menuColombia;
    @FXML private MenuItem menuEspana;
    @FXML private MenuItem menuUSA;
    @FXML private MenuItem menuFrancia;
    @FXML private MenuItem menuInglaterra;
    @FXML private MenuItem menuNigeria;
    @FXML private MenuItem menuNZelanda;
    @FXML private MenuItem menuSudafrica;
    @FXML private MenuItem menuSuecia;

    private final Map<String, String> ligaPantallas = new HashMap<>();


    private final Map<String, String> seleccionPantallas = new HashMap<>();

    @FXML private TableView<Estadisticas> tableEstadisticas;
    @FXML private TableColumn<Estadisticas, Integer> colGAnotados;
    @FXML private TableColumn<Estadisticas, Integer> colGRecibidos;
    @FXML private TableColumn<Estadisticas, Integer> colTAmarillas;
    @FXML private TableColumn<Estadisticas, Integer> colTRojas;

    @FXML private ListView<String> listPorteras;
    @FXML private ListView<String> listDefensas;
    @FXML private ListView<String> listCentros;
    @FXML private ListView<String> listDelanteras;

    @FXML private Label lblNombre;
    @FXML private Label lblPosicion;
    @FXML private Label lblEquipo;
    @FXML private Label lblFecha;
    @FXML private ImageView imgJugadora;


    @FXML private Tab tabPortera;
    @FXML private Tab tabCentro;
    @FXML private Tab tabDelantera;
    @FXML private Tab tabDefensas;

    @FXML private Label labelPlantilla;
    @FXML private Label labelEstadisticas;
    @FXML private Label labelPalmares;
    @FXML private Label labelEquipaciones;

    @FXML private Label labelEstadio;
    @FXML private Label labelEntrenador;
    @FXML private Label labelFundacion;
    @FXML private Label labelSupercopa;
    @FXML private Label labelCopaPrimera;

    @FXML private Label footer;


    private static final Map<String, String> mapaPosiciones = new HashMap<>();

    static {
        mapaPosiciones.put("PO", "Portera");
        mapaPosiciones.put("DF", "Defensas");
        mapaPosiciones.put("CC", "Centrocampistas");
        mapaPosiciones.put("DL", "Delantera");
    }

    private JugadoraDAO jugadoraDAO;

    private String seleccionActualDeLaJugadora;

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
        inicializarEquipos();
        inicializarClasifiacion();
        inicializarTablaEstadisticas();
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
        String idiomaSeleccionado = comboBoxIdiomas.getValue();
        if (idiomaSeleccionado.equals("Inglés")) {
            localeActual = new Locale("en", "US");
        } else {
            localeActual = new Locale("es", "ES");
        }

        try {
            recursos = ResourceBundle.getBundle("idiomas.messages", localeActual);
            traducirUI();

            // ACTUALIZAR la información de la jugadora seleccionada al cambiar idioma
            String jugadoraSeleccionada = obtenerJugadoraSeleccionada();
            if (jugadoraSeleccionada != null) {
                Jugadora j = jugadoraDAO.obtenerJugadoraPorNombre("Atletico Madrid", jugadoraSeleccionada);
                mostrarInfoEnPanel(j, "Liga Española");
            }

        } catch (MissingResourceException e) {
            System.err.println("Archivo de idioma no encontrado.");
        }
    }

    private String obtenerJugadoraSeleccionada() {
        String seleccionada = listPorteras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) seleccionada = listDefensas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) seleccionada = listCentros.getSelectionModel().getSelectedItem();
        if (seleccionada == null) seleccionada = listDelanteras.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            return seleccionada.replace(" ⭐", "").trim();
        }
        return null;
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
        menuUSA.setText(recursos.getString("menu.unidos"));
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
        btnClasifiacion.setText(recursos.getString("btn.clasificacion"));

        // Columnas de tabla
        colGAnotados.setText(recursos.getString("stats.goles_anotados"));
        colGRecibidos.setText(recursos.getString("stats.goles_recibidos"));
        colTAmarillas.setText(recursos.getString("stats.amarillas"));
        colTRojas.setText(recursos.getString("stats.rojas"));

        // Tabs
        tabPortera.setText(recursos.getString("tab.portera"));
        tabDefensas.setText(recursos.getString("tab.defensas"));
        tabCentro.setText(recursos.getString("tab.centrocampistas"));
        tabDelantera.setText(recursos.getString("tab.delantera"));

        //Labels generales
        labelPlantilla.setText(recursos.getString("plantilla.label"));
        labelEstadisticas.setText(recursos.getString("estadisticas.label"));
        labelPalmares.setText(recursos.getString("palmares.label"));
        labelEquipaciones.setText(recursos.getString("equipaciones.label"));

        //Footer
        footer.setText(recursos.getString("footer.copy"));

        //Info equipo
        labelCopaPrimera.setText(recursos.getString("equipoAtletico.titulo1"));
        labelSupercopa.setText(recursos.getString("equipoAtletico.titulo3"));
        labelEntrenador.setText(recursos.getString("equipoAtletico.entrenador"));
        labelEstadio.setText(recursos.getString("equipoAtletico.estadio"));
        labelFundacion.setText(recursos.getString("equipoAtletico.fundacion"));
    }

    private String traducirPosicion(String posicionOriginal) {
        switch (posicionOriginal.toLowerCase()) {
            case "PO":
                return recursos.getString("portera");
            case "DF":
                return recursos.getString("defensa");
            case "CC":
                return recursos.getString("centrocampista");
            case "DL":
                return recursos.getString("delantera");
            default:
                return posicionOriginal; // Por si acaso hay algo inesperado
        }
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

    private void inicializarEquipos() {
        btnEquipo.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Espana/PEquiposEspana.fxml"));
    }

    private void inicializarClasifiacion() {
        btnClasifiacion.setOnAction(e -> cargarPantalla("/org/example/onside_fem/Espana/PClasificacionEspana.fxml"));
    }

    private void inicializarTablaEstadisticas() {
        colGAnotados.setCellValueFactory(new PropertyValueFactory<>("golesAnotados"));
        colGRecibidos.setCellValueFactory(new PropertyValueFactory<>("golesRecibidos"));
        colTAmarillas.setCellValueFactory(new PropertyValueFactory<>("tarjetasAmarillas"));
        colTRojas.setCellValueFactory(new PropertyValueFactory<>("tarjetasRojas"));

        Estadisticas estadisticas = new EstadisticasDAO().obtenerEstadisticas("Atletico Madrid", "Finetwork Liga F");

        if (estadisticas != null) {
            tableEstadisticas.getItems().setAll(estadisticas);
        } else {
            System.err.println("No se encontraron estadísticas.");
        }
    }

    private void cargarJugadores() {
        cargarListaJugadoras("PO", listPorteras);
        cargarListaJugadoras("DF", listDefensas);
        cargarListaJugadoras("CC", listCentros);
        cargarListaJugadoras("DL", listDelanteras);
    }

    private void cargarListaJugadoras(String posicion, ListView<String> listView) {
        List<Jugadora> jugadoras = jugadoraDAO.obtenerJugadoresPorEquipoYPosicion("Atletico Madrid", posicion);

        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String nombre, boolean empty) {
                super.updateItem(nombre, empty);
                if (empty || nombre == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String nombreLimpio = nombre.replace(" ⭐", "").trim();
                    boolean tieneEstrella = jugadoraDAO.juegaEnSeleccion(nombreLimpio);

                    Label label = new Label(nombreLimpio);
                    label.setStyle("-fx-font-size: 13px;");

                    if (tieneEstrella) {
                        ImageView estrella = new ImageView(new Image(getClass().getResourceAsStream("/Imagenes/star.png")));
                        estrella.setFitWidth(15);
                        estrella.setFitHeight(15);
                        estrella.setCursor(Cursor.HAND);

                        // Acción para ir a pantalla de selección al clickar la estrella
                        estrella.setOnMouseClicked(event -> {
                            JugadoraSeleccion js = jugadoraDAO.obtenerJugadoraSeleccionPorNombre(null, nombreLimpio);
                            if (js != null) {
                                String seleccion = js.getNombre_seleccion();
                                System.out.println("Seleccion obtenida para jugadora '" + nombreLimpio + "': '" + seleccion + "'");
                                String ruta = seleccionPantallas.get(seleccion);
                                System.out.println("Ruta obtenida para seleccion '" + seleccion + "': " + ruta);
                                if (ruta != null) {
                                    URL recurso = getClass().getResource(ruta);
                                    if (recurso != null) {
                                        cargarPantalla(ruta);
                                    } else {
                                        System.err.println("No se encontró recurso para ruta: " + ruta);
                                    }
                                } else {
                                    System.err.println("No se encontró ruta para la selección: " + seleccion);
                                }
                            } else {
                                System.err.println("No se encontró la selección para la jugadora: " + nombreLimpio);
                            }
                            event.consume();
                        });


                        HBox hBox = new HBox(10, label, estrella);
                        setGraphic(hBox);
                    } else {
                        // Si no tiene estrella, solo selecciona el item normal
                        label.setOnMouseClicked(event -> listView.getSelectionModel().select(getIndex()));
                        setGraphic(label);
                    }
                }
            }
        });

        listView.getItems().clear(); // Evita duplicados
        for (Jugadora j : jugadoras) {
            listView.getItems().add(j.getNombre());
        }

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String nombreLimpio = newVal.replace(" ⭐", "").trim();
                Jugadora j = jugadoraDAO.obtenerJugadoraPorNombre("Atletico Madrid", nombreLimpio);
                mostrarInfoEnPanel(j, "Liga Española");
            }
        });
    }


    private void mostrarInfoEnPanel(Jugadora jugadora, String liga) {
        lblNombre.setText(recursos.getString("nombre") + ": " + jugadora.getNombre());

        String posicionTraducida = traducirPosicion(jugadora.getPosicion());
        lblPosicion.setText(recursos.getString("posicion") + ": " + posicionTraducida);

        lblEquipo.setText(recursos.getString("fecha") + ": " + jugadora.getNombre_equipo());
        lblFecha.setText(recursos.getString("equipo_label") + ": " + jugadora.getFecha());

        lblNombre.setVisible(true);
        lblNombre.setManaged(true);

        lblPosicion.setVisible(true);
        lblPosicion.setManaged(true);

        lblEquipo.setVisible(true);
        lblEquipo.setManaged(true);

        lblFecha.setVisible(true);
        lblFecha.setManaged(true);

        // Imagen
        String rutaImagen = "/Imagenes/Liga_Española/Jugadoras/Atlético de Madrid/" + jugadora.getNombre() + ".jpg";
        InputStream inputStream = getClass().getResourceAsStream(rutaImagen);
        if (inputStream == null) {
            inputStream = getClass().getResourceAsStream("/Imagenes/default_jugadora.png");
        }
        if (inputStream != null) {
            imgJugadora.setImage(new Image(inputStream));
        }

        // Comprobar si juega en selección
        if (jugadoraDAO.juegaEnSeleccion(jugadora.getNombre())) {
            JugadoraSeleccion js = jugadoraDAO.obtenerJugadoraSeleccionPorNombre(null, jugadora.getNombre());
            if (js != null) {
                seleccionActualDeLaJugadora = js.getNombre_seleccion();
            }
        } else {
            seleccionActualDeLaJugadora = null;
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
