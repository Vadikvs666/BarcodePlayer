package sibsalut.barcode;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainApp extends Application {

    private Stage mainStage;
    private BorderPane rootLayout;
    private Button closeButton;
    private Button workModeButton;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        rootLayout = new BorderPane();
        initLayout();

    }

    private void initLayout() {

        rootLayout.setRight(new BarcodeAddForm(mainStage));
        initBottomButtons();
        Scene scene = new Scene(this.rootLayout);
        this.mainStage.setTitle("Добавление штрих-кода");
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }

    private void initBottomButtons() {
        closeButton = new Button(" Закрыть ");
        closeButton.setOnAction((e) -> {
            closeAction(e);
        });
        workModeButton = new Button("Режим показа");
        workModeButton.setOnAction((e) -> {
            workMode();
        });
        AnchorPane anchorPane = new AnchorPane();
        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 5, 5, 5));
        hb.setSpacing(10);
        hb.getChildren().addAll(workModeButton, closeButton);
        anchorPane.getChildren().add(hb);   // Add grid from Example 1-5
        anchorPane.setBottomAnchor(hb, 5.0);
        anchorPane.setRightAnchor(hb, 5.0);
        anchorPane.setTopAnchor(hb, 10.0);
        rootLayout.setBottom(anchorPane);

    }

    private void workMode() {

        File mediaFile = new File("skin.jpg");
        BarcodePlayer bp = new BarcodePlayer(mediaFile,true,false);
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
