package Model.exp;

import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
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
    public IType typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        IType typ= exp.typeCheck(typeEnv);
        if (typ instanceof RefType){
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new InvalidTypeError("RH arg is not of type Ref");
    }

    @Override
    public String toString() {
        return String.format("rH(%s)",exp);
    }
}
