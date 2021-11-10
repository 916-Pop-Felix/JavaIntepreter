package View.commands;

import Exceptions.*;

public abstract class Command {
    private final String key, description;
    public Command(String key, String description)
    { this.key = key; this.description = description;}
    public abstract void execute() throws InterpreterError, ListError, StackError, DictError, VarNotDefinedError, InvalidTypeError, DivisionByZeroError, VarAlreadyDefined;
    public String getKey(){return key;}
    public String getDescription(){return description;}
}
