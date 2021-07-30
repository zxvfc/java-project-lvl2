package hexlet.code;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    private static final String JSON_FILE_NAME = "file1.json";
    private static final String YAML_FILE_NAME = "file1.yml";
    private static final String NESTED_YAML_FILE_NAME = "file3.yml";
    private static final int EXPECTED_SIZE = 4;

    private final Parser parser = new Parser();

    @Test
    public void parseJson() throws IOException {
        final var parsedResult = parser.parse(getFile(JSON_FILE_NAME));

        assertEquals(EXPECTED_SIZE, parsedResult.size());
    }

    @Test
    public void parseYaml() throws IOException {
        final var parsedResult = parser.parse(getFile(YAML_FILE_NAME));

        assertEquals(EXPECTED_SIZE, parsedResult.size());
    }

    @Test
    public void parsNested() throws IOException {
        final var parsedResult = parser.parse(getFile(NESTED_YAML_FILE_NAME));

        assertEquals("{nestedKey=value, isNested=true}", parsedResult.get("obj1").toString());
        assertEquals("[a, b, c]", parsedResult.get("chars1").toString());
    }

    private File getFile(final String name) {
        return new File(getClass().getClassLoader().getResource(name).getFile());
    }
}
