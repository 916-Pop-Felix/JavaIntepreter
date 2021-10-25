package Model.exp;

import Exceptions.InterpreterError;
import Model.adt.IDict;
import Model.value.IValue;

public class ValueExp extends Exp{

    IValue value;
    public ValueExp(IValue _val){
        value=_val;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable){
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s",value.toString());
    }
}
