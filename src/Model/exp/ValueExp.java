package Model.exp;

import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class ValueExp implements Exp{

    IValue value;
    public ValueExp(IValue _val){
        value=_val;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap){
        return value;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        return value.getType();
    }

    @Override
    public String toString() {
        return String.format("%s",value.toString());
    }
}
