package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IStack;
import Model.adt.MyStack;

public class ForkStmt implements IStmt{
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError {
        return new PrgState(new MyStack<>(),state.getSymTable(),state.getOutput(), state.getFileTable(),
                state.getHeap(), stmt);
    }

    @Override
    public String toString() {
        return String.format("fork(\n%s\n)",stmt);
    }
}
