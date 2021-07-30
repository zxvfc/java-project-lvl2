package hexlet.code.formatters;

import hexlet.code.Changes;
import java.util.StringJoiner;
import java.util.stream.Collector;

public final class Stylish {

    private static final String PREFIX =    "{\n\t";
    private static final String SUFFIX =    "\n}";
    private static final String DELIMITER = "\n\t";

    private static final String DELETED_CHANGED_PREFIX =    "- ";
    private static final String ADDED_CHANGED_PREFIX =      "+ ";
    private static final String NAME_VALUE_DELIMITER =      ": ";
    private static final String NO_CHANGES_PREFIX =         "  ";

    public static final Collector<Changes, StringJoiner, String> COLLECTOR = Collector.of(
            () -> new StringJoiner(DELIMITER, PREFIX, SUFFIX),
            Stylish::apply,
            StringJoiner::merge,
            StringJoiner::toString
    );

    private static void apply(StringJoiner joiner, Changes changes) {
        if (changes.isUpdated()) {
            joiner.add(DELETED_CHANGED_PREFIX + getWasWithName(changes))
                  .add(ADDED_CHANGED_PREFIX + getNowWithName(changes));
        } else if (changes.isRemoved()) {
            joiner.add(DELETED_CHANGED_PREFIX + getWasWithName(changes));
        } else if (changes.isAdded()) {
            joiner.add(ADDED_CHANGED_PREFIX + getNowWithName(changes));
        } else {
            joiner.add(NO_CHANGES_PREFIX + getWasWithName(changes));
        }
    }

    private static String getWasWithName(final Changes changes) {
        return changes.getRecordName() + NAME_VALUE_DELIMITER + changes.getRecordWas().toString();
    }

    private static String getNowWithName(final Changes changes) {
        return changes.getRecordName() + NAME_VALUE_DELIMITER + changes.getRecordNow().toString();
    }
}
