package hexlet.code.formatters;

import hexlet.code.Record;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;

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
        if (record.isRemoved()) {
            joiner.add(REMOVED_TEMPLATE.formatted(record.getName()));
        }
        if (record.isAdded()) {
            joiner.add(ADDED_TEMPLATE.formatted(record.getName(), getPreparedNow(record)));
        }
        if (record.isUpdated()) {
            joiner.add(UPDATED_TEMPLATE.formatted(
                    record.getName(),
                    getPreparedWas(record),
                    getPreparedNow(record))
            );
        }
    }

    private static Object getPreparedWas(final Record record) {
        return handleComplexValues(record.getValueWas());
    }

    private static Object getPreparedNow(final Record record) {
        return handleComplexValues(record.getValueNow());
    }

    private static Object handleComplexValues(final Object value) {
        return value instanceof Object[] || value instanceof Collection || value instanceof Map
                ? "[complex value]"
                : value;
    }
}
