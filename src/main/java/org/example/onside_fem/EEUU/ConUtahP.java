package org.example.onside_fem.EEUU;

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

public class ConUtahP {
    @FXML private MenuItem volverItem, menuAlemania, menuAustralia, menuBrasil, menuCanada, menuColombia, menuEspana, menuUSA, menuFrancia, menuInglaterra, menuNigeria, menuNZelanda, menuSudafrica, menuSuecia;
    @FXML private Menu menuLigas, menuSelecciones;
    @FXML private MenuItem menuInicio;
    @FXML private ComboBox<String> comboBoxIdiomas;
    @FXML private Hyperlink hyperlinkAyuda;
    @FXML private Button btnEquipo, btnClasifiacion;

    @FXML private TableView<Estadisticas> tableEstadisticas;
    @FXML private TableColumn<Estadisticas, Integer> colGAnotados, colGRecibidos, colTAmarillas, colTRojas;

    @FXML private ListView<String> listPorteras, listDefensas, listCentros, listDelanteras;

    @FXML private Label lblNombre, lblPosicion, lblEquipo, lblFecha;
    @FXML private ImageView imgJugadora;

    @FXML private Tab tabPortera, tabCentro, tabDelantera, tabDefensas;

    @FXML private Label labelPlantilla, labelEstadisticas, labelPalmares, labelEquipaciones;
    @FXML private Label labelEstadio, labelEntrenador, labelFundacion;
    @FXML private Label footer;

    private final Map<String, String> ligaPantallas = new HashMap<>();
    private final Map<String, String> seleccionPantallas = new HashMap<>();

    private JugadoraDAO jugadoraDAO;
    private String seleccionActualDeLaJugadora;
    private ResourceBundle recursos;
    private Locale localeActual;

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

    /**
     * Cambia el idioma de la interfaz según la opción seleccionada en el ComboBox.
     * Carga los recursos de idioma apropiados y reinicializa todos los textos de la UI.
     */
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
            inicializarEquipos();
            inicializarClasifiacion();
            inicializarTablaEstadisticas();

