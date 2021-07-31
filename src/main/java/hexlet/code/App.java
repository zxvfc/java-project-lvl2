package hexlet.code;

import java.io.IOException;
import java.util.concurrent.Callable;
import picocli.CommandLine;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Help.Visibility.ALWAYS;
import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

@Command(name = "gendiff", description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<String> {

    private static final String DEFAULT_FORMAT = "stylish";

    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = { "-f", "--format" },
            defaultValue = DEFAULT_FORMAT,
            showDefaultValue = ALWAYS,
            description = "output format"
    ) private String format;

    @Option(names = { "-h", "--help" },
            usageHelp = true,
            description = "Show this help message and exit."
    ) private boolean help;

    @Option(names = { "-v", "--version" },
            description = "Print version information and exit."
    ) private boolean printVersion;

    public static void main(String[] args) {
        final var commandLineRunner = new CommandLine(new App());
        commandLineRunner.execute(args);

        final String result = commandLineRunner.getExecutionResult();
        System.out.println(result);
    }

    @Override
    public String call() throws IOException {
        return Differ.generate(filepath1, filepath2, format);
    }
}
