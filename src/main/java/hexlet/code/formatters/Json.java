package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Record;
import java.util.StringJoiner;
import java.util.stream.Collector;

public final class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final Collector<Record, StringJoiner, String> COLLECTOR = Collector.of(
            () -> new StringJoiner("\n"),
            Json::apply,
            StringJoiner::merge,
            StringJoiner::toString
    );

    private static void apply(StringJoiner joiner, Record record) {
        try {
            joiner.add(MAPPER.writeValueAsString(record));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
