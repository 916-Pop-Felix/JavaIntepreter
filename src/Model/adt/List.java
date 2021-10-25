package Model.adt;

import java.util.LinkedList;
import java.util.Queue;
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
    public T pop() {return list.pop();}

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
    public String toString() {
        return list.toString();
    }
}
