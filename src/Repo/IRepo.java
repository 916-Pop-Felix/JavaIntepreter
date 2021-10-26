package Repo;
import Exceptions.ListError;
import Model.PrgState;
import Model.adt.IList;

public interface IRepo {
    void addPrg(PrgState newPrg);
    PrgState getCrtPrg() throws ListError;
    IList<PrgState> getPrograms();
    PrgState getPrgAtPos(int pos);
}
