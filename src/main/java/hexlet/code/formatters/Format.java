package hexlet.code.formatters;

import hexlet.code.Record;
import java.util.StringJoiner;
import java.util.stream.Collector;

public enum Format {
    PLAIN(Plain.COLLECTOR),
    STYLISH(Stylish.COLLECTOR),
    JSON(Json.COLLECTOR);

    Format(final Collector<Record, StringJoiner, String> passedCollector) {
        this.collector = passedCollector;
    }

    private final Collector<Record, StringJoiner, String> collector;

    public Collector<Record, StringJoiner, String> getCollector() {
        return collector;
    }
}
