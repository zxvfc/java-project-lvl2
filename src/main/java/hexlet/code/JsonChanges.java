package hexlet.code;

class JsonChanges {

    private static final String ADDED_CHANGED_PREFIX = "+ ";
    private static final String DELETED_CHANGED_PREFIX = "- ";
    private static final String NO_CHANGES_PREFIX = "  ";
    private static final String NAME_VALUE_DELIMITER = ": ";

    private final String recordName;

    private final Object recordWas;

    private final Object recordNow;

    private final String prefixForWas;

    private final String prefixForNow;

    JsonChanges(final String recordNameIncome, final Object recordWasIncome, final Object recordNowIncome) {
        this.recordName = recordNameIncome;
        this.recordWas = recordWasIncome;
        this.recordNow = recordNowIncome;
        this.prefixForWas = changed() || deleted() ? DELETED_CHANGED_PREFIX : !added() ? NO_CHANGES_PREFIX : null;
        this.prefixForNow = changed() || added() ? ADDED_CHANGED_PREFIX : null;
    }

    public String getRecordName() {
        return recordName;
    }

    public boolean changed() {
        return !deleted() && !added() && !recordWas.equals(recordNow);
    }

    public boolean deleted() {
        return recordWas != null && recordNow == null;
    }

    public boolean added() {
        return recordWas == null && recordNow != null;
    }

    private String buildWasWithoutPrefix() {
        return recordName + NAME_VALUE_DELIMITER + recordWas;
    }

    private String buildNowWithoutPrefix() {
        return recordName + NAME_VALUE_DELIMITER + recordNow;
    }

    public String toStringWas() {
        return changed() || deleted() || !added()
                ? prefixForWas + buildWasWithoutPrefix()
                : null;
    }

    public String toStringNow() {
        return changed() || added()
                ? prefixForNow + buildNowWithoutPrefix()
                : null;
    }
}
