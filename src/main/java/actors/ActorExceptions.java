package actors;

import io.vertx.core.eventbus.Message;
import jsonvalues.JsObj;
import jsonvalues.spec.JsErrorPair;
import jsonvalues.spec.JsObjSpec;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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


    public static Consumer<Message<JsObj>> ifErrorElse(final JsObjSpec spec,
                                                       final Function<Set<JsErrorPair>,String> errorMessage,
                                                       final Consumer<Message<JsObj>> valid){
        return message -> {
            Set<JsErrorPair> errors = spec.test(message.body());
            if(errors.isEmpty()) message.reply(GET_BAD_MESSAGE_EXCEPTION.apply(errorMessage.apply(errors)));
            else valid.accept(message);
        };

    }

    public static <I> Consumer<Message<I>> ifErrorElse(final Predicate<I> spec,
                                                       final String errorMessage,
                                                       final Consumer<Message<I>> valid){
        return message -> {
            if(spec.test(message.body())) valid.accept(message);
            else message.reply(GET_BAD_MESSAGE_EXCEPTION.apply(errorMessage));
        };

    }

}
