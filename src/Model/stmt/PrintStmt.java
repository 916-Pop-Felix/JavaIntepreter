package Model.stmt;


import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.exp.Exp;

public class PrintStmt implements IStmt{
    Exp expression;

    public PrintStmt(Exp exp)
    {
        this.expression = exp;
    }

    @Override
    public String toString() {
        return "print(" +expression.toString()+")";
    }

    public PrgState execute(PrgState state) throws InterpreterError, DivisionByZeroError, InvalidTypeError, DictError {
        state.getOutput().add(expression.eval(state.getSymTable(),state.getHeap() ).toString());
        return state;
    }
}
