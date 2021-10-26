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

import java.util.Scanner;


public class Main {

    static Repo myRepository = new Repo();
    static Controller myController = new Controller(myRepository);
    static Scanner scan = new Scanner(System.in);
    public static void printMenu(){
        System.out.println("""
                Please enter the program of your choice:
                1. int v; v=2; print(2);
                2. a=2+3*5;b=a+1;print(b);
                3. bool a; int v; a=true;(If a Then v=2 Else v=3);print(v);
                """);
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
        boolean loop=true;
        while (loop) {
            printMenu();
            IStack<IStmt> exeStack = new MyStack<IStmt>();
            IDict<String, IValue> symTable = new Dict<String, IValue>();
            IList<String> out = new List<String>();

            System.out.print(">> ");
            int op=scan.nextInt();
            //scan.close();
            switch (op) {
                case 0 -> loop = false;
                case 1 -> {
                    exeStack.push(ex1);
                    PrgState myPrgState1 = new PrgState(exeStack, symTable, out, ex1);
                    myController.addProgram(myPrgState1);
                    myController.allStep();
                }
                case 2 -> {
                    exeStack.push(ex2);
                    PrgState myPrgState2 = new PrgState(exeStack, symTable, out, ex2);
                    myController.addProgram(myPrgState2);
                    myController.allStep();
                }
                case 3 -> {
                    exeStack.push(ex3);
                    PrgState myPrgState3 = new PrgState(exeStack, symTable, out, ex3);
                    myController.addProgram(myPrgState3);
                    myController.allStep();
                }
            }

        }


    }
}
