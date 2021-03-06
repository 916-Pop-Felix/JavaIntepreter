package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.types.IType;
import Model.value.IValue;

public class AssignStmt implements IStmt{

    String id;
    Exp expression;

    public AssignStmt(String _id, Exp _exp){
        this.id = _id;
        this.expression=_exp;

    }

    @Override
    public String toString(){
        return this.id + "=" + this.expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError {
        IStack<IStmt> stk=state.getExeStack();
        IDict<String, IValue> symTbl= state.getSymTable();

        if (symTbl.isDefined(id)) {
            IValue val = expression.eval(symTbl,state.getHeap() );
            IType typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else
                throw new InvalidTypeError("declared type of variable" + id + " the assigned expression do not match");
        }
        else
            throw new VarNotDefinedError("the used variable" +id + " was not declared before");
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        IType typevar=typeEnv.lookup(id);
        IType typexp= expression.typeCheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new InvalidTypeError("declared type of variable" + id + " the assigned expression do not match");
    }

}
