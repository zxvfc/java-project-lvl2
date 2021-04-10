package hexlet.code;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

public class Differ {

    private static final String DELIMITER = "\n\t";
    private static final String PREFIX = "{\n\t";
    private static final String SUFFIX = "\n}";

    public static String generate(final File file1, final File file2) throws IOException {
        return getDiff(file1, file2)
                     .flatMap(Differ::unwrapChanges)
                     .filter(Objects::nonNull)
                     .collect(joining(DELIMITER, PREFIX, SUFFIX));
    }

    private static Stream<Changes> getDiff(final File file1, final File file2) throws IOException {
        final var parser = new Parser();
        final var firstFileContent = parser.parse(file1);
        final var secondFileContent = parser.parse(file2);

        return concat(firstFileContent.keySet().stream(), secondFileContent.keySet().stream())
                .distinct()
                .map(recordName -> new Changes(recordName, firstFileContent.get(recordName), secondFileContent.get(recordName)))
                .sorted(comparing(Changes::getRecordName));
    }

    private static Stream<String> unwrapChanges(final Changes changes) {
        return Stream.of(changes.toStringWas(), changes.toStringNow());
    }
}
