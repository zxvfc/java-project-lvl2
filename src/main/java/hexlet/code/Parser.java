package hexlet.code;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class Parser {

    private static final String JSON = ".json";
    private static final String YML = ".yml";
    private static final String YAML = ".yaml";

    public Map<String, Object> parse(final File file) throws IOException {
        final var extension = getExtensionOf(file);
        return getMapperFor(extension).readValue(file, Map.class);
    }

    private static String getExtensionOf(final File file) {
        final var fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    private ObjectMapper getMapperFor(final String extension) {
        final var factory = switch (extension.toLowerCase()) {
            case JSON -> new MappingJsonFactory();
            case YML, YAML -> new YAMLFactory();
            default -> throw new IllegalStateException("Unexpected value: " + extension);
        };
        return new ObjectMapper(factory);
    }
}
