package Parser;

import Commands.*;
import DataStruct.TaskList;
import DaveExceptions.DaveException;
import DataStruct.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    private static final DateTimeFormatter slashFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter dashFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String eventBreak = "/at ";
    private final String deadlineBreak = "/by ";

    public static Command dispatch(String command, String args, TaskList tasks) throws DaveException {
        switch (command) {
        case "bye":
            return new EndCommand(tasks, args);
        case "list":
            return new ListCommand(tasks, args);
        case "mark":
            return new MarkDoneCommand(tasks, args);
        case "unmark":
            return new UnmarkDoneCommand(tasks, args);
        case "todo":
            return new AddTodoCommand(tasks, args);
        case "deadline":
            return new AddDeadlineCommand(tasks, args);
        case "event":
            return new AddEventCommand(tasks, args);
        case "remove":
            return new RemoveTaskCommand(tasks, args);
        case "find":
            return new FindTasksCommand(tasks, args);
        default:
            throw new DaveException("(｡╯︵╰｡) OOPS!!! I'm sowwy, but I don't know what that means ｡･ﾟﾟ*(>д<)*ﾟﾟ･｡");
        }
    }
    public static Pair<String, String> splitInputIntoCommand(String input) {
        String[] splitInput = input.trim().split(" ", 2);
        String command = splitInput[0].toLowerCase();
        String args;
        try {
            args = splitInput[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            args = "";
        }
        assert args != null : "Arguments to a command should be a string and never null";
        return new Pair<String, String>(command, args);
    }

    public static Pair<String, LocalDateTime> parseTask(String input) throws DaveException {
        if (input.equals("")) {
            throw new DaveException("( ; ω ; ) Oh nyo!!! The description of an event cannot be empty!");
        }
        String[] args = input.split("/at |/by ");
        if (args.length > 2) {
            throw new DaveException("( ; ω ; ) Oh nyo!!! Too many timings given, Dave's brain is fried!");
        } else if (args.length < 2) {
            throw new DaveException("( ; ω ; ) Oh nyo!!! Please provide a timing for the event!");
        }

        assert args.length == 2 : "Number of args to a Task command should always be 2";

        String task = args[0];
        String dateStr = args[1].trim();
        LocalDateTime dateTime;

        try {
            if (dateStr.contains("/")) {
                dateTime = LocalDateTime.parse(dateStr, slashFormat);
            } else {
                dateTime = LocalDateTime.parse(dateStr, dashFormat);
            }
        } catch (DateTimeParseException e) {
            throw new DaveException("Please input a valid date!");
        }

        return new Pair<>(task, dateTime);
    }
}