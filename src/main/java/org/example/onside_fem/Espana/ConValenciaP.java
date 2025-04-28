package org.example.onside_fem.Espana;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.onside_fem.BBDD.Estadisticas;
import org.example.onside_fem.BBDD.EstadisticasDAO;
import org.example.onside_fem.BBDD.Jugadora;
import org.example.onside_fem.BBDD.JugadoraDAO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConValenciaP {
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

    @FXML
    private Button btnEquipo;

    @FXML
    private Button btnClasifiacion;


    private final Map<String, String> ligaPantallas = new HashMap<>();


    private final Map<String, String> seleccionPantallas = new HashMap<>();

    @FXML private TableView<Estadisticas> tableEstadisticas;
    @FXML private TableColumn<Estadisticas, Integer> colGAnotados;
    @FXML private TableColumn<Estadisticas, Integer> colGRecibidos;
    @FXML private TableColumn<Estadisticas, Integer> colTAmarillas;
    @FXML private TableColumn<Estadisticas, Integer> colTRojas;

    private static final Map<String, String> mapaPosiciones = new HashMap<>();

    static {
        mapaPosiciones.put("PO", "Portera");
        mapaPosiciones.put("DF", "Defensas");
        mapaPosiciones.put("CC", "Centrocampistas");
        mapaPosiciones.put("DL", "Delantera");
    }

    @FXML
    private Tab tabPortera;
    @FXML
    private Tab tabDefensas;
    @FXML
    private Tab tabCentro;
    @FXML
    private Tab tabDelantera;

    private JugadoraDAO jugadoraDAO;

    @FXML
    public void initialize() {
        inicializarIdioma();
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

        Estadisticas estadisticas = new EstadisticasDAO().obtenerEstadisticas("Valencia", "Finetwork Liga F");

        if (estadisticas != null) {
            tableEstadisticas.getItems().setAll(estadisticas);
        } else {
            System.err.println("No se encontraron estadísticas.");
        }
    }

    private void cargarJugadores() {
        cargarJugadoresEnTab("PO", tabPortera); // "PO" para porteras
        cargarJugadoresEnTab("DF", tabDefensas); // "DE" para defensas
        cargarJugadoresEnTab("CC", tabCentro); // "CC" para centrocampistas
        cargarJugadoresEnTab("DL", tabDelantera); // "DL" para delantera
    }

    private void cargarJugadoresEnTab(String abreviaturaPosicion, Tab tab) {
        List<Jugadora> jugadores = jugadoraDAO.obtenerJugadoresPorEquipoYPosicion("Valencia", abreviaturaPosicion);
        VBox vbox = new VBox(10);

        for (Jugadora jugadora : jugadores) {
            Button btn = new Button(jugadora.getNombre());
            btn.setOnAction(e -> mostrarInfoJugadora(jugadora, "Liga Española")); // Puedes hacerlo variable
            vbox.getChildren().add(btn);
        }

        ScrollPane scroll = new ScrollPane(vbox);
        tab.setContent(scroll);
    }

    private void mostrarInfoJugadora(Jugadora jugadora, String liga) {
        Stage infoStage = new Stage();
        infoStage.setTitle(jugadora.getNombre());
        infoStage.setResizable(false);

        // Estilos personalizados para los textos
        Label nombre = new Label("Nombre: " + jugadora.getNombre());
        nombre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2a2a2a;");

        Label posicion = new Label("Posición: " + obtenerNombrePosicion(jugadora.getPosicion()));
        posicion.setStyle("-fx-font-size: 14px; -fx-font-weight: normal; -fx-text-fill: #555;");

        Label equipon = new Label("Año de nacimiento: : " + jugadora.getNombre_equipo());
        equipon.setStyle("-fx-font-size: 14px; -fx-font-weight: normal; -fx-text-fill: #777;");

        Label anioNacimiento = new Label("Equipo: " + jugadora.getFecha());
        anioNacimiento.setStyle("-fx-font-size: 14px; -fx-font-weight: normal; -fx-text-fill: #777;");

        // Creamos el VBox para organizar los componentes
        VBox vbox = new VBox(10, cargarFotoJugadora(jugadora, liga), nombre, posicion, equipon, anioNacimiento);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, #000, 10, 0, 0, 2);");

        Scene scene = new Scene(vbox, 400, 300);
        infoStage.setScene(scene);

        // Cerrar el Stage después de 5 segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> infoStage.close());
        delay.play();

        infoStage.show();
    }


    private ImageView cargarFotoJugadora(Jugadora jugadora, String liga) {
        String rutaImagen = "/Imagenes/Liga_Española/Jugadoras/Valencia CF Femenino/"
                + jugadora.getNombre() + ".jpg";

        InputStream inputStream = getClass().getResourceAsStream(rutaImagen);

        Image imagen;
        if (inputStream != null) {
            imagen = new Image(inputStream);
        } else {
            System.err.println("No se encontró la imagen para: " + rutaImagen);
            inputStream = getClass().getResourceAsStream("/Imagenes/default_jugadora.png");
            if (inputStream == null) {
                throw new RuntimeException("Imagen por defecto no encontrada.");
            }
            imagen = new Image(inputStream);
        }

        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(150);  // Reducimos el tamaño de la imagen
        imageView.setFitHeight(150); // Ajuste proporcional
        imageView.setPreserveRatio(true);

        return imageView;
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
