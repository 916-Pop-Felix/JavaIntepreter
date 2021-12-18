package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public interface IStmt {
    PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError;
    IDict<String, IType> typeCheck(IDict<String,IType> typeEnv) throws InvalidTypeError;
}
