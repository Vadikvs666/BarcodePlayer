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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author vadim
 */
public class BarcodeAddForm extends Parent{
    
    private Label editLabel;
    private TextField barcodeEdit;
    private Button addButton;
    private Button closeButton;
    private Button chooseFileButton;
    private HBox labelTextLayout;
    private HBox buttonsLayout;
    private VBox centralLayout;
    private Stage mainStage;
    
    BarcodeAddForm(Stage stage)
    {
        mainStage=stage;
        initControls();
        buildLayouts();
        
    }
    
    private String getFile() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                     new ExtensionFilter("All Files", "*.*"));
        File fileName= fileChooser.showOpenDialog(mainStage);
        BarcodePlayer bp = new BarcodePlayer(fileName,false,true);
        bp.show();
        return fileName.getCanonicalPath();
        
    }
    
    private void initControls()
    {
        editLabel = new Label(" Штрих-код ");
        barcodeEdit = new TextField();
        barcodeEdit.setPromptText("Отсканируйте штрих-код");
        addButton = new Button(" Добавить ");
        closeButton = new Button(" Закрыть ");
        chooseFileButton =new Button(" Выберите файл с видео");
        chooseFileButton.setOnAction((e)->{
            try {
                getFile();
            } catch (IOException ex) {
                Logger.getLogger(BarcodeAddForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        labelTextLayout = new HBox();
        buttonsLayout = new HBox();
        centralLayout =new VBox();
    }
    
    private void buildLayouts()
    {
        this.labelTextLayout.getChildren().add(this.editLabel);
        this.labelTextLayout.getChildren().add(this.barcodeEdit);
        this.buttonsLayout.getChildren().add(this.addButton);
        buttonsLayout.getChildren().add(chooseFileButton);
        //this.buttonsLayout.getChildren().add(this.closeButton);
        this.centralLayout.getChildren().add(this.labelTextLayout);
        this.centralLayout.getChildren().add(this.buttonsLayout);
        this.getChildren().add(this.centralLayout);
    }

   
}
