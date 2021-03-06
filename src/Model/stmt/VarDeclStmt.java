package Model.stmt;


import Exceptions.InterpreterError;
import Exceptions.InvalidTypeError;
import Exceptions.VarAlreadyDefined;
import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;
import Model.value.IValue;

public class VarDeclStmt implements IStmt{
    String name;
    IType type;

    public VarDeclStmt(String name, IType type){
        this.name = name;
        this.type = type;
    }


    @Override
    public PrgState execute(PrgState state) throws InterpreterError, VarAlreadyDefined {
        IDict<String,IValue> symTable=state.getSymTable();
        if (!symTable.isDefined(name)){
            symTable.add(name,type.defaultValue());
            return null;
        }
        throw new VarAlreadyDefined(String.format("Variable %s already declared",name));

    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws InvalidTypeError {
        typeEnv.add(name,type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("%s %s",type.toString(),name);
    }
}
