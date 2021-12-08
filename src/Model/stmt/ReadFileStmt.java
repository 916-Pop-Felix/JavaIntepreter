package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
    Exp exp;
    String varName;

    public ReadFileStmt(Exp _exp, String _var){
        exp=_exp;
        varName=_var;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError {
        IDict<String, IValue> symTable=state.getSymTable();
        IDict<String, BufferedReader> fileTable=state.getFileTable();
        if (!symTable.isDefined(varName))
            throw new VarNotDefinedError(String.format("Variable %s not defined!",varName));
        IValue value=symTable.lookup(varName);
        if (!value.getType().equals(new IntType()))
            throw new InvalidTypeError(String.format("Variable %s is not of type int",value));
        value=exp.eval(symTable,state.getHeap() );
        if (!value.getType().equals(new StringType()))
            throw new InvalidTypeError(String.format("Variable %s is not of type string",value));
        StringValue fileName=(StringValue) value;
        if (!fileTable.isDefined(fileName.getValue()))
            throw new FileError(String.format("File %s not opened!",fileName.getValue()));
        BufferedReader buf=fileTable.lookup(fileName.getValue());
        try{
            String line=buf.readLine();
            if (line==null)
                line="0";
            symTable.update(varName,new IntValue(Integer.parseInt(line)));
        }
        catch (IOException ioe){
            throw new FileError(String.format("Could not read from file %s",fileName.getValue()));
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("readFile(%s,%s)",exp,varName);
    }
}
