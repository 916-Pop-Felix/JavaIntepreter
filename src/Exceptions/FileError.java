package Exceptions;

public class FileError extends Exception{
    public FileError(){super(); }
    public FileError(String msg){
        super(msg);
    }
}
