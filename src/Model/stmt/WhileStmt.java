package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.types.BoolType;
import Model.types.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class WhileStmt implements IStmt{
    Exp exp;
    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws InterpreterError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, FileError {
        IValue value=exp.eval(state.getSymTable(),state.getHeap());
        if (!value.getType().equals(new BoolType()))
            throw new InvalidTypeError(String.format("%s needs to be of type bool",exp));
        BoolValue boolValue=(BoolValue) value;
        if (boolValue.getValue()){
            state.getExeStack().push(this);
            state.getExeStack().push(stmt);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        if (exp.typeCheck(typeEnv).equals(new BoolType())){
            stmt.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        throw new InvalidTypeError(String.format("%s needs to be of type bool",exp));
    }

    @Override
    public String toString() {
        return String.format("while(%s){\n%s;\n}",exp,stmt);
    }
}
