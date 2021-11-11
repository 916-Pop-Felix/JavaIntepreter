package Model.stmt;

import Exceptions.InterpreterError;
import Model.PrgState;
import Model.adt.IStack;

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
        return state;
    }
}
