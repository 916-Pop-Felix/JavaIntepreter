package Model.stmt;


import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.List;
import Model.exp.Exp;
import Model.value.IValue;

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

    public PrgState execute(PrgState state) throws InterpreterError, DivisionByZeroError, InvalidTypeError {
        state.getOutput().add(expression.eval(state.getSymTable()).toString());
        return state;
    }
}
