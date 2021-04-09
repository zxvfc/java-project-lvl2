package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

public class Differ {

    private final static String DELIMITER = "\n\t";
    private final static String PREFIX = "{\n\t";
    private final static String SUFFIX = "\n}";

    public static String generate(final File filepath1, final File filepath2) throws IOException {
        return getChanges(filepath1, filepath2).flatMap(Differ::getStringStream)
                    .filter(Objects::nonNull)
                    .collect(joining(DELIMITER, PREFIX, SUFFIX));
    }

    private static Stream<String> getStringStream(JsonChanges it) {
        return Stream.of(it.toStringWas(), it.toStringNow());
    }

    private static Stream<JsonChanges> getChanges(final File filepath1, final File filepath2) throws IOException {
        final var mapper = new ObjectMapper();
        final Map<String, Object> firstFileContent = mapper.readValue(filepath1, Map.class);
        final Map<String, Object> secondFileContent = mapper.readValue(filepath2, Map.class);

        return concat(firstFileContent.keySet().stream(), secondFileContent.keySet().stream())
                .distinct()
                .map(it -> new JsonChanges(it, firstFileContent.get(it), secondFileContent.get(it)))
                .sorted(comparing(JsonChanges::getRecordName));
    }
}
