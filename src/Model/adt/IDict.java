package Model.adt;

import Exceptions.DictError;
import Model.value.IValue;

import java.util.Map;

public interface IDict<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2) throws DictError;
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    void remove(T1 v1) throws DictError;
    Map<T1, T2> getContent();
    IDict<T1,T2> copy();
    String toString();
}
