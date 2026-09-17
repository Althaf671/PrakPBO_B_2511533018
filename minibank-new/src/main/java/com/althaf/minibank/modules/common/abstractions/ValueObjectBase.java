package com.althaf.minibank.modules.common.abstractions;

import java.util.List;
import java.util.Objects;

public abstract class ValueObjectBase {

    protected abstract List<Object> getAtomicValues();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ValueObjectBase other)) {
            return false;
        }

        return getAtomicValues().equals(other.getAtomicValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAtomicValues().toArray());
    }
}