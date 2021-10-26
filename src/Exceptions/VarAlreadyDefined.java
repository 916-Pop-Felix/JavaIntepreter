package Exceptions;

public class VarAlreadyDefined extends Exception{
    public VarAlreadyDefined(){super();}
    public VarAlreadyDefined(String msg){
        super(msg);
    }
}
