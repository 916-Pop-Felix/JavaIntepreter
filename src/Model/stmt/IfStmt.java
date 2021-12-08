package Model.stmt;

import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements IStmt{
    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp e,IStmt t, IStmt el){
        exp=e;
        thenS=t;
        elseS=el;
    }

    @Override
    public String toString() {
        return String.format("if (%s){\n%s\n}else{\n%s\n}",exp.toString(),thenS.toString(),elseS.toString());
    }

    public PrgState execute(PrgState state) throws InterpreterError, InvalidTypeError, DivisionByZeroError, DictError {
        IValue value=exp.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new BoolType())) {
            BoolValue cond=(BoolValue)value;
            if (cond.getValue()){
                state.getExeStack().push(thenS);
            }
            else{
                state.getExeStack().push(elseS);
            }
            return null;
        }
        throw new InvalidTypeError("Bool type absent in if statement");
    }
}
