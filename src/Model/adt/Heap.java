package Model.adt;

import Exceptions.DictError;
import Model.value.IValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap{
    private final Map<Integer, IValue> map;
    private Integer freeValue;
    public Heap(Map<Integer, IValue> map) {
        this.map = map;
        freeValue=0;
    }

    public Heap(){
        this.map= new HashMap<>();
        this.freeValue=0;
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return this.map;
    }

    @Override
    public void setContent(Map<Integer, IValue> newValue) {
        map.clear();
        for (Integer i: newValue.keySet()){
            map.put(i,newValue.get(i));
        }
    }

    @Override
    public Integer addValue(IValue val) {
        freeValue++;
        map.put(freeValue,val);
        return freeValue;
    }

    @Override
    public void update(Integer pos, IValue updatedValue) throws DictError {
        if (!map.containsKey(pos))
            throw new DictError(String.format("Address %s was not found in the heap",pos));
        map.replace(pos, updatedValue);
    }

    @Override
    public IValue get(Integer pos) throws DictError {
        if (!map.containsKey(pos))
            throw new DictError(String.format("Address %s was not found in the heap",pos));
        return map.get(pos);
    }

    @Override
    public String toString() {
        return  Arrays.toString(map.entrySet().toArray());
    }
}
