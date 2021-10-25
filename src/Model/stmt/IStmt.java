package Model.stmt;

import Exceptions.InterpreterError;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws InterpreterError;

}
