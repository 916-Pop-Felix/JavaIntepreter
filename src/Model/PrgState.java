package Model;
import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.List;
import Model.stmt.IStmt;
import Model.value.IValue;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<String> out;
    IStmt originalProgram; //optional field, but good to have


    public PrgState(IStack<IStmt> _exe,IDict<String, IValue> _sym,IList<String> _out,IStmt _org){
        exeStack=_exe;
        symTable=_sym;
        out=_out;
        originalProgram=_org;
    }

    public IList<String> getOutput() {
        return this.out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IStack<IStmt> getExeStack(){return this.exeStack;}

}