/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sibsalut.barcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import org.apache.commons.io.FileUtils;

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
    @FXML
    private Button saveBarcodeButton;
    @FXML
    private Button clearBarcodeButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField priceTextField;
    private Stage stage;
    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Boolean videoOk = false;
    private Settings settings;
    private Database base;
    private Barcode barcode;
    private Boolean changed;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        settings = new Settings();
        base = Database.getInstance();
        barcode = new Barcode();
        changed = false;
    }

    @FXML
    private void onCloseButton() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        stage.close();
    }

    @FXML
    private void onConvertButton() throws IOException {
        File file = new File(fileNameLabel.getText());
        String copyed_file = settings.getVideoPath() + File.separator + barcodeTextField.getText() + "-not_converted";
        String newFileName = settings.getVideoPath() + File.separator + barcodeTextField.getText() + ".mp4";
        File dest = new File(copyed_file);
        FileUtils.copyFile(file, dest);
        ShellCommand shell = new ShellCommand();
        shell.setCommand(settings.getFfmpegPath());
        String[] options = {
            "-i",
            copyed_file,
            settings.getFfmpegOptions(),
            newFileName
        };
        shell.setOptions(options);
        shell.execute();
        shell.getOutPut();
        shell.getError();
        fileNameLabel.setText(newFileName);
        videoOk = true;
        setChanged();
        //barcode.setVideo(newFileName);
    }

    @FXML
    private void onSettingsButton() {
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
    private void OnFileChooseButton() {
        videoOk = false;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбрать видео файл");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File fileName = fileChooser.showOpenDialog(stage);
        fileNameLabel.setText(fileName.getAbsolutePath());
        playVideo(fileName);
        setChanged();
    }

    @FXML
    private void onSaveBarcodeButton() throws IOException {
        if (videoOk) {
            setBarcodeBycontrols();
            base.insert(barcode);
            setNoChange();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setTitle("Закрыть?");
            alert.setHeaderText("Невозможно сохранить, видео не поддерживается. \n"
                    + " Нажмите на кнопку конвертировать \n"
                    + " или выберите другой файл");
            alert.showAndWait();
        }
    }

    @FXML
    private void barcodeTextChanged() {
        barcode = base.selectByBarcode(barcodeTextField.getText());
        if (barcode.getBarcode() != "") {
            setControlsByEntity();
            File file = new File(barcode.getVideo());
            playVideo(file);
        } else {
            setChanged();
        }

    }

    @FXML
    private void titleTextChanged() {

        setChanged();

    }

    @FXML
    private void priceTextChanged() {

        setChanged();

    }

    @FXML
    private void textChanged() {

        setChanged();
        //stopVideo();

    }

    @FXML
    private void onClearBarcodeButton() {
        barcodeTextField.setText("");
        fileNameLabel.setText("");
        titleTextField.setText("");
        priceTextField.setText("");
        videoPane.getChildren().clear();
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        videoOk = false;
        setChanged();
    }

    public void setStage(Stage st) {
        stage = st;
    }

    private void setControlsByEntity() {
        barcodeTextField.setText(barcode.getBarcode());
        fileNameLabel.setText(barcode.getVideo());
        titleTextField.setText(barcode.getTitle());
        priceTextField.setText(String.valueOf(barcode.getPrice()));
        videoOk = true;
    }

    private void setBarcodeBycontrols() {
        barcode.setBarcode(barcodeTextField.getText());
        barcode.setVideo(fileNameLabel.getText());
        barcode.setTitle(titleTextField.getText());
        Double price = 0.0;
        try {
            price = Double.valueOf(priceTextField.getText());
        } catch (NumberFormatException exp) {
            price = 0.0;
        }
        barcode.setPrice(price);
    }

    private void setChanged() {
        changed = true;
        saveBarcodeButton.setText("Сохранить*");
    }

    private void setNoChange() {
        changed = false;
        saveBarcodeButton.setText("Сохранить");
    }

    private void playVideo(File fileName) {
        try {
            stopVideo();
            videoOk=true;
            mediaPlayer = null;
            Media media = new Media(fileName.toURI().toString());
            System.out.println("Play video: " + fileName.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView = new MediaView(mediaPlayer);
            videoPane.getChildren().add(mediaView);
            mediaView.setFitWidth(videoPane.getMaxWidth());
            mediaView.setFitWidth(videoPane.getMaxHeight());
            mediaPlayer.setOnError(new Runnable()
                {
                    public void run()
                    {
                        setUnSupportedVideo();
                    }
                });
            mediaPlayer.setOnReady(new Runnable() {

                @Override
                public void run() {

                    mediaPlayer.play();
                }
            });
            System.out.println("Media player status: " + mediaPlayer.getStatus().toString());
            } catch (MediaException ex) {
            setUnSupportedVideo();
            System.out.println("Media player exception: " + ex.getMessage());
        }
        }

    

    private void setUnSupportedVideo() {
        videoPane.getChildren().clear();
        Label notSupportedVideo = new Label(" Видео не поддерживается нажмите на кнопку конвертировать");
        videoPane.getChildren().add(notSupportedVideo);
        videoOk=false;
    }

    private void stopVideo() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
           
        }
        videoPane.getChildren().clear();
    }
}
