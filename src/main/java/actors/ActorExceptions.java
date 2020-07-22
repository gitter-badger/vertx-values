package actors;

import java.util.function.Function;

public class ActorExceptions {

    public static final int ERROR_HANDLING_SUCCESSFUL_EVENT = 0;
    public static final int ERROR_HANDLING_FAILURE_EVENT = 1;

    public static Function<Throwable,ActorException> EXCEPTION_HANDLING_SUSCESSFULL_EVENT =
            exc -> new ActorException(ERROR_HANDLING_SUCCESSFUL_EVENT, exc);


    public static Function<Throwable,ActorException> EXCEPTION_HANDLING_FAILURE_EVENT =
            exc -> new ActorException(ERROR_HANDLING_FAILURE_EVENT,exc);
}
