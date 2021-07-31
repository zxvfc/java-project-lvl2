package hexlet.code.formatters;

import hexlet.code.Record;
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
            joiner.add(ADDED_TEMPLATE.formatted(record.getName(), record.getValueNow()));
        }
        if (record.isUpdated()) {
            joiner.add(UPDATED_TEMPLATE.formatted(
                    record.getName(),
                    record.getValueNow(),
                    record.getValueWas())
            );
        }
    }
}
