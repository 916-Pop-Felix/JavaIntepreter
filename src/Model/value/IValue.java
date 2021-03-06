package Model.value;

import Model.types.IType;

public interface IValue {
    IType getType();
    IType getLocation();
    IValue deepCopy();
}
