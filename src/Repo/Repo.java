package Repo;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.List;

public class Repo implements IRepo {

    IList<PrgState> myPrgStates;

    public Repo() {
        myPrgStates = new List<PrgState>();
    }

    @Override
    public PrgState getCrtPrg() {
        return myPrgStates.pop();
    }

    @Override
    public IList<PrgState> getPrograms() {
        return myPrgStates;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }


}
