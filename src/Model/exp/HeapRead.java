package Model.exp;

import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapRead implements Exp{
    Exp exp;

    public HeapRead(Exp exp) {
        this.exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws DictError, DivisionByZeroError, InvalidTypeError, InterpreterError {
        IValue eval=exp.eval(symTable,heap);
        if (!(eval.getType() instanceof RefType))
            throw new InvalidTypeError(String.format("%s is not of type Ref",eval));
        RefValue refValue=(RefValue) eval;
        return heap.get(refValue.getAddr());
    }

    @Override
    public String toString() {
        return String.format("rH(%s)",exp);
    }
}
