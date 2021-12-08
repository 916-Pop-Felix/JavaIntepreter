package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt{
    Exp exp;

    public OpenRFileStmt(Exp _exp){exp=_exp;}

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined,FileError {
        IValue value=exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeError(String.format("%s is not of type string",exp));
        StringValue fileName=(StringValue) value;
        IDict<String, BufferedReader> fileTable=state.getFileTable();
        if (fileTable.isDefined(fileName.getValue()))
            throw new FileError(String.format("File %s already opened!",fileName));
        BufferedReader buf;
        try{
            buf=new BufferedReader(new FileReader(fileName.getValue()));
        }
        catch(IOException ioe) {
            throw new FileError(String.format("File %s could not be opened",fileName));
        }
        fileTable.add(fileName.getValue(),buf);
        return null;
    }

    @Override
    public String toString() {
        return String.format("openRFile(%s)",exp.toString());
    }
}
