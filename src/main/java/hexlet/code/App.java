package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import picocli.CommandLine;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Help.Visibility.ALWAYS;
import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Command(name = "gendiff", description = "Compares two configuration files and shows a difference.")
public class App implements Callable<String> {

    private static final String DEFAULT_FORMAT = "stylish";

    @Parameters(index = "0", description = "path to first file")
    private File filepath1;

    @Parameters(index = "1", description = "path to second file")
    private File filepath2;

    @Option(names = { "-f", "--format" },
            defaultValue = DEFAULT_FORMAT,
            showDefaultValue = ALWAYS,
            description = "output format"
    ) private String format;

    @Option(names = { "-h", "--help" },
            usageHelp = true,
            description = "Show this help message and exit."
    ) private boolean help;

    @Option(names = { "-v", "--version" },
            description = "Print version information and exit."
    ) private boolean printVersion;

    public static void main(String[] args) {
        final var commandLineRunner = new CommandLine(new App());
        commandLineRunner.execute(args);

        final String result = commandLineRunner.getExecutionResult();
        System.out.println(result);
    }

    @Override
    public String call() throws IOException {
        return getChanges().flatMap(this::getStringStream)
                           .filter(Objects::nonNull)
                           .collect(joining("\n\t", "{\n\t", "\n}"));
    }

    private Stream<String> getStringStream(JsonChanges it) {
        return Stream.of(it.toStringWas(), it.toStringNow());
    }

    private Stream<JsonChanges> getChanges() throws IOException {
        final var mapper = new ObjectMapper();
        final Map<String, Object> firstFileContent = mapper.readValue(filepath1, Map.class);
        final Map<String, Object> secondFileContent = mapper.readValue(filepath2, Map.class);

        return concat(firstFileContent.keySet().stream(), secondFileContent.keySet().stream())
                .distinct()
                .map(it -> new JsonChanges(it, firstFileContent.get(it), secondFileContent.get(it)))
                .sorted(comparing(JsonChanges::getRecordName));
    }
}

class JsonChanges {

    private static final String ADDED_CHANGED_PREFIX = "+ ";
    private static final String DELETED_CHANGED_PREFIX = "- ";
    private static final String NO_CHANGES_PREFIX = "  ";
    private static final String NAME_VALUE_DELIMITER = ": ";

    private final String recordName;

    private final Object recordWas;

    private final Object recordNow;

    private final String prefixForWas;

    private final String prefixForNow;

    public JsonChanges(String recordName, Object recordWas, Object recordNow) {
        this.recordName = recordName;
        this.recordWas = recordWas;
        this.recordNow = recordNow;
        this.prefixForWas = changed() || deleted() ? DELETED_CHANGED_PREFIX : !added() ? NO_CHANGES_PREFIX : null;
        this.prefixForNow = changed() || added() ? ADDED_CHANGED_PREFIX : null;
    }

    public String getRecordName() {
        return recordName;
    }

    public boolean changed() {
        return !deleted() && !added() && !recordWas.equals(recordNow);
    }

    public boolean deleted() {
        return recordWas != null && recordNow == null;
    }

    public boolean added() {
        return recordWas == null && recordNow != null;
    }

    private String buildWasWithoutPrefix() {
        return recordName + NAME_VALUE_DELIMITER + recordWas;
    }

    private String buildNowWithoutPrefix() {
        return recordName + NAME_VALUE_DELIMITER + recordNow;
    }

    public String toStringWas() {
        return changed() || deleted() || !added()
                ? prefixForWas + buildWasWithoutPrefix()
                : null;
    }

    public String toStringNow() {
        return changed() || added()
                ? prefixForNow + buildNowWithoutPrefix()
                : null;
    }
}