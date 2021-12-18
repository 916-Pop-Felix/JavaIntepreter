package Model.stmt;


import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IType;

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
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}
