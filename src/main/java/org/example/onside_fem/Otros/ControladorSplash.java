package org.example.onside_fem.Otros;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ControladorSplash {
    //Variables
    @FXML
    private ImageView gifImageView;
    @FXML
    private ProgressBar progressBar;

    // Metodo para iniciar el splash con la barra de progreso
    public void startSplash(Runnable onComplete) {
        try {
            String gifPath = getClass().getResource("/images/cupragif.gif").toExternalForm();
            Image gifImage = new Image(gifPath);
            gifImageView.setImage(gifImage);

            // Crear un timeline para la animación de la barra de progreso
            Timeline timeline = new Timeline(

                    new KeyFrame(Duration.ZERO, e -> progressBar.setProgress(0.0)),  // Empezamos con el 0%
                    new KeyFrame(Duration.seconds(1), e -> progressBar.setProgress(0.25)), // 1 segundo => 25%
                    new KeyFrame(Duration.seconds(2), e -> progressBar.setProgress(0.5)),  // 2 segundos => 50%
                    new KeyFrame(Duration.seconds(3), e -> progressBar.setProgress(0.75)), // 3 segundos => 75%
                    new KeyFrame(Duration.seconds(4), e -> progressBar.setProgress(1.0))   // Al final de 4 segundos => 100%
            );

            timeline.setOnFinished(e -> onComplete.run());

            timeline.setCycleCount(1);
            timeline.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
