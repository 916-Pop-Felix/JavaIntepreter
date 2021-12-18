package Model.exp;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class VarExp implements Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        return symTable.lookup(id);
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        return typeEnv.lookup(id);
    }

    public String toString() {return id;}

}
