package hexlet.code;

import hexlet.code.formatters.Format;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

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
                                              !firstFileContent.containsKey(recordName),
                                              !secondFileContent.containsKey(recordName)
                                              )
                ).sorted(comparing(Record::getName));
    }

    private static Map<String, Object> readFileToMap(final String filePath) throws IOException {
        var extension = filePath.substring(filePath.lastIndexOf('.'));
        var content = Files.readString(Paths.get(filePath));
        return Parser.parse(content, extension);
    }
}
