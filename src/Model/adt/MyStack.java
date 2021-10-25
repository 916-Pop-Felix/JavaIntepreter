package Model.adt;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    Stack<T> stack;

    public MyStack(){
        stack=new Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
