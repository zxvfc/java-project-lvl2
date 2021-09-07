package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    private static final String JSON_FILE_NAME = "file1.json";
    private static final String YAML_FILE_NAME = "file1.yml";
    private static final String NESTED_YAML_FILE_NAME = "file3.yml";
    private static final int EXPECTED_SIZE = 4;

    @Test
    public void parseJson() throws IOException {
        final var parsedResult = Parser.parse(readFile(JSON_FILE_NAME), ".json");

        assertEquals(EXPECTED_SIZE, parsedResult.size());
    }

    @Test
    public void parseYaml() throws IOException {
        final var parsedResult = Parser.parse(readFile(YAML_FILE_NAME), ".yml");

        assertEquals(EXPECTED_SIZE, parsedResult.size());
    }

    @Test
    public void parsNested() throws IOException {
        final var parsedResult = Parser.parse(readFile(NESTED_YAML_FILE_NAME), ".yml");

        assertEquals("{nestedKey=value, isNested=true}", parsedResult.get("obj1").toString());
        assertEquals("[a, b, c]", parsedResult.get("chars1").toString());
    }

    private String readFile(final String name) throws IOException {
        final var path = Paths.get(getClass().getClassLoader().getResource(name).getFile());
        return Files.readString(path);
    }
}
