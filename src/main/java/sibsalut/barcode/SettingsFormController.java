/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sibsalut.barcode.Settings;

/**
 * FXML Controller class
 *
 * @author vadim
 */
public class SettingsFormController implements Initializable {
    @FXML
    private TextField videoPathLabel;
    @FXML
    private TextField ffmpegPathLabel;
    @FXML
    private Button closeButton;
    @FXML 
    private Button saveButton;
    private Settings setings;
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setings =new Settings();
        videoPathLabel.setText(setings.getVideoPath());
        ffmpegPathLabel.setText(setings.getFfmpegPath());
    } 
    @FXML
    private void onSaveBattonClicked(ActionEvent event){
        setings.setFfmpegPath(ffmpegPathLabel.getText());
        setings.setVideoPath(videoPathLabel.getText());
    }
    @FXML
    private void onCloseButtonClicked(ActionEvent event){
        stage.close();
    }
    public void setStage(Stage st){
        stage=st;
    }
    
}
