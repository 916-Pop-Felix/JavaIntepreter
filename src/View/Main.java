package View;

import Exceptions.*;
import Model.adt.*;
import Model.adt.List;
import Model.exp.*;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.RefType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;
import View.commands.ExitCommand;
import View.commands.RunCommand;
import View.gui.ListWindow;
import View.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {

    public static java.util.List<IStmt> examples=new ArrayList<>();

    static IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));

    // ex 2: a=2+3*5;b=a+1;Print(b)
    static IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
            new CompStmt(new AssignStmt("a", new ArithExp(OPERATOR.ADD, new ValueExp(new IntValue(2)), new ArithExp(OPERATOR.MUL,
                    new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                    new AssignStmt("b", new ArithExp(OPERATOR.ADD, new VarExp("a"), new ValueExp(new IntValue(1)))),
                    new PrintStmt(new VarExp("b"))))));

    //ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
    static IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
            new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
            new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                    VarExp("v"))))));

    static IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",
            new ValueExp(new StringValue("test.in"))), new CompStmt(new OpenRFileStmt(new VarExp("varf")),
            new CompStmt(new VarDeclStmt("varc", new IntType()), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                    new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                            new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));

    static IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4)))
            , new CompStmt(new WhileStmt(new RelationExp(OPERATOR.GREATER, new VarExp("v"), new ValueExp(new IntValue(0)))
            , new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(OPERATOR.SUB, new VarExp("v"),
            new ValueExp(new IntValue(1)))))), new PrintStmt(new VarExp("v")))));

    static IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAlloc(
            "v", new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType())))
            , new CompStmt(new HeapAlloc("a", new VarExp("v")), new CompStmt(new PrintStmt(new VarExp("v")),
            new PrintStmt(new VarExp("a")))))));

    static IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAlloc(
            "v", new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType())))
            , new CompStmt(new HeapAlloc("a", new VarExp("v")), new CompStmt(new PrintStmt(new HeapRead(new VarExp("v"))),
            new PrintStmt(new ArithExp(OPERATOR.ADD, new HeapRead(new HeapRead(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

    static IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAlloc("v", new ValueExp(new IntValue(20)))
            , new CompStmt(new PrintStmt(new HeapRead(new VarExp("v"))), new CompStmt(new HeapWrite("v", new ValueExp(new IntValue(30))),
            new PrintStmt(new ArithExp(OPERATOR.ADD, new HeapRead(new VarExp("v")), new ValueExp(new IntValue(5))))))));

    static IStmt ex9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAlloc(
            "v", new ValueExp(new IntValue(20))), new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType())))
            , new CompStmt(new HeapAlloc("a", new VarExp("v")), new CompStmt(new HeapAlloc(
            "v", new ValueExp(new IntValue(30))), new PrintStmt(new HeapRead(new HeapRead(new VarExp("a")))))))));


    static IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a",
            new RefType(new IntType())), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
            new CompStmt(new HeapAlloc("a", new ValueExp(new IntValue(22))),
                    new CompStmt(new ForkStmt(new CompStmt(new HeapWrite("a", new ValueExp(new IntValue(30)))
                            , new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapRead(new VarExp("a"))))))),
                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapRead(new VarExp("a")))))))));

    static IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()), new AssignStmt("v", new ValueExp(new BoolValue())));

    public static Controller createController(IStmt ex, String logFileName) {
        PrgState myPrgState = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue>(), new List<String>(),
                new Dict<String, BufferedReader>(), new Heap(), ex);
        Repo repo = new Repo(logFileName);
        Controller ctrl = new Controller(repo);
        ctrl.addProgram(myPrgState);
        return ctrl;
    }

    public static void addExamples(){
        examples.add(ex1);
        examples.add(ex2);
        examples.add(ex3);
        examples.add(ex4);
        examples.add(ex5);
        examples.add(ex6);
        examples.add(ex7);
        examples.add(ex8);
        examples.add(ex9);
        examples.add(ex10);
        examples.add(ex11);
    }

    public static java.util.List<IStmt> getExamples(){
        return examples;
    }

    public static void main(String[] args) throws InterpreterError, ListError, StackError, DictError, VarNotDefinedError
            , InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, IOException, FileError {




//        Controller ctrl1 = createController(ex1, "log1.txt");
//        Controller ctrl2 = createController(ex2, "log2.txt");
//        Controller ctrl3 = createController(ex3, "log3.txt");
//        Controller ctrl4 = createController(ex4, "log4.txt");
//        Controller ctrl5 = createController(ex5, "log5.txt");
//        Controller ctrl6 = createController(ex6, "log6.txt");
//        Controller ctrl7 = createController(ex7, "log7.txt");
//        Controller ctrl8 = createController(ex8, "log8.txt");
//        Controller ctrl9 = createController(ex9, "log9.txt");
//        Controller ctrl10 = createController(ex10, "log10.txt");
//        Controller ctrl11 = createController(ex11, "log11.txt");

//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "Exit"));
//        menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
//        menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
//        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));
//        menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));
//        menu.addCommand(new RunCommand("5", ex5.toString(), ctrl5));
//        menu.addCommand(new RunCommand("6", ex6.toString(), ctrl6));
//        menu.addCommand(new RunCommand("7", ex7.toString(), ctrl7));
//        menu.addCommand(new RunCommand("8", ex8.toString(), ctrl8));
//        menu.addCommand(new RunCommand("9", ex9.toString(), ctrl9));
//        menu.addCommand(new RunCommand("10", ex10.toString(), ctrl10));
//        menu.addCommand(new RunCommand("11", ex11.toString(), ctrl11));
//        menu.show();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        addExamples();
        FXMLLoader listLoader=new FXMLLoader();
        listLoader.setLocation(getClass().getResource("gui/ListWindow.fxml"));
        Parent root=listLoader.load();
        ListWindow listCtrl=listLoader.getController();
        Scene scene=new Scene(root,600,400);
        stage.setScene(scene);
        stage.setTitle("Program List");
        stage.show();

        FXMLLoader mainLoader=new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("gui/MainWindow.fxml"));
        Parent mainRoot=mainLoader.load();
        MainWindow mainCtrl=mainLoader.getController();
        listCtrl.setMainWindow(mainCtrl);
        Stage mainStage=new Stage();
        mainStage.setTitle("Toy Language Interpreter");
        mainStage.setScene(new Scene(mainRoot,1000,700));
        mainStage.show();
    }
}
