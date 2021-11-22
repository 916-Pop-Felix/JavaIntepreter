package Controller;
import Exceptions.*;
import Model.PrgState;
import Model.adt.IStack;
import Model.adt.List;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.Repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    Repo repo;

    public Controller(Repo _repo){repo=_repo;}

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    private  Map<Integer, IValue> GarbageCollector(java.util.List<Integer> symTableAddr, Map<Integer,IValue> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private static java.util.List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues,Map<Integer,IValue> heap){
        java.util.List<Integer> finalHeap=new ArrayList<>();
        symTableValues.forEach(symTable -> symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .forEach(v -> {
                    while (v instanceof RefValue) {
                        finalHeap.add(((RefValue)v).getAddr());
                        v = heap.get(((RefValue)v).getAddr());
                    }
                }));
        return finalHeap;
    }
    public PrgState oneStep(PrgState state) throws InterpreterError, StackError, DictError,
            VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, IOException, FileError {
        IStack<IStmt> stk=state.getExeStack();
        if (stk.isEmpty()){
            throw new InterpreterError("PrgState stack is empty!");
        }
        IStmt crtStmt=stk.pop();
        repo.logPrgExe(state);
        return crtStmt.execute(state);
    }

    public void allStep() throws InterpreterError, ListError, StackError, DictError,
            VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, IOException, FileError {
        PrgState prg=repo.getCrtPrg();
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
            prg.getHeap().setContent(GarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values(),prg.getHeap().getContent()),
                    prg.getHeap().getContent()));
        }
        System.out.println(prg.getOutput().toString());
    }
}
