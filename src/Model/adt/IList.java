package Model.adt;

import Exceptions.ListError;

import java.util.List;

public interface IList<T> {
    void add(T v);
    T pop() throws ListError;
    String toString();
    boolean empty();
    void clear();
    T get(int pos);
    boolean contains(T e);
    List<T> get();
}
