package hexlet.code.formatters;

import hexlet.code.Record;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;

import static hexlet.code.State.UNCHANGED;

public final class Plain {

    private static final String REMOVED_TEMPLATE = "Property %s was removed";
    private static final String ADDED_TEMPLATE = "Property %s was added with value: %s";
    private static final String UPDATED_TEMPLATE = "Property %s was updated. From %s to %s";

    public static final Collector<Record, StringJoiner, String> COLLECTOR = Collector.of(
                () -> new StringJoiner("\n"),
                Plain::apply,
                StringJoiner::merge,
                StringJoiner::toString
        );

    private static void apply(final StringJoiner joiner, final Record record) {
        if (record.state() == UNCHANGED) {
            return;
        }

        final var fieldDiff = switch (record.state()) {
            case REMOVED -> REMOVED_TEMPLATE.formatted(getNameWithQuotes(record));
            case ADDED -> ADDED_TEMPLATE.formatted(getNameWithQuotes(record), getPreparedNow(record));
            case CHANGED -> UPDATED_TEMPLATE.formatted(
                    getNameWithQuotes(record),
                    getPreparedWas(record),
                    getPreparedNow(record));
            default -> "";
        };

        joiner.add(fieldDiff);
    }

    private static String getNameWithQuotes(final Record record) {
        return "'" + record.name() + "'";
    }

    private static Object getPreparedWas(final Record record) {
        return handleComplexValues(record.valueWas());
    }

    private static Object getPreparedNow(final Record record) {
        return handleComplexValues(record.valueNow());
    }

    private static Object handleComplexValues(final Object value) {
        return value instanceof Object[] || value instanceof Collection || value instanceof Map
                ? "[complex value]"
                : value instanceof String
                ? "'" + value + "'"
                : value;
    }
}
