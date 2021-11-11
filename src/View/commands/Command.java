package View.commands;

import Exceptions.*;

import java.io.IOException;

public abstract class Command {
    private final String key, description;
    public Command(String key, String description)
    { this.key = key; this.description = description;}
    public abstract void execute() throws InterpreterError, ListError, StackError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined, IOException, FileError;
    public String getKey(){return key;}
    public String getDescription(){return description;}
}
