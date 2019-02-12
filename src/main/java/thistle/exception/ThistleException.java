package thistle.exception;

import lombok.Getter;

@Getter
public class ThistleException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public ThistleException(String message) {
        this(message, message);
    }

    public ThistleException(String userMessage, String developerMessage) {
        super(userMessage);
        this.exceptionMessage = new ExceptionMessage(userMessage, developerMessage);
    }

    public ThistleException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getUserMessage());
        this.exceptionMessage = exceptionMessage;
    }
}
