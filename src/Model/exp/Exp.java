package Model.exp;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.value.IValue;

public interface Exp {

    IValue eval(IDict<String, IValue> symTable) throws InterpreterError, DivisionByZeroError, InvalidTypeError;
    String toString();
}
