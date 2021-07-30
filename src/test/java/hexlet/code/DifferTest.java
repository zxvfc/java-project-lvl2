package hexlet.code;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    private static final String EXPECTED = """
            {
            \t- follow: false
            \t  host: hexlet.io
            \t- proxy: 123.234.53.22
            \t- timeout: 50
            \t+ timeout: 20
            \t+ verbose: true
            }""";

    private static final String EXPECTED_OPPOSITE = """
            {
            \t+ follow: false
            \t  host: hexlet.io
            \t+ proxy: 123.234.53.22
            \t- timeout: 20
            \t+ timeout: 50
            \t- verbose: true
            }""";

    private static final String EXPECTED_NESTED = """
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

    private static final String EXPECTED_NESTED_OPPOSITE = """
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

    private final File file1 = getFileWithName("file1.json");
    private final File file2 = getFileWithName("file2.json");

    private final File nestedFile1 = getFileWithName("file2.yml");
    private final File nestedFile2 = getFileWithName("file3.yml");

    private File getFileWithName(final String name) {
        return new File(getClass().getClassLoader().getResource(name).getFile());
    }

    @Test
    void generate() throws IOException {
        final String result = Differ.generate(file1, file2);
        assertEquals(EXPECTED, result);

        final String resultOpposite = Differ.generate(file2, file1);
        assertEquals(EXPECTED_OPPOSITE, resultOpposite);
    }

    @Test
    void diffForFilesWithNestedFields() throws IOException {
        final String result = Differ.generate(nestedFile1, nestedFile2);
        assertEquals(EXPECTED_NESTED, result);

        final String resultOpposite = Differ.generate(nestedFile2, nestedFile1);
        assertEquals(EXPECTED_NESTED_OPPOSITE, resultOpposite);
    }
}
