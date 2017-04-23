package sibsalut.barcode;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

public class MainApp extends Application {

    private Stage mainStage;
    private VBox rootLayout;
    private Button closeButton;
    private Button workModeButton;
    private Button settingsButton;
    private Button addBarcodeButton;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        rootLayout = new VBox();
        initLayout();

    }

    private void initLayout() {

       // rootLayout.getChildren().add(new BarcodeAddForm(mainStage));
        initBottomButtons();
        Scene scene = new Scene(this.rootLayout);
        this.mainStage.setTitle("Сибирский салют");
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }

    private void initBottomButtons() {
        closeButton = new Button(" Закрыть ");
        settingsButton = new Button(" Настройки ");
        settingsButton.setOnAction((e) -> {
            showSettingForm();
        });
        closeButton.setOnAction((e) -> {
            closeAction(e);
        });
        workModeButton = new Button("Режим показа");
        workModeButton.setOnAction((e) -> {
            workMode();
        });
        addBarcodeButton = new Button(" Добавить ");
        addBarcodeButton.setOnAction((e)->{
            showAddBarcodeForm();
        });
        AnchorPane anchorPane = new AnchorPane();
        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 5, 5, 5));
        hb.setSpacing(10);
        hb.getChildren().addAll(addBarcodeButton,workModeButton,settingsButton, closeButton);
        anchorPane.getChildren().add(hb);   // Add grid from Example 1-5
        anchorPane.setBottomAnchor(hb, 5.0);
        anchorPane.setRightAnchor(hb, 5.0);
        anchorPane.setTopAnchor(hb, 10.0);
        rootLayout.getChildren().add(anchorPane);

    }

    private void workMode() {

        File mediaFile = new File("skin.jpg");
        BarcodePlayer bp = new BarcodePlayer(mediaFile, true, false);
        bp.show();
    }

    private void closeAction(ActionEvent event) {
        ButtonType myYesButtonType = new ButtonType("Да");
        ButtonType myNoButtonType = new ButtonType("Нет");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", myYesButtonType, myNoButtonType);
        alert.setTitle("Закрыть?");
        alert.setHeaderText("Вы действительно хотите закрыть программу?");
        alert.showAndWait().ifPresent(response -> {
            if (response == myYesButtonType) {
                mainStage.close();
            }
        });

    }

    private void showSettingForm() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/SettingsForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Настройки");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            SettingsFormController controller = loader.getController();
            controller.setStage(dialogStage);
          

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

         
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }
    private void showAddBarcodeForm(){
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/addBarcodeForm.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Настройки");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            AddBarcodeFormController controller = loader.getController();
            controller.setStage(dialogStage);
          

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

         
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
