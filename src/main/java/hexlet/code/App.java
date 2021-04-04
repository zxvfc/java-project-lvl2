package hexlet.code;

import picocli.CommandLine;

import static picocli.CommandLine.*;
import static picocli.CommandLine.Option;

@Command(name = "gendiff", description = "Compares two configuration files and shows a difference.")
public class App {

    @Option(names = { "-h", "--help" }, usageHelp = true, description = "Show this help message and exit.")
    private boolean help;

    @Option(names = { "-v", "--version" }, description = "Print version information and exit.")
    private boolean printVersion;

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }
}
