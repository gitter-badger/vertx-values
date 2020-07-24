package actors;

import java.util.function.Function;

public class ActorExceptions {

    public static final int BAD_MESSAGE_CODE = 0;
    public static final int ERROR_HANDLING_SUCCESSFUL_EVENT_CODE = 1;
    public static final int ERROR_HANDLING_FAILURE_EVENT_CODE = 2;

    public static Function<Throwable,ActorException> GET_ERROR_HANDLING_SUCCESSFUL_EVENT_EXCEPTION =
            exc -> new ActorException(ERROR_HANDLING_SUCCESSFUL_EVENT_CODE, exc);

    public static Function<Throwable,ActorException> GET_ERROR_HANDLING_FAILURE_EVENT_EXCEPTION =
            exc -> new ActorException(ERROR_HANDLING_FAILURE_EVENT_CODE,exc);

    public static Function<String,ActorException> GET_BAD_MESSAGE_EXCEPTION =
           errorMessage -> new ActorException(BAD_MESSAGE_CODE,errorMessage);


}
