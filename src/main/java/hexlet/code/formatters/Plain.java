package hexlet.code.formatters;

import hexlet.code.Changes;
import java.util.StringJoiner;
import java.util.stream.Collector;

public final class Plain {

    private static final String REMOVED_TEMPLATE = "Property %s was removed";
    private static final String ADDED_TEMPLATE = "Property %s was added with value: %s";
    private static final String UPDATED_TEMPLATE = "Property %s was updated. From %s to %s";

    public static final Collector<Changes, StringJoiner, String> COLLECTOR = Collector.of(
                () -> new StringJoiner("\n"),
                Plain::apply,
                StringJoiner::merge,
                StringJoiner::toString
        );

    private static void apply(final StringJoiner joiner, final Changes changes) {
        if (changes.isRemoved()) {
            joiner.add(REMOVED_TEMPLATE.formatted(changes.getRecordName()));
        }
        if (changes.isAdded()) {
            joiner.add(ADDED_TEMPLATE.formatted(changes.getRecordName(), changes.getRecordNow()));
        }
        if (changes.isUpdated()) {
            joiner.add(UPDATED_TEMPLATE.formatted(changes.getRecordName(),
                                                            changes.getRecordNow(),
                                                            changes.getRecordWas())
            );
        }
    }
}
