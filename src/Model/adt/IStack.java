package Model.adt;

import Exceptions.StackError;


public interface IStack<T> {

    T pop() throws StackError;
    void push(T v);
    boolean isEmpty();
    T top() throws StackError;
    String toString();
}

