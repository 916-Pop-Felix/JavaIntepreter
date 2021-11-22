package Model.value;

import Model.types.IType;
import Model.types.RefType;

public class RefValue implements IValue{
    private final int addr;
    private final IType location;

    public RefValue(int addr, IType location) {
        this.addr = addr;
        this.location = location;
    }

    public int getAddr() {
        return addr;
    }

    public IType getLocation(){
        return location;
    }

    @Override
    public IType getType() {
        return new RefType(location);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(addr,location);
    }

    @Override
    public String toString() {
        return String.format("(%d ,%s)", addr, location);
    }
}
