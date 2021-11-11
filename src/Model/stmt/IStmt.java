package Model.stmt;

import Exceptions.*;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError;

}
