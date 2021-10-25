package View;
import Exceptions.InterpreterError;
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


public class Main {

    static Repo myRepository = new Repo();
    static Controller myController = new Controller(myRepository);

    public static void main(String[] args) {

        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp(OPERATOR.ADD,new ValueExp(new IntValue(2)),new ArithExp(OPERATOR.MUL,
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp(OPERATOR.ADD,new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));

        //ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
        IStack<IStmt> exeStack = new MyStack<IStmt>();
        IDict<String, IValue> symTable = new Dict<String, IValue>();
        IList<String> out = new List<String>();
        exeStack.push(ex3);
        //exeStack.push(ex2);
        //exeStack.push(ex3);
        PrgState myPrgState = new PrgState(exeStack, symTable, out, ex3);
        System.out.println(ex3);
        myController.addProgram(myPrgState);
        try{
        myController.allStep();

        }
        catch(InterpreterError ie){
            System.out.println(ie);
        }




    }
}
