package Model.exp;
import Exceptions.InterpreterError;
import Model.adt.IDict;
import Model.value.IValue;

public abstract class Exp {

    public abstract IValue eval(IDict<String, IValue> symTable) throws InterpreterError;
    public abstract String toString();
}
