package hexlet.code;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Stream.concat;

public final class Differ {

    public static String generate(final File file1, final File file2, final String formatName) throws IOException {
        return getDiff(file1, file2).collect(Formatter.format(formatName));
    }

    private static Stream<Record> getDiff(final File file1, final File file2) throws IOException {
        final var parser = new Parser();
        final var firstFileContent = parser.parse(file1);
        final var secondFileContent = parser.parse(file2);

        return concat(firstFileContent.keySet().stream(), secondFileContent.keySet().stream())
                .distinct()
                .map(recordName -> new Record(recordName,
                                              firstFileContent.get(recordName),
                                              secondFileContent.get(recordName))
                ).sorted(comparing(Record::getName));
    }
}
