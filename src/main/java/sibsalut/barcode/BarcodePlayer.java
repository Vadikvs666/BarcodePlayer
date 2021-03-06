/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author vadim
 */
public class BarcodePlayer extends Stage {

    private Scene scene;
    private StackPane pane;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private final int width = 480;
    private final int height = 240;
    private final Boolean fullScreen;
    private Settings settings;
    private Database base;

    public Slot onClose ;

    public BarcodePlayer(File mediaFile, Boolean fullscreen, Boolean checkAdded) {
         if (checkAdded) {
                createPlayerToCheckVideo(mediaFile);
            } else {
                createPlayerToPlayBarcodes(mediaFile);
            }
            fullScreen = fullscreen;
            setSceneSize();
            settings = new Settings();
            base = Database.getInstance();
            this.onClose = new Slot(this,"closePlayer");
        

    }

    public StackPane getPane() {
        return pane;
    }

    private void setSceneSize() {
        setMinWidth(width);
        setMinHeight(height);
        setFullScreen(fullScreen);
        setFullScreenExitHint("");
    }

    private void initMedia(File mediaFile) {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        Media media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView = new MediaView(mediaPlayer);
        pane = new StackPane();
        pane.getChildren().add(mediaView);
        scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/styles/bp.css").toExternalForm());
        setTitle("sibsalut");
        setScene(scene);
        mediaPlayer.play();

    }

    private void createPlayerToCheckVideo(File mediaFile) {
        try {
            initMedia(mediaFile);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    close();
                }
            });
        } catch (MediaException ex) {
            showNotSupportedForm();
        }
    }

    private void createPlayerToPlayBarcodes(File mediaFile) {
        try {
            initMedia(mediaFile);
            initEvents();

        } catch (MediaException ex) {
            initByImage();
            initEvents();

        }
    }

    private void initByImage() {
        pane = new StackPane();
        scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/styles/bp.css").toExternalForm());
        setTitle("sibsalut");
        setScene(scene);
    }

    private void initEvents() {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                close();
            } else {
                MediaByBarcode(e.getText());
                System.out.println(e.toString());
            }
        });

    }

    private void MediaByBarcode(String code) {
        Barcode barcode = base.selectByBarcode(code);
        //  File file = new File(settings.getVideoPath() + File.separator + code + ".mp4");
        File file = new File(barcode.getVideo());
        initMedia(file);
        Label title =new Label(barcode.getTitle());
        title.setId("labeltext");
        Label price =new Label("Цена: "+String.valueOf(barcode.getPrice())+" руб.");
        price.setId("labeltext");
        pane.getChildren().addAll(title,price);
        pane.setAlignment(title,Pos.TOP_LEFT);
        pane.setAlignment(price,Pos.BOTTOM_RIGHT);
        initEvents();
        setSceneSize();
    }

    private void showNotSupportedForm() {
        pane = new StackPane();
        Button closeButton = new Button(" Закрыть ");
        closeButton.setOnAction((e) -> {
            close();
        });
        Button convertButton = new Button(" Конвертироавть ");
        Label label = new Label(" Видео не поддерживается выберите другой файл или нажмите кнопку конвертировать");
        VBox vb = new VBox();
        vb.getChildren().add(label);
        AnchorPane anchorPane = new AnchorPane();
        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 5, 5, 5));
        hb.setSpacing(10);
        hb.getChildren().addAll(convertButton, closeButton);

        anchorPane.getChildren().add(hb);   // Add grid from Example 1-5
        anchorPane.setBottomAnchor(hb, 5.0);
        anchorPane.setRightAnchor(hb, 5.0);
        anchorPane.setTopAnchor(hb, 10.0);
        vb.getChildren().add(anchorPane);
        pane.getChildren().add(vb);
        scene = new Scene(pane);
        setTitle("Видео не поддерживается выберите другой файл или нажмите кнопку конвертировать");
        setScene(scene);

    }
    protected void closePlayer(){
        close();
    }
}
