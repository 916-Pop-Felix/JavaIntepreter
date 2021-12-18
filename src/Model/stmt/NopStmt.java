package Model.stmt;

import Exceptions.InvalidTypeError;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class NopStmt implements IStmt {

    public PrgState execute(PrgState state){
        return null;
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "no operation";
    }
}
