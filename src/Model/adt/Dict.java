package Model.adt;
import Exceptions.DictError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dict<T1,T2> implements IDict<T1,T2> {
    Map<T1, T2> dictionary;

    public Dict() {
        dictionary = new HashMap<T1,T2>();
    }


    @Override
    public void add(T1 v1, T2 v2) {
        dictionary.put(v1,v2);
    }

    @Override
    public void update(T1 v1, T2 v2) throws DictError{
        if (!isDefined(v1)){
            throw new DictError("ID not found in dict");
        }
        dictionary.replace(v1,v2);
    }

    @Override
    public T2 lookup(T1 id) {
        return dictionary.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    @Override
    public void remove(T1 v1) throws DictError {
        if (!isDefined(v1)){
            throw new DictError("ID not found in dict");
        }
        dictionary.remove(v1);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    public IDict<T1, T2> copy() {
        IDict<T1, T2> toReturn = new Dict<>();
        for (T1 key : keySet())
            toReturn.add(key, lookup(key));
        return toReturn;
    }

    private Set<T1> keySet() {
        return dictionary.keySet();
    }

    @Override
    public String toString() {
        return Arrays.toString(dictionary.entrySet().toArray());
    }
}
