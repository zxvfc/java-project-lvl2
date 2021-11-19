package hexlet.code;

import hexlet.code.formatters.Format;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static hexlet.code.State.ADDED;
import static hexlet.code.State.CHANGED;
import static hexlet.code.State.REMOVED;
import static hexlet.code.State.UNCHANGED;
import static java.util.Comparator.comparing;
import static java.util.stream.Stream.concat;

public final class Differ {

    public static String generate(final String filePath1, final String filePath2) throws IOException {
        return generate(filePath1, filePath2, Format.STYLISH.name());
    }

    public static String generate(final String filePath1,
                                  final String filePath2,
                                  final String formatName) throws IOException {
        return getDiff(filePath1, filePath2).collect(Formatter.format(formatName));
    }

    private static Stream<Record> getDiff(final String filePath1, final String filePath2) throws IOException {

        final var firstFileContent = readFileToMap(filePath1);
        final var secondFileContent = readFileToMap(filePath2);

        return concat(firstFileContent.keySet().stream(), secondFileContent.keySet().stream())
                .distinct()
                .map(recordName -> new Record(recordName,
                                              firstFileContent.get(recordName),
                                              secondFileContent.get(recordName),
                                              defineState(firstFileContent, secondFileContent, recordName)
                                              )
                ).sorted(comparing(Record::name));
    }

    private static Map<String, Object> readFileToMap(final String filePath) throws IOException {
        var extension = filePath.substring(filePath.lastIndexOf('.'));
        var content = Files.readString(Paths.get(filePath));
        return Parser.parse(content, extension);
    }

    private static State defineState(final Map<String, Object> content1,
                                     final Map<String, Object> content2,
                                     final String recordName) {
        return !content1.containsKey(recordName) ? ADDED
                : !content2.containsKey(recordName) ? REMOVED
                : Objects.equals(content1.get(recordName), content2.get(recordName)) ? UNCHANGED
                : CHANGED;
    }
}
