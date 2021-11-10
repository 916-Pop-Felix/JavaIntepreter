package Model.adt;

import Exceptions.ListError;

import java.util.Stack;

public class List<T> implements IList<T> {
    Stack<T> list;

    public List(){
        list=new Stack<T>();
    }

    @Override
    public void add(T v) {
        list.push(v);
    }

    @Override
    public T pop()  throws ListError {
        if (list.isEmpty())
            throw new ListError("Empty list!");
        return list.pop();
    }

    public T getFirstElement() {return this.list.peek();}

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    @Override
    public T get(int pos) {
        return this.list.get(pos);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
