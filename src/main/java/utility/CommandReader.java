package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for read commands from console and separate command name and its' arguments for invoker
 */
public class CommandReader {
    private final Scanner scanner;
    private final Invoker invoker;
    private final Pattern commandNamePattern;
    private final Pattern argPattern;

    /**
     * @param scanner - is used to read commands from console
     * @param invoker - invoker which wil execute received commands
     */
    public CommandReader(Scanner scanner, Invoker invoker) {
        this.scanner = scanner;
        this.invoker = invoker;
        commandNamePattern = Pattern.compile("^\\w+");
        argPattern = Pattern.compile("\\b(.*\\s*)*");

    }

    /**
     * Start reading loop
     * Loop reads commands and calls invoker
     * Loop is finished when input is empty or exit commands is called
     */
    public void ActiveMode() {
        String line;
        String command;
        String arg;
        do {
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException exception) {
                break;
            }
            Matcher matcher = commandNamePattern.matcher(line);
            matcher.find();
            try {
                command = matcher.group();
            } catch (IllegalStateException e) {
                System.out.println("Input is not a command.");
                continue;
            }
            line = line.substring(command.length());
            matcher = argPattern.matcher(line);
            matcher.find();
            try {
                arg = matcher.group();
            } catch (IllegalStateException e) {
                arg = "";
            }
            invoker.exe(command, arg);
        } while (!invoker.isStopRequested() && scanner.hasNext());
    }
}