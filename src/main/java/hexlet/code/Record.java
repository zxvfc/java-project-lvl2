package hexlet.code;

import java.util.Objects;

public final class Record {

    private final String name;

    private final Object valueWas;

    private final Object valueNow;

    private final boolean isAdded;

    private final boolean isRemoved;

    public Record(final String recordName,
                  final Object recordValueWas,
                  final Object recordValueNow,
                  final boolean isValueAdded,
                  final boolean isValueRemoved) {
        this.name = recordName;
        this.valueWas = recordValueWas;
        this.valueNow = recordValueNow;
        this.isAdded = isValueAdded;
        this.isRemoved = isValueRemoved;
    }

    public String getName() {
        return name;
    }

    public boolean isUpdated() {
        return !isRemoved() && !isAdded() && !Objects.equals(valueWas, valueNow);
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public Object getValueWas() {
        return valueWas;
    }

    public Object getValueNow() {
        return valueNow;
    }
}
