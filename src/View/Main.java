package View;

import Exceptions.*;
import Model.adt.*;
import Model.exp.ArithExp;
import Model.exp.OPERATOR;
import Model.exp.ValueExp;
import Model.exp.VarExp;
import Model.stmt.*;
import Model.types.BoolType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;
import View.commands.ExitCommand;
import View.commands.RunCommand;

import java.util.Scanner;


public class Main {

    static Repo myRepository = new Repo();
    static Controller myController = new Controller(myRepository);
    private static Controller createController(IStmt ex){
        PrgState myPrgState = new PrgState(new MyStack<IStmt>(), new Dict<String, IValue>(), new List<String>(), ex);
        Repo repo=new Repo();
        repo.addPrg(myPrgState);
        return new Controller(repo);
    }

    public static void main(String[] args) throws InterpreterError, ListError, StackError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined {

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp(OPERATOR.ADD, new ValueExp(new IntValue(2)), new ArithExp(OPERATOR.MUL,
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(
                        new AssignStmt("b", new ArithExp(OPERATOR.ADD, new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        //ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));

        Controller ctrl1=createController(ex1);
        Controller ctrl2=createController(ex2);
        Controller ctrl3=createController(ex3);


        TextMenu menu=new TextMenu();
        menu.addCommand(new ExitCommand("0","Exit"));
        menu.addCommand(new RunCommand("1",ex1.toString(),ctrl1));
        menu.addCommand(new RunCommand("2",ex2.toString(),ctrl2));
        menu.addCommand(new RunCommand("3",ex3.toString(),ctrl3));
        menu.show();
    }
}
