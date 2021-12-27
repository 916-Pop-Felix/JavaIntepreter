package View.gui;

import Controller.Controller;
import Exceptions.InvalidTypeError;
import Exceptions.StackError;
import Model.PrgState;
import Model.stmt.IStmt;
import View.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

public class ListWindow {

    private MainWindow mainWindow;

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @FXML
    private ListView<IStmt> statements;

    @FXML
    private Button selectButton;

    @FXML
    public void  initialize(){
        statements.setItems(FXCollections.observableArrayList(Main.getExamples()));
        selectButton.setOnAction(actionEvent -> {
            int index=statements.getSelectionModel().getSelectedIndex();
            if (index<0)
                return;
            Controller ctrl=Main.createController(Main.examples.get(index),"log.txt");
            try {
                ctrl.runTypeChecker();
                mainWindow.setController(ctrl);
            } catch (InvalidTypeError | StackError invalidTypeError) {
                Alert alert = new Alert(Alert.AlertType.ERROR, invalidTypeError.toString(), ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        });
    }
}
