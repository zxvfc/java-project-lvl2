package hexlet.code;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifferTest {

    private static final String EXPECTED = "{\n"
                                           + "\t- follow: false\n"
                                           + "\t  host: hexlet.io\n"
                                           + "\t- proxy: 123.234.53.22\n"
                                           + "\t- timeout: 50\n"
                                           + "\t+ timeout: 20\n"
                                           + "\t+ verbose: true\n"
                                           + "}";

    private static final String EXPECTED_OPPOSITE = "{\n"
                                                    + "\t+ follow: false\n"
                                                    + "\t  host: hexlet.io\n"
                                                    + "\t+ proxy: 123.234.53.22\n"
                                                    + "\t- timeout: 20\n"
                                                    + "\t+ timeout: 50\n"
                                                    + "\t- verbose: true\n"
                                                    + "}";

    private final File file1 = getFileWithName("file1.json");
    private final File file2 = getFileWithName("file2.json");

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
}
