package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    Exp exp;

    public CloseRFileStmt(Exp _exp){exp=_exp;}

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError {
        IValue value=exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeError(String.format("%s is not of type string",exp));
        StringValue fileName=(StringValue) value;
        IDict<String, BufferedReader> fileTable=state.getFileTable();
        if (!fileTable.isDefined(fileName.getValue()))
            throw new FileError(String.format("File %s not opened!",fileName));
        BufferedReader buf=fileTable.lookup(fileName.getValue());
        try{
            buf.close();
        }
        catch(IOException ioe) {
            throw new FileError(String.format("File %s could not be closed!",fileName));
        }
        fileTable.remove(fileName.getValue());
        return null;
    }

    @Override
    public String toString() {
        return String.format("closeRFile(%s)",exp);
    }
}
