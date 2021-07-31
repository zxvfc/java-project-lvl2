package hexlet.code;

import hexlet.code.formatters.Format;
import java.util.StringJoiner;
import java.util.stream.Collector;

public final class Formatter {

    public static Collector<Record, StringJoiner, String> format(final String formatName) {
        return Format.valueOf(formatName.toUpperCase()).getCollector();
    }
}
