package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.Map;

public final class Parser {

    private static final String JSON = ".json";
    private static final String YML = ".yml";
    private static final String YAML = ".yaml";

    public static Map<String, Object> parse(final String content, final String type) throws IOException {
        return getMapperFor(type).readValue(content, new TypeReference<>() { });
    }

    private static ObjectMapper getMapperFor(final String type) {
        final var factory = switch (type.toLowerCase()) {
            case JSON -> new MappingJsonFactory();
            case YML, YAML -> new YAMLFactory();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
        return new ObjectMapper(factory);
    }
}
