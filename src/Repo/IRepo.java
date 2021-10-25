package Repo;
import Model.PrgState;
import Model.adt.IList;

public interface IRepo {
    void addPrg(PrgState newPrg);
    PrgState getCrtPrg();
    IList<PrgState> getPrograms();
    }
