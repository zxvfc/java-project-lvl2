package hexlet.code.formatters;

import hexlet.code.Changes;
import java.util.StringJoiner;
import java.util.stream.Collector;

public enum Format {
    PLAIN(Plain.COLLECTOR),
    STYLISH(Stylish.COLLECTOR);

    Format(final Collector<Changes, StringJoiner, String> passedCollector) {
        this.collector = passedCollector;
    }

    private final Collector<Changes, StringJoiner, String> collector;

    public Collector<Changes, StringJoiner, String> getCollector() {
        return collector;
    }
}
