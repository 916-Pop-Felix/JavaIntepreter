package Controller;
import Exceptions.*;
import Model.PrgState;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.types.IType;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.Repo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    Repo repo;
    private ExecutorService executor;
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

    public java.util.List<PrgState> removeCompletedPrg(java.util.List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    void oneStepForAllPrg(java.util.List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgExe(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        java.util.List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());

        java.util.List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> { try { return future.get();}
                catch(InterruptedException | ExecutionException ie) {
                    System.out.println(ie);
                    return null;
                }}).filter(p -> p!=null)
                            .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgExe(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //Save the current programs in the repository
        repo.setPrgList(prgList);
    }

    public void runTypeChecker() throws InvalidTypeError,StackError{
        for (PrgState state: repo.getPrograms()){
            IDict<String, IType> typeEnv=new Dict<>();
            state.getExeStack().top().typeCheck(typeEnv);
        }
    }

    public void allStep() throws InterpreterError, ListError, StackError, DictError,
            VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, IOException, FileError, InterruptedException {
        runTypeChecker();
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        java.util.List<PrgState> prgList=removeCompletedPrg(repo.getPrograms());
        while(prgList.size() > 0){
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList=removeCompletedPrg(repo.getPrograms());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repo.setPrgList(prgList);
    }
}
