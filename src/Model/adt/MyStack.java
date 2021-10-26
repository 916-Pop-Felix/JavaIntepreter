package Model.adt;
import Exceptions.StackError;

import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    Stack<T> stack;

    public MyStack(){
        stack=new Stack<T>();
    }

    @Override
    public T pop() throws StackError{
        if (stack.isEmpty())
            throw new StackError("Stack is empty!");
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

    @Override
    public T top() throws StackError {
        return stack.peek();
    }
}
