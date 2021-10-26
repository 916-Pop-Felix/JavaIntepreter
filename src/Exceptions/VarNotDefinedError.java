package Exceptions;

public class VarNotDefinedError extends Exception{
    public VarNotDefinedError(){super();}
    public VarNotDefinedError(String msg){
        super(msg);
    }
}
