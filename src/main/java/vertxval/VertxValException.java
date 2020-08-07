package vertxval;

import io.vertx.core.eventbus.ReplyException;
import jsonvalues.Prism;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;
import static java.util.Objects.requireNonNull;

@SuppressWarnings("serial")
public class VertxValException extends ReplyException {

    public static final int BAD_MESSAGE_CODE = 0;
    public static final int ERROR_EXECUTING_VERTICLE_CODE = 1;
    public static final int ERROR_DEPLOYING_CODEC_CODE = 2;
    public static final int ERROR_DEPLOYING_MODULE_CODE = 3;
    /**
     Error that happens when the domain can't be resolved: wrong name or there is no internet connection.
     */
    public static final int UNKNOWN_HOST_CODE = 10000;
    public static final int CONNECT_TIMEOUT_CODE = 10001;
    public static final int REQUEST_TIMEOUT_CODE = 10002;

    public static final int HTTP_METHOD_NOT_IMPLEMENTED_CODE = 10003;
    public static final int UNKNOWN_EXCEPTION_CODE = 10004;


    public static final Prism<Throwable, VertxValException> prism = new Prism<>(
            t -> {
                if (t instanceof VertxValException) return Optional.of(((VertxValException) t));
                else return Optional.empty();
            },
            v -> v
    );
    public static Function<String, VertxValException> GET_BAD_MESSAGE_EXCEPTION =
            errorMessage -> new VertxValException(BAD_MESSAGE_CODE,
                                                  requireNonNull(errorMessage)
            );

    public static Function<Throwable, VertxValException> GET_EXECUTING_VERTICLE_EXCEPTION =
            exc -> new VertxValException(ERROR_EXECUTING_VERTICLE_CODE,
                                         requireNonNull(exc)
            );


    public static Function<Throwable, VertxValException> GET_REGISTERING_CODECS_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_CODEC_CODE,
                                         requireNonNull(exc)
            );

    public static Function<Throwable, VertxValException> GET_DEPLOYING_MODULE_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_MODULE_CODE,
                                         requireNonNull(exc)
            );

    public static final int ERROR_DEPLOYING_VERTICLE_CODE = 4;
    public static Function<Throwable, VertxValException> GET_DEPLOYING_VERTICLE_EXCEPTION =
            exc -> new VertxValException(ERROR_DEPLOYING_VERTICLE_CODE,
                                         requireNonNull(exc)
            );

    public static final int ERROR_STOPPING_VERTICLE_CODE = 5;
    public static Function<Throwable, VertxValException> GET_STOPPING_VERTICLE_EXCEPTION =
            exc -> new VertxValException(ERROR_STOPPING_VERTICLE_CODE,
                                         requireNonNull(exc)
            );

    public static Function<Integer, VertxValException> GET_HTTP_METHOD_NOT_IMPLEMENTED_EXCEPTION =
            method -> new VertxValException(HTTP_METHOD_NOT_IMPLEMENTED_CODE,
                                        "The method " + method + "is not supported. Supported types are in enum HttpReqBuilder.TYPE. Use a provided builder to make requests."
            );

    public static Function<Throwable, VertxValException> GET_RESPONSE_EXCEPTION =
            exc -> {
                switch (exc.getClass()
                           .getSimpleName()) {
                    case "ConnectTimeoutException": {
                        return new VertxValException(CONNECT_TIMEOUT_CODE,
                                                 exc
                        );
                    }
                    case "UnknownHostException": {
                        return new VertxValException(UNKNOWN_HOST_CODE,
                                                 exc
                        );
                    }
                    case "NoStackTraceTimeoutException": {
                        return new VertxValException(REQUEST_TIMEOUT_CODE,
                                                 exc
                        );

                    }
                    default:
                        return new VertxValException(UNKNOWN_EXCEPTION_CODE,
                                                 exc
                        );
                }
            };

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
              e.getStackTrace().length == 0 ?
              e.toString() :
              e.toString() + "@" + Arrays.toString(e.getStackTrace())
             );
    }


}
