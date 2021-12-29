package View.gui;

import Controller.Controller;
import Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainWindow {
    private Controller controller;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTable;

    @FXML
    private TableColumn<Pair<Integer,IValue>,Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer,IValue>,String> valueColumn;

    @FXML
    private TableView<Pair<String,IValue>> symTable;

    @FXML
    private TableColumn<Pair<String,IValue>,String> symVarCol;

    @FXML
    private TableColumn<Pair<String,IValue>,String> symValueCol;

    @FXML
    private ListView<String> out;

    @FXML
    private ListView<String> fileTable;

    @FXML
    private ListView<Integer> prgList;

    @FXML
    private ListView<String> exeStack;

    @FXML
    private Button oneStep;

    @FXML
    private TextField prgSize;

    public void setController(Controller controller) {
        this.controller = controller;
        populateMain();
    }

    @FXML
    public void initialize(){
        addressColumn.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValue().toString()));
        symVarCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getKey()));
        symValueCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValue().toString()));
        oneStep.setOnAction(actionEvent -> {
            if (controller==null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            if (getCurrentPrg()==null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Program at end of execution", ButtonType.OK);
                alert.showAndWait();
                populateMain();
                return;
            }
            if (getCurrentPrg().getExeStack().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Program at end of execution!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            try {
                controller.oneStep();
                populateMain();
            } catch (InterpreterError | ListError | StackError | DictError | VarNotDefinedError | InvalidTypeError | DivisionByZeroError | VarAlreadyDefined | IOException | FileError | InterruptedException interpreterError) {
                Alert alert = new Alert(Alert.AlertType.ERROR, interpreterError.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        prgList.setOnMouseClicked(mouseEvent -> {
            populateMain();
        });
    }

    private PrgState getCurrentPrg(){
        if (controller.getRepo().getPrograms().size()==0)
            return null;
        int id=prgList.getSelectionModel().getSelectedIndex();
        if (id==-1)
            return controller.getRepo().getPrgAtPos(0);
        return controller.getRepo().getPrgAtPos(id);
    }

    private void populateMain(){
        populateHeap();
        populatePrgIDs();
        populateSym();
        populateOut();
        populateFile();
        populateStack();
    }



    private void populateHeap() {
        IHeap heap=new Heap();
        if (controller.getRepo().getPrograms().size()>0)
            heap=controller.getRepo().getPrograms().get(0).getHeap();
        List<Pair<Integer,IValue>> heapList=new ArrayList<>();
        for (Map.Entry<Integer,IValue> entry:heap.getContent().entrySet()){
            heapList.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        heapTable.setItems(FXCollections.observableArrayList(heapList));
        heapTable.refresh();
    }

    private void populatePrgIDs(){
        List<PrgState> prgStates=controller.getRepo().getPrograms();
        prgList.setItems(FXCollections.observableArrayList(prgStates.stream().
                map(ps ->ps.id).collect(Collectors.toList())));
        prgSize.setText(String.valueOf(prgStates.size()));
    }

    private void populateSym(){
        PrgState state=getCurrentPrg();
        List<Pair<String,IValue>> symList=new ArrayList<>();
        if (state!=null) {
            for (Map.Entry<String, IValue> entry : state.getSymTable().getContent().entrySet()) {
                symList.add(new Pair<>(entry.getKey(), entry.getValue()));
            }
        }
        symTable.setItems(FXCollections.observableArrayList(symList));
        symTable.refresh();
    }

    private void populateOut(){
        IList<String> outList=new Model.adt.List<>();
        if (controller.getRepo().getPrograms().size()>0)
          outList=controller.getRepo().getPrgAtPos(0).getOutput();
        out.setItems(FXCollections.observableArrayList(outList.get()));
        out.refresh();
    }

    private void populateFile() {
        List<String> files=new ArrayList<>();
        if (controller.getRepo().getPrograms().size()>0)
            files= new ArrayList<>(controller.getRepo().getPrgAtPos(0).getFileTable().getContent().keySet());
        fileTable.setItems(FXCollections.observableArrayList(files));
    }

    private void populateStack(){
        PrgState state=getCurrentPrg();
        List<String> exeList=new ArrayList<>();

        if (state!=null){
            IStack<IStmt> stackCopy=state.getExeStack();
            for (IStmt s: state.getExeStack().get()){
                exeList.add(s.toString());
            }
        }
        exeStack.setItems(FXCollections.observableArrayList(exeList));
        exeStack.refresh();
    }
}
