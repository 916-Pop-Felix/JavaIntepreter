package Exceptions;

public class InterpreterError extends Exception{
    public InterpreterError(){
        super();
    }
    public InterpreterError(String msg){
        super(msg);
    }
}
