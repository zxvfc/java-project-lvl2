package hexlet.code.formatters;

import hexlet.code.Record;
import java.util.StringJoiner;
import java.util.stream.Collector;

public final class Stylish {

    private static final String PREFIX =    "{\n  ";
    private static final String SUFFIX =    "\n}";
    private static final String DELIMITER = "\n  ";

    private static final String ADDED_TEMPLATE =       "+ %s: %s";
    private static final String DELETED_TEMPLATE =     "- %s: %s";
    private static final String UNCHANGED_TEMPLATE =   "  %s: %s";
    private static final String CHANGED_T = DELETED_TEMPLATE + DELIMITER + ADDED_TEMPLATE;

    public static final Collector<Record, StringJoiner, String> COLLECTOR = Collector.of(
            () -> new StringJoiner(DELIMITER, PREFIX, SUFFIX),
            Stylish::apply,
            StringJoiner::merge,
            StringJoiner::toString
    );

    private static void apply(final StringJoiner joiner, final Record record) {

        final var fieldDiff = switch (record.state()) {
            case ADDED -> ADDED_TEMPLATE.formatted(record.name(), record.valueNow());
            case REMOVED -> DELETED_TEMPLATE.formatted(record.name(), record.valueWas());
            case UNCHANGED -> UNCHANGED_TEMPLATE.formatted(record.name(), record.valueNow());
            case CHANGED -> CHANGED_T.formatted(record.name(), record.valueWas(), record.name(), record.valueNow());
        };

        joiner.add(fieldDiff);
    }
}
