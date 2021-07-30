package hexlet.code;

import java.util.Objects;

public final class Changes {

    private final String recordName;

    private final Object recordWas;

    private final Object recordNow;

    public Changes(final String recordNameIncome, final Object recordWasIncome, final Object recordNowIncome) {
        this.recordName = recordNameIncome;
        this.recordWas = recordWasIncome;
        this.recordNow = recordNowIncome;
    }

    public String getRecordName() {
        return recordName;
    }

    public boolean isUpdated() {
        return !isRemoved() && !isAdded() && !Objects.equals(recordWas, recordNow);
    }

    public boolean isRemoved() {
        return recordWas != null && recordNow == null;
    }

    public boolean isAdded() {
        return recordWas == null && recordNow != null;
    }

    public Object getRecordWas() {
        return recordWas;
    }

    public Object getRecordNow() {
        return recordNow;
    }
}
