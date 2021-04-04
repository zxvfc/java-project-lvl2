package hexlet.code;

import java.io.File;
import picocli.CommandLine;

import static picocli.CommandLine.*;
import static picocli.CommandLine.Help.Visibility.ALWAYS;
import static picocli.CommandLine.Option;

@Command(name = "gendiff", description = "Compares two configuration files and shows a difference.")
public class App {

    private static final String DEFAULT_FORMAT = "stylish";

    @Option(names = { "-h", "--help" },
            usageHelp = true,
            description = "Show this help message and exit."
    ) private boolean help;

    @Option(names = { "-v", "--version" },
            description = "Print version information and exit."
    ) private boolean printVersion;

    @Option(names = { "-f", "--format" },
            defaultValue = DEFAULT_FORMAT,
            showDefaultValue = ALWAYS,
            description = "output format"
    ) private String format;

    @Parameters(index = "0", description = "path to first file")
    private File filepath1;

    @Parameters(index = "1", description = "path to second file")
    private File filepath2;

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }
}
