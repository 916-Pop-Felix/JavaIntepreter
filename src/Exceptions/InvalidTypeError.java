package Exceptions;

public class InvalidTypeError extends Exception{
    public InvalidTypeError(){super();}
    public InvalidTypeError(String msg){
        super(msg);
    }
}
