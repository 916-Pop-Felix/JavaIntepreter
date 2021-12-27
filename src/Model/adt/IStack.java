package Model.adt;

import Exceptions.StackError;

import java.util.List;


public interface IStack<T> {

    T pop() throws StackError;
    void push(T v);
    boolean isEmpty();
    T top() throws StackError;
    String toString();
    List<T> get();
}

