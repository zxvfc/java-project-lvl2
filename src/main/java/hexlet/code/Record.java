package hexlet.code;

public record Record(
        String name,
        Object valueWas,
        Object valueNow,
        State state
) { }
