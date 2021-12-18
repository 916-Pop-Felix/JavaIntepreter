package Model.exp;

import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.BoolType;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    OPERATOR op;

    public LogicExp(Exp _e1, Exp _e2, OPERATOR _op){
        e1=_e1;
        e2=_e2;
        op=_op;
    }
    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws InterpreterError, DivisionByZeroError, InvalidTypeError, DictError {
        IValue v1,v2;
        v1=e1.eval(symTable, heap);
        if (v1.getType().equals(new BoolType())){
            v2=e2.eval(symTable,heap );
            if (v2.getType().equals(new BoolType())){
                BoolValue b1=(BoolValue)v1 ;
                BoolValue b2=(BoolValue)v2 ;
                boolean n1,n2;
                n1=b1.getValue();
                n2=b2.getValue();
                if (op==OPERATOR.AND)
                    return new BoolValue(n1&&n2);
                if (op==OPERATOR.OR)
                    return new BoolValue(n1||n2);

            }

        }
        throw new InvalidTypeError(String.format("Invalid operation between %s and %s",e1.toString(),e2.toString()));
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        IType typ1, typ2;
        typ1=e1.typeCheck(typeEnv);
        typ2=e2.typeCheck(typeEnv);
        if (typ1.equals((new BoolType()))){
            if (typ2.equals(new BoolType())){
                return new BoolType();
            }else
                throw new InvalidTypeError("second operand is not boolean");
        }
        else{
            throw new InvalidTypeError("first operand is not boolean");
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
