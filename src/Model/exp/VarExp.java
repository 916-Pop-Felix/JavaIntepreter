package Model.exp;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.value.IValue;

public class VarExp implements Exp{
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        return symTable.lookup(id);
    }

    public String toString() {return id;}

}
