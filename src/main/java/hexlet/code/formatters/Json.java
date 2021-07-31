package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Record;
import java.util.StringJoiner;
import java.util.stream.Collector;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.*;
import static com.fasterxml.jackson.annotation.PropertyAccessor.IS_GETTER;

public final class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper().setVisibility(IS_GETTER, NONE);

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
