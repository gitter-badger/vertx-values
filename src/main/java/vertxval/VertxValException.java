package vertxval;

import io.vertx.core.eventbus.ReplyException;
import java.util.Arrays;

import java.util.function.Function;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;

@SuppressWarnings("serial")
public class VertxValException extends ReplyException {


    public static final int BAD_MESSAGE_CODE = 0;
    public static Function<String, VertxValException> GET_BAD_MESSAGE_EXCEPTION =
            errorMessage -> new VertxValException(BAD_MESSAGE_CODE,
                                                  errorMessage
            );

    public static final int ERROR_EXECUTING_VERTIClE_CODE = 1;
    public static Function<Throwable, VertxValException> GET_EXECUTING_VERTIClE_EXCEPTION =
            exc -> new VertxValException(ERROR_EXECUTING_VERTIClE_CODE,
                                         exc
            );


    public static final int ERROR_DEPLOYING_CODECS_CODE = 2;
    public static Function<Throwable, VertxValException> GET_REGISTERING_CODECS_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_CODECS_CODE,
                                         exc
            );

    public static final int ERROR_DEPLOYING_MODULE_CODE = 3;
    public static Function<Throwable, VertxValException> GET_DEPLOYING_MODULE_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_MODULE_CODE,
                                         exc
            );

    public static final int ERROR_DEPLOYING_VERTIClE_CODE = 4;
    public static Function<Throwable, VertxValException> GET_DEPLOYING_VERTIClE_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_VERTIClE_CODE,
                                         exc
            );

    public static final int ERROR_STOPING_VERTIClE_CODE = 5;
    public static Function<Throwable, VertxValException> GET_STOPPING_VERTIClE_EXCEPTION =
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



}
