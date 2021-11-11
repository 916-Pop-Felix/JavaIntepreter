package Model.value;

import Model.types.IType;
import Model.types.StringType;

import java.util.Objects;

public class StringValue implements IValue{

    String value;

    public StringValue(){this.value="";}

    public StringValue(String _value){value=_value;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(value, that.value);
    }


    @Override
    public IType getType() {
        return new StringType();
    }

    public String getValue(){return this.value;}

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public String toString() {
        return String.format("\"%s\"",value);
    }
}
