package Repo;
import Exceptions.ListError;
import Model.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repo implements IRepo {

    List<PrgState> myPrgStates;
    private final String logFileName;

    public Repo(String _logFileName) {
        logFileName=_logFileName;
        myPrgStates = new ArrayList<>();
    }

    @Override
    public List<PrgState> getPrograms() {
        return myPrgStates;
    }

    @Override
    public PrgState getPrgAtPos(int pos) {
        return myPrgStates.get(pos);
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgExe(PrgState prg) throws IOException{
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFileName, true)));
        logFile.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        logFile.println(prg);
        logFile.close();
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        myPrgStates=prgList;
    }


}
