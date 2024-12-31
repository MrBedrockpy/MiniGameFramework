package ru.mrbedrockpy.bedrocklib.command;

public class CommandResult {

    private final ResultStatus status;

    private final String message;

    public CommandResult(ResultStatus status) {
        this.status = status;
        this.message = "";
    }

    public CommandResult(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
