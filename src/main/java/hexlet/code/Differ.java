package hexlet.code;

import hexlet.code.formatters.Format;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Stream.concat;

public final class Differ {

    public static String generate(final String filePath1, final String filePath2) throws IOException {
        return generate(filePath1, filePath2, Format.STYLISH.name()).toLowerCase();
    }

    public static String generate(final String filePath1,
                                  final String filePath2,
                                  final String formatName) throws IOException {
        return getDiff(filePath1, filePath2).collect(Formatter.format(formatName));
    }

    private static Stream<Record> getDiff(final String filePath1, final String filePath2) throws IOException {
        final var parser = new Parser();

        final var file1 = Paths.get(filePath1).toFile();
        final var firstFileContent = parser.parse(file1);
        System.out.println("File1 as map:" + firstFileContent);

        final var file2 = Paths.get(filePath2).toFile();
        final var secondFileContent = parser.parse(file2);
        System.out.println("File2 as map:" + secondFileContent);

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
}
