package View.commands;

import Controller.Controller;
import Exceptions.*;

public class RunCommand extends Command{

    private final Controller ctrl;
    public RunCommand(String key,String desc,Controller _ctrl){
        super(key,desc);
        this.ctrl=_ctrl;
    }
    @Override
    public void execute() throws InterpreterError, ListError, StackError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined{
        try {
            ctrl.allStep();
        }
        catch (InterpreterError | VarAlreadyDefined |   ListError | StackError | DictError | InvalidTypeError | DivisionByZeroError | VarNotDefinedError e){
            System.out.println(e);
        }
    }
}
