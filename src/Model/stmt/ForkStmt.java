package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyStack;
import Model.types.IType;

public class ForkStmt implements IStmt{
    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError {
        return new PrgState(new MyStack<>(),state.getSymTable().copy(),state.getOutput(), state.getFileTable(),
                state.getHeap(), stmt);
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        stmt.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("fork(\n%s\n)",stmt);
    }
}
