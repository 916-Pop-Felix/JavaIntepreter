package Model.exp;

import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelationExp implements Exp{
    Exp e1;
    Exp e2;
    OPERATOR op;

    public RelationExp(OPERATOR _op,Exp _e1, Exp _e2){
        e1=_e1;
        e2=_e2;
        op=_op;
    }

    @Override
    public IValue eval(IDict<String, IValue> tbl, IHeap heap) throws InterpreterError, DivisionByZeroError, InvalidTypeError, DictError {
        IValue v1,v2;
        v1=e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,heap );
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                return switch (op){
                    case EQUAL -> new BoolValue(n1==n2);
                    case NOTEQUAL -> new BoolValue(n1!=n2);
                    case LESSER -> new BoolValue(n1<n2);
                    case LESSOREQUAL -> new BoolValue(n1<=n2);
                    case GREATER -> new BoolValue(n1>n2);
                    case GRTOREQUAL -> new BoolValue(n1>=n2);
                    default -> throw new InvalidTypeError(String.format("%s is an invalid operator",op.getSign()));
                };
            } else {
                throw new InvalidTypeError("second operand is not an integer");
            }
        }
        throw new InvalidTypeError("first operand is not an integer");
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        IType typ1, typ2;
        typ1=e1.typeCheck(typeEnv);
        typ2=e2.typeCheck(typeEnv);
        if (typ1.equals((new IntType()))){
            if (typ2.equals(new IntType())){
                return new IntType();
            }else
                throw new InvalidTypeError("second operand is not an integer");
        }
        else{
            throw new InvalidTypeError("first operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return e1.toString() + op.label + e2.toString();
    }
}
