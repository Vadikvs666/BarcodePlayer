/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author vadim
 */


public class BarcodeAddForm extends StackPane {

    private Label editLabel;
    private Label fileChooseLabel;
    private Label fileNameLabel;
    private StackPane videoPane;
    private TextField barcodeEdit;
    private Button addButton;
    private Button closeButton;
    private Button chooseFileButton;
    private Button convertButton;
    private HBox labelTextLayout;
    private HBox buttonsLayout;
    private VBox centralLayout;
    private Stage mainStage;
    private final int width = 480;
    private final int height = 240;
    private final int videoWidth = 320;
    private final int videoHeight = 240;

    BarcodeAddForm(Stage stage) {
        mainStage = stage;
        initControls();
        buildLayouts();

    }

    private String getFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("All Files", "*.*"));
        File fileName = fileChooser.showOpenDialog(mainStage);
        
        try {

            Media media = new Media(fileName.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            videoPane.getChildren().add(mediaView);
            mediaView.setFitWidth(videoWidth);
            mediaView.setFitWidth(videoHeight );
            mediaPlayer.play();
        } catch (MediaException ex) {
            Label notSupportedVideo = new Label(" Видео не поддерживается нажмите на кнопку конвертировать");
            videoPane.getChildren().add(notSupportedVideo);
        }

        return fileName.getCanonicalPath();

    }

    private void initControls() {
        editLabel = new Label(" Штрих-код ");
        fileChooseLabel = new Label(" Выберите файл с видео ");
        barcodeEdit = new TextField();
        
        barcodeEdit.setPromptText("Отсканируйте штрих-код");
        convertButton = new Button(" Конвертировать видео ");
        addButton = new Button(" Сохранить ");
        closeButton = new Button(" Закрыть ");
        chooseFileButton = new Button(" Выбрать файл");
        chooseFileButton.setOnAction((e) -> {
            try {
                getFile();
            } catch (IOException ex) {
                Logger.getLogger(BarcodeAddForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        labelTextLayout = new HBox();
        labelTextLayout.setMinWidth(width);
        barcodeEdit.setMinWidth(width-labelTextLayout.getWidth()-15);
        labelTextLayout.setPadding(new Insets(5, 5, 5, 5));
        labelTextLayout.setSpacing(10);
        buttonsLayout = new HBox();
        centralLayout = new VBox();
        videoPane = new StackPane();
        videoPane.setMaxSize(videoWidth, videoHeight);
    }

    private void buildLayouts() {
        setMinWidth(width);
        setMinHeight(height);
        this.labelTextLayout.getChildren().add(this.editLabel);
        this.labelTextLayout.getChildren().add(this.barcodeEdit);
        this.buttonsLayout.getChildren().add(this.addButton);
        buttonsLayout.getChildren().add(chooseFileButton);
        //this.buttonsLayout.getChildren().add(this.closeButton);
        this.centralLayout.getChildren().add(this.labelTextLayout);
        centralLayout.getChildren().add(videoPane);
        this.centralLayout.getChildren().add(this.buttonsLayout);
        this.getChildren().add(this.centralLayout);
    }

}
