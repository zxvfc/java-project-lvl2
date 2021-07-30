package hexlet.code;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static hexlet.code.formatters.Format.PLAIN;
import static hexlet.code.formatters.Format.STYLISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    private static final String EXPECTED_STYLISH = """
            {
            \t- follow: false
            \t  host: hexlet.io
            \t- proxy: 123.234.53.22
            \t- timeout: 50
            \t+ timeout: 20
            \t+ verbose: true
            }""";

    private static final String EXPECTED_STYLISH_OPPOSITE = """
            {
            \t+ follow: false
            \t  host: hexlet.io
            \t+ proxy: 123.234.53.22
            \t- timeout: 20
            \t+ timeout: 50
            \t- verbose: true
            }""";

    private static final String EXPECTED_STYLISH_NESTED = """
            {
            \t  chars1: [a, b, c]
            \t- chars2: [d, e, f]
            \t+ chars2: false
            \t- checked: false
            \t+ checked: true
            \t+ default: [value1, value2]
            \t- id: 45
            \t- key1: value1
            \t+ key2: value2
            \t  numbers1: [1, 2, 3, 4]
            \t- numbers2: [2, 3, 4, 5]
            \t+ numbers2: [22, 33, 44, 55]
            \t- numbers3: [3, 4, 5]
            \t+ numbers4: [4, 5, 6]
            \t+ obj1: {nestedKey=value, isNested=true}
            \t- setting1: Some value
            \t+ setting1: Another value
            \t- setting2: 200
            \t+ setting2: 300
            \t- setting3: true
            \t+ setting3: none
            }""";

    private static final String EXPECTED_STYLISH_NESTED_OPPOSITE = """
            {
            \t  chars1: [a, b, c]
            \t- chars2: false
            \t+ chars2: [d, e, f]
            \t- checked: true
            \t+ checked: false
            \t- default: [value1, value2]
            \t+ id: 45
            \t+ key1: value1
            \t- key2: value2
            \t  numbers1: [1, 2, 3, 4]
            \t- numbers2: [22, 33, 44, 55]
            \t+ numbers2: [2, 3, 4, 5]
            \t+ numbers3: [3, 4, 5]
            \t- numbers4: [4, 5, 6]
            \t- obj1: {nestedKey=value, isNested=true}
            \t- setting1: Another value
            \t+ setting1: Some value
            \t- setting2: 300
            \t+ setting2: 200
            \t- setting3: none
            \t+ setting3: true
            }""";

    private static final String EXPECTED_PLAIN = """
            Property follow was removed
            Property proxy was removed
            Property timeout was updated. From 20 to 50
            Property verbose was added with value: true""";

    private static final String EXPECTED_PLAIN_OPPOSITE = """
            Property follow was added with value: false
            Property proxy was added with value: 123.234.53.22
            Property timeout was updated. From 50 to 20
            Property verbose was removed""";

    private static final String EXPECTED_PLAIN_NESTED = """
            Property chars2 was updated. From false to [d, e, f]
            Property checked was updated. From true to false
            Property default was added with value: [value1, value2]
            Property id was removed
            Property key1 was removed
            Property key2 was added with value: value2
            Property numbers2 was updated. From [22, 33, 44, 55] to [2, 3, 4, 5]
            Property numbers3 was removed
            Property numbers4 was added with value: [4, 5, 6]
            Property obj1 was added with value: {nestedKey=value, isNested=true}
            Property setting1 was updated. From Another value to Some value
            Property setting2 was updated. From 300 to 200
            Property setting3 was updated. From none to true""";

    private static final String EXPECTED_PLAIN_NESTED_OPPOSITE = """
            Property chars2 was updated. From [d, e, f] to false
            Property checked was updated. From false to true
            Property default was removed
            Property id was added with value: 45
            Property key1 was added with value: value1
            Property key2 was removed
            Property numbers2 was updated. From [2, 3, 4, 5] to [22, 33, 44, 55]
            Property numbers3 was added with value: [3, 4, 5]
            Property numbers4 was removed
            Property obj1 was removed
            Property setting1 was updated. From Some value to Another value
            Property setting2 was updated. From 200 to 300
            Property setting3 was updated. From true to none""";

    private final File file1 = getFileWithName("file1.json");
    private final File file2 = getFileWithName("file2.json");

    private final File nestedFile1 = getFileWithName("file2.yml");
    private final File nestedFile2 = getFileWithName("file3.yml");

    private File getFileWithName(final String name) {
        return new File(getClass().getClassLoader().getResource(name).getFile());
    }

    @Test
    void generateStylish() throws IOException {
        final String result = Differ.generate(file1, file2, STYLISH.name());
        assertEquals(EXPECTED_STYLISH, result);

        final String resultOpposite = Differ.generate(file2, file1, STYLISH.name());
        assertEquals(EXPECTED_STYLISH_OPPOSITE, resultOpposite);

        final String resultNested = Differ.generate(nestedFile1, nestedFile2, STYLISH.name());
        assertEquals(EXPECTED_STYLISH_NESTED, resultNested);

        final String resultNestedOpposite = Differ.generate(nestedFile2, nestedFile1, STYLISH.name());
        assertEquals(EXPECTED_STYLISH_NESTED_OPPOSITE, resultNestedOpposite);
    }

    @Test
    void generatePlain() throws IOException {
        final String result = Differ.generate(file1, file2, PLAIN.name());
        assertEquals(EXPECTED_PLAIN, result);

        final String resultOpposite = Differ.generate(file2, file1, PLAIN.name());
        assertEquals(EXPECTED_PLAIN_OPPOSITE, resultOpposite);

        final String resultNested = Differ.generate(nestedFile1, nestedFile2, PLAIN.name());
        assertEquals(EXPECTED_PLAIN_NESTED, resultNested);

        final String resultNestedOpposite = Differ.generate(nestedFile2, nestedFile1, PLAIN.name());
        assertEquals(EXPECTED_PLAIN_NESTED_OPPOSITE, resultNestedOpposite);
    }
}