            if (jugadoraDAO == null) jugadoraDAO = new JugadoraDAO();
            cargarJugadores();
            hyperlinkAyuda.setOnAction(this::abrirAyuda);

        } catch (MissingResourceException e) {
            System.err.println("Archivo de idioma no encontrado.");
        }
    }

    /**
     * Traduce todos los textos visibles de la interfaz usando el archivo de idioma seleccionado.
     */
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

        btnEquipo.setText(recursos.getString("btn.equipo"));
        btnClasifiacion.setText(recursos.getString("btn.clasificacion"));

        colGAnotados.setText(recursos.getString("stats.goles_anotados"));
        colGRecibidos.setText(recursos.getString("stats.goles_recibidos"));
        colTAmarillas.setText(recursos.getString("stats.amarillas"));
        colTRojas.setText(recursos.getString("stats.rojas"));

        tabPortera.setText(recursos.getString("tab.portera"));
        tabDefensas.setText(recursos.getString("tab.defensas"));
        tabCentro.setText(recursos.getString("tab.centrocampistas"));
        tabDelantera.setText(recursos.getString("tab.delantera"));

        labelPlantilla.setText(recursos.getString("plantilla.label"));
        labelEstadisticas.setText(recursos.getString("estadisticas.label"));
        labelPalmares.setText(recursos.getString("palmares.label"));
        labelEquipaciones.setText(recursos.getString("equipaciones.label"));

        labelEntrenador.setText(recursos.getString("equipoUtha.entrenador"));
        labelEstadio.setText(recursos.getString("equipoUtha.estadio"));
        labelFundacion.setText(recursos.getString("equipoUtha.fundacion"));
        footer.setText(recursos.getString("footer.copy"));
    }

    /**
     * Inicializa la acción del menú "Volver al inicio".
     */
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

    /**
     * Asocia eventos a los ítems del menú de ligas, que cargan su respectiva pantalla FXML.
     */
    private void inicializarLigas() {
        menuLigas.getItems().forEach(item -> item.setOnAction(e -> cargarPantalla(ligaPantallas.get(item.getText()))));
    }

    /**
     * Asocia eventos a los ítems del menú de selecciones, que cargan su respectiva pantalla FXML.
     */
    private void inicializarSelecciones() {
        menuSelecciones.getItems().forEach(item -> item.setOnAction(e -> cargarPantalla(seleccionPantallas.get(item.getText()))));
    }

    /**
     * Asigna una acción al botón de ver equipos.
     */
    private void inicializarEquipos() {
        btnEquipo.setOnAction(e -> cargarPantalla("/org/example/onside_fem/EEUU/PEquiposEEUU.fxml"));
    }

    /**
     * Asigna una acción al botón de ver clasificación.
     */
    private void inicializarClasifiacion() {
        btnClasifiacion.setOnAction(e -> cargarPantalla("/org/example/onside_fem/EEUU/PClasificacionEEUU.fxml"));
    }

    /**
     * Inicializa la tabla de estadísticas con los datos del Atlético de Madrid.
     */
    private void inicializarTablaEstadisticas() {
        colGAnotados.setCellValueFactory(new PropertyValueFactory<>("golesAnotados"));
        colGRecibidos.setCellValueFactory(new PropertyValueFactory<>("golesRecibidos"));
        colTAmarillas.setCellValueFactory(new PropertyValueFactory<>("tarjetasAmarillas"));
        colTRojas.setCellValueFactory(new PropertyValueFactory<>("tarjetasRojas"));

        Estadisticas estadisticas = new EstadisticasDAO().obtenerEstadisticas("Royals", "National Womens Soccer League");
        if (estadisticas != null) tableEstadisticas.getItems().setAll(estadisticas);
    }

    /**
     * Carga todas las listas de jugadoras por posición y las muestra en los ListView correspondientes.
     */
    private void cargarJugadores() {
        cargarListaJugadoras("PO", listPorteras);
        cargarListaJugadoras("DF", listDefensas);
        cargarListaJugadoras("CC", listCentros);
        cargarListaJugadoras("DL", listDelanteras);
    }

    /**
     * Carga y muestra las jugadoras de una posición específica en un ListView.
     * Añade un icono de estrella si la jugadora forma parte de una selección.
     *
     * @param posicion Código de la posición (PO, DF, CC, DL)
     * @param listView ListView donde se mostrarán los nombres
     */
    private void cargarListaJugadoras(String posicion, ListView<String> listView) {
        List<Jugadora> jugadoras = jugadoraDAO.obtenerJugadoresPorEquipoYPosicion("Royals", posicion);

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
                Jugadora j = jugadoraDAO.obtenerJugadoraPorNombre("Royals", nombreLimpio);
                mostrarInfoEnPanel(j, "Liga Estadonudense");
            }
        });
    }

    /**
     * Traduce el nombre de una posición según el idioma seleccionado.
     *
     * @param posicion Posición en texto
     * @return Nombre traducido de la posición
     */
    private String traducirPosicion(String posicion) {
        switch (posicion.toLowerCase()) {
            case "portera": return recursos.getString("pos_portera");
            case "defensa": return recursos.getString("pos_defensa");
            case "centrocampista": return recursos.getString("pos_centrocampista");
            case "delantera": return recursos.getString("pos_delantera");
            default: return posicion;
        }
    }

    /**
     * Muestra en el panel derecho los detalles de una jugadora seleccionada.
     *
     * @param jugadora Jugadora de la que mostrar datos
     * @param liga     Liga a la que pertenece
     */
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
        String rutaImagen = "/Imagenes/Liga Estadunidense/Jugadoras/Royals/" + jugadora.getNombre() + ".jpg";
        InputStream inputStream = getClass().getResourceAsStream(rutaImagen);
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

    /**
     * Carga una nueva pantalla FXML en la ventana actual.
     *
     * @param rutaFXML Ruta del archivo FXML a cargar
     */
    private void cargarPantalla(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            loader.setResources(recursos);
            Pane root = loader.load();

            Stage stage = (Stage) comboBoxIdiomas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);

        } catch (IOException e) {
            System.err.println("Error al cargar: " + rutaFXML);
            e.printStackTrace();
        }
    }

    /**
     * Abre el archivo de ayuda HTML en el navegador predeterminado del sistema.
     *
     * @param event Evento de acción al hacer clic en el hyperlink de ayuda
     */
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
