package Repo;
import Model.PrgState;
import java.io.IOException;
import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    List<PrgState> getPrograms();
    PrgState getPrgAtPos(int pos);
    void logPrgExe(PrgState prg) throws IOException;
    void setPrgList(List<PrgState> prgList);
}
