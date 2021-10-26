package Model.adt;

import Exceptions.ListError;

public interface IList<T> {
    void add(T v);
    T pop() throws ListError;
    String toString();
    boolean empty();
    void clear();
    T get(int pos);
}
