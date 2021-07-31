package hexlet.code;

import java.util.Objects;

public final class Record {

    private final String name;

    private final Object valueWas;

    private final Object valueNow;

    public Record(final String recordName, final Object recordValueWas, final Object recordValueNow) {
        this.name = recordName;
        this.valueWas = recordValueWas;
        this.valueNow = recordValueNow;
    }

    public String getName() {
        return name;
    }

    public boolean isUpdated() {
        return !isRemoved() && !isAdded() && !Objects.equals(valueWas, valueNow);
    }

    public boolean isRemoved() {
        return valueWas != null && valueNow == null;
    }

    public boolean isAdded() {
        return valueWas == null && valueNow != null;
    }

    public Object getValueWas() {
        return valueWas;
    }

    public Object getValueNow() {
        return valueNow;
    }
}
