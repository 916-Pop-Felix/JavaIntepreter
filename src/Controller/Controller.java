package Controller;
import Exceptions.InterpreterError;
import Model.PrgState;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Repo.Repo;

public class Controller {

    Repo repo;

    public Controller(Repo _repo){repo=_repo;}

    public void addProgram(PrgState newPrg) {
        repo.addPrg(newPrg);
    }

    public PrgState oneStep(PrgState state) throws InterpreterError {
        IStack<IStmt> stk=state.getExeStack();
        if (stk.isEmpty()){
            throw new InterpreterError("PrgState stack is empty!");
        }
        IStmt crtStmt=stk.pop();
        //System.out.println(crtStmt);
        return crtStmt.execute(state);
    }

    public void allStep() throws InterpreterError{
        PrgState prg=repo.getCrtPrg();
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
        }
        System.out.println(prg.getOutput().toString());
    }
}