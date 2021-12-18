package Model.stmt;

import Exceptions.DictError;
import Exceptions.DivisionByZeroError;
import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
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

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        IType typexp=exp.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())){
            thenS.typeCheck(typeEnv.copy());
            elseS.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new InvalidTypeError("Bool type absent in if statement");
    }
}
