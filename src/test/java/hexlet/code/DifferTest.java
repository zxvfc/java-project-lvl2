package hexlet.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static hexlet.code.formatters.Format.JSON;
import static hexlet.code.formatters.Format.PLAIN;
import static hexlet.code.formatters.Format.STYLISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @MethodSource("data")
    @ParameterizedTest(name = "{0} {1}")
    public final void test(final String formatName,
                     final String testName,
                     final String fileName1,
                     final String fileName2) throws IOException {
        final File file1 = getFileByName(fileName1);
        final File file2 = getFileByName(fileName2);

        final String expected = getExpectedFor(formatName, testName);
        final String result = Differ.generate(file1.getPath(), file2.getPath(), formatName);
        System.out.println(result);
        assertEquals(expected, result);
    }

    private String getExpectedFor(final String formatName, final String testName) throws FileNotFoundException {
        final String path = ("expected/" + formatName + " " + testName).toLowerCase().replace(' ', '_');
        return new BufferedReader(new FileReader(getFileByName(path)))
                .lines()
                .collect(Collectors.joining("\n"));
    }

    private File getFileByName(final String name) {
        return new File(getClass().getClassLoader().getResource(name).getFile());
    }

    private static Stream<Object[]> data() {
        return Stream.of(
                new Object[] {STYLISH.name(), "Simple", "file1.json", "file2.json"},
                new Object[] {STYLISH.name(), "Opposite", "file2.json", "file1.json"},
                new Object[] {STYLISH.name(), "Nested", "file2.yml", "file3.yml"},
                new Object[] {STYLISH.name(), "Nested Opposite", "file3.yml", "file2.yml"},

                new Object[] {PLAIN.name(), "Simple", "file1.json", "file2.json"},
                new Object[] {PLAIN.name(), "Opposite", "file2.json", "file1.json"},
                new Object[] {PLAIN.name(), "Nested", "file2.yml", "file3.yml"},
                new Object[] {PLAIN.name(), "Nested Opposite", "file3.yml", "file2.yml"},

                new Object[] {JSON.name(), "Simple", "file1.json", "file2.json"},
                new Object[] {JSON.name(), "Opposite", "file2.json", "file1.json"},
                new Object[] {JSON.name(), "Nested", "file2.yml", "file3.yml"},
                new Object[] {JSON.name(), "Nested Opposite", "file3.yml", "file2.yml"}
        );
    }
}
