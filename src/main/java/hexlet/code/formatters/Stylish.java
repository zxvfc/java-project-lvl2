package hexlet.code.formatters;

import hexlet.code.Record;
import java.util.StringJoiner;
import java.util.stream.Collector;

public final class Stylish {

    private static final String PREFIX =    "{\n  ";
    private static final String SUFFIX =    "\n}";
    private static final String DELIMITER = "\n  ";

    private static final String DELETED_CHANGED_PREFIX =    "- ";
    private static final String ADDED_CHANGED_PREFIX =      "+ ";
    private static final String NAME_VALUE_DELIMITER =      ": ";
    private static final String NO_CHANGES_PREFIX =         "  ";

    public static final Collector<Record, StringJoiner, String> COLLECTOR = Collector.of(
            () -> new StringJoiner(DELIMITER, PREFIX, SUFFIX),
            Stylish::apply,
            (stringJoiner, other) -> {
                System.out.println("s1: " + stringJoiner.toString());
                System.out.println("s2: " + other.toString());
                return stringJoiner.merge(other);
            },
            stringJoiner -> {
                String str = stringJoiner.toString();
                System.out.println("String: " + str);
                return str;
            }
    );

    private static void apply(final StringJoiner joiner, final Record record) {
        if (record.isUpdated()) {
            joiner.add(DELETED_CHANGED_PREFIX + getWasWithName(record))
                  .add(ADDED_CHANGED_PREFIX + getNowWithName(record));
        } else if (record.isRemoved()) {
            joiner.add(DELETED_CHANGED_PREFIX + getWasWithName(record));
        } else if (record.isAdded()) {
            joiner.add(ADDED_CHANGED_PREFIX + getNowWithName(record));
        } else {
            joiner.add(NO_CHANGES_PREFIX + getWasWithName(record));
        }
    }

    private static String getWasWithName(final Record record) {
        return record.getName() + NAME_VALUE_DELIMITER + record.getValueWas();
    }

    private static String getNowWithName(final Record record) {
        return record.getName() + NAME_VALUE_DELIMITER + record.getValueNow();
    }
}
