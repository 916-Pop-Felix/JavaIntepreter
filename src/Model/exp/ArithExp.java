package Model.exp;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.adt.IDict;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
public class ArithExp implements Exp{
    OPERATOR op;
    Exp e1, e2;



    //constructor

    public ArithExp(OPERATOR _op, Exp _e1, Exp _e2){
        op=_op;
        e1=_e1;
        e2=_e2;
    }

    public IValue eval(IDict<String,IValue> tbl) throws InterpreterError, DivisionByZeroError, InvalidTypeError {
        IValue v1,v2;
        v1=e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (op == OPERATOR.ADD)
                    return new IntValue(n1 + n2);
                if (op == OPERATOR.SUB)
                    return new IntValue(n1 - n2);
                if (op == OPERATOR.MUL)
                    return new IntValue(n1 * n2);
                if (op == OPERATOR.DIV)
                    if (n2 == 0)
                        throw new DivisionByZeroError("division by zero");
                    else
                        return new IntValue(n1 / n2);
            } else {
                throw new InvalidTypeError("second operand is not an integer");
            }
        }
        throw new InvalidTypeError("first operand is not an integer");
    }

    public OPERATOR getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }


    public String toString() { return e1.toString() + op.label + e2.toString(); }
}
