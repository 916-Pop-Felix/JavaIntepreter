package Model;

import Model.adt.IDict;
import Model.adt.IList;
import Model.adt.IStack;
import Model.adt.List;
import Model.stmt.IStmt;
import Model.value.IValue;

import java.io.BufferedReader;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<String> out;
    IStmt originalProgram; //optional field, but good to have
    IDict<String, BufferedReader> fileTable;

    public PrgState(IStack<IStmt> _exe, IDict<String, IValue> _sym, IList<String> _out, IDict<String, BufferedReader> _file, IStmt _org) {
        exeStack = _exe;
        exeStack.push(_org);
        symTable = _sym;
        out = _out;
        originalProgram = _org;
        fileTable = _file;
    }

    public IList<String> getOutput() {
        return this.out;
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IDict<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    @Override
    public String toString() {
        return String.format("%s------EXE_STACK------\n%s------SYM_TABLE------\n%s------OUT------\n%s------FILE_TABLE------\n",
                exeStack.toString(),
                symTable.toString(),
                out.toString(),
                fileTable.toString());
    }

}