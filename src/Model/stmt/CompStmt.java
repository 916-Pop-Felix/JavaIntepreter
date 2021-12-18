package Model.stmt;

import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;

public class CompStmt implements IStmt{
    IStmt first,second;

    public CompStmt(IStmt _first, IStmt _second) {
        first = _first;
        second = _second;
    }

    @Override
    public String toString() {
        return first.toString() + ";\n" + second.toString();
    }

    @Override
    public PrgState execute(PrgState state){
        IStack<IStmt> stk=state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}
