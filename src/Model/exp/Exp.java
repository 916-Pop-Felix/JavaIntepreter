package Model.exp;
import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public interface Exp {

    IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterError, DivisionByZeroError, InvalidTypeError, DictError;
    IType typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError;
    String toString();
}
