/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vadim
 */
public class AddBarcodeFormController implements Initializable {
    @FXML
     private TextField barcodeTextField;
    @FXML
    private Button fileChooseButton;
    @FXML
    private Label fileNameLabel; 
    @FXML
    private StackPane videoPane;
    @FXML 
    private Button closeButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button convertButtons;
    private Stage stage;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    @FXML
    private void onCloseButton(){
        stage.close();
    }
    @FXML
    private void onConvertButton(){
        
    }
    @FXML
    private void onSettingsButton(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/SettingsForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Настройки");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            SettingsFormController controller = loader.getController();
            controller.setStage(dialogStage);
            dialogStage.showAndWait(); 
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }
    @FXML
    private void OnFileChooseButton(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать видео файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File fileName = fileChooser.showOpenDialog(stage);

         try {
             if(mediaPlayer!=null&&mediaPlayer.getStatus()==MediaPlayer.Status.PLAYING){
                 mediaPlayer.stop();
             }
            media = new Media(fileName.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView = new MediaView(mediaPlayer);
            videoPane.getChildren().clear();
            videoPane.getChildren().add(mediaView);
            mediaView.setFitWidth(videoPane.getWidth());
            mediaView.setFitWidth(videoPane.getHeight());
            mediaPlayer.play();
        } catch (MediaException ex) {
            videoPane.getChildren().clear();
            Label notSupportedVideo = new Label(" Видео не поддерживается нажмите на кнопку конвертировать");
            videoPane.getChildren().add(notSupportedVideo);
        }
    }
    public void setStage(Stage st){
        stage =st;
    }
    
}
