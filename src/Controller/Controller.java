package Controller;
import Exceptions.*;
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

    public PrgState oneStep(PrgState state) throws InterpreterError, StackError, DictError,
            VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined {
        IStack<IStmt> stk=state.getExeStack();
        if (stk.isEmpty()){
            throw new InterpreterError("PrgState stack is empty!");
        }
        IStmt crtStmt=stk.pop();
       // if (!stk.isEmpty())
            System.out.println(state);
        return crtStmt.execute(state);
    }

    public void allStep() throws InterpreterError, ListError, StackError, DictError,
            VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined {
        PrgState prg=repo.getCrtPrg();
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
        }
        System.out.println(prg.getOutput().toString());
    }
}
