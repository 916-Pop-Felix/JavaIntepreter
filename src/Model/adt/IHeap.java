package Model.adt;

import Exceptions.DictError;
import Model.value.IValue;

import java.util.Map;

public interface IHeap {
    Map<Integer, IValue> getContent();
    void setContent(Map<Integer,IValue> newValue);
    Integer addValue(IValue val);
    void update(Integer pos, IValue updatedValue) throws DictError;
    IValue get(Integer pos) throws DictError;
    String toString();
}
