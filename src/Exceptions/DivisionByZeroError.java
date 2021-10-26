package Exceptions;

public class DivisionByZeroError extends Exception{
    public DivisionByZeroError(){super();}
    public DivisionByZeroError(String msg){
        super(msg);
    }
}
