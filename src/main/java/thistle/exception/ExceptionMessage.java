package thistle.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionMessage {

    private final String userMessage;
    private final String developerMessage;
}
