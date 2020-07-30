package vertxval;

import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.ReplyException;
import jsonvalues.JsObj;
import jsonvalues.spec.JsErrorPair;
import jsonvalues.spec.JsObjSpec;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;

@SuppressWarnings("serial")
public class VertxValException extends ReplyException {


    public static final int BAD_MESSAGE_CODE = 0;
    public static Function<String, VertxValException> GET_BAD_MESSAGE_EXCEPTION =
            errorMessage -> new VertxValException(BAD_MESSAGE_CODE,
                                                  errorMessage
            );

    public static final int ERROR_EXECUTING_VERTIClE_CODE = 1;
    public static Function<Throwable, VertxValException> GET_ERROR_EXECUTING_VERTIClE_EXCEPTION =
            exc -> new VertxValException(ERROR_EXECUTING_VERTIClE_CODE,
                                         exc
            );


    public static final int ERROR_DEPLOYING_CODECS_CODE = 2;
    public static Function<Throwable, VertxValException> GET_ERROR_DEPLOYING_CODECS_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_CODECS_CODE,
                                         exc
            );

    public static final int ERROR_DEPLOYING_MODULE_CODE = 3;
    public static Function<Throwable, VertxValException> GET_ERROR_DEPLOYING_MODULE_CODE_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_MODULE_CODE,
                                         exc
            );

    public static final int ERROR_DEPLOYING_VERTIClE_CODE = 4;
    public static Function<Throwable, VertxValException> GET_ERROR_DEPLOYING_VERTIClE_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_VERTIClE_CODE,
                                         exc
            );

    public static final int ERROR_STOPING_VERTIClE_CODE = 5;
    public static Function<Throwable, VertxValException> GET_ERROR_STOPPING_VERTIClE_EXCEPTION =
            exc -> new VertxValException(ERROR_STOPING_VERTIClE_CODE,
                                         exc
            );


    VertxValException(final int code,
                      final String message) {
        super(RECIPIENT_FAILURE,
              code,
              message
             );
    }

    VertxValException(final int code,
                      final Throwable e) {
        super(RECIPIENT_FAILURE,
              code,
              e.getStackTrace().length == 0 ? e.toString() : e.toString() + "@" + Arrays.toString(e.getStackTrace())
             );
    }


    public static Consumer<Message<JsObj>> ifErrorElse(final JsObjSpec spec,
                                                       final Function<Set<JsErrorPair>, String> errorMessage,
                                                       final Consumer<Message<JsObj>> valid) {
        return message -> {
            Set<JsErrorPair> errors = spec.test(message.body());
            if (errors.isEmpty()) message.reply(GET_BAD_MESSAGE_EXCEPTION.apply(errorMessage.apply(errors)));
            else valid.accept(message);
        };

    }

    public static <I> Consumer<Message<I>> ifErrorElse(final Predicate<I> spec,
                                                       final String errorMessage,
                                                       final Consumer<Message<I>> valid) {
        return message -> {
            if (spec.test(message.body())) valid.accept(message);
            else message.reply(GET_BAD_MESSAGE_EXCEPTION.apply(errorMessage));
        };

    }
}
