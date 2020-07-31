package vertxval.httpclient;


import io.vertx.core.eventbus.ReplyException;

import java.util.Arrays;
import java.util.function.Function;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;

@SuppressWarnings("serial")
public class HttpException extends ReplyException {

    public static final int ERROR_HTTP_METHOD_NOT_IMPLEMENTED_CODE = 10000;
    public static final int READING_BODY_RESPONSE_CODE = 10001;
    public static final int UNKNOWN_RESPONSE_CODE = 10002;
    /**
     Error that happens when the domain can't be resolved: wrong name or there is no internet connection.
     */
    public static final int UNKNOWN_HOST_CODE = 10003;
    public static final int CONNECT_TIMEOUT_CODE = 10004;
    public static final int REQUEST_TIMEOUT_CODE = 10005;

    public static Function<Integer, HttpException> GET_HTTP_METHOD_NOT_SUPPORTED_EXCEPTION =
            method -> new HttpException(ERROR_HTTP_METHOD_NOT_IMPLEMENTED_CODE,
                                        "The method " + method + "is not supported. Supported types are in enum HttpReqBuilder.TYPE. Use a provided builder to make requests."
            );


    public static Function<Throwable, HttpException> EXCEPTION_READING_BODY_RESPONSE =
            exc -> new HttpException(READING_BODY_RESPONSE_CODE,
                                     exc);


    public static Function<Throwable, HttpException> GET_RESPONSE_EXCEPTION =
            exc -> {
                switch (exc.getClass()
                           .getSimpleName()) {
                    case "ConnectTimeoutException": {
                        return new HttpException(CONNECT_TIMEOUT_CODE,
                                                 exc
                        );
                    }
                    case "UnknownHostException": {
                        return new HttpException(UNKNOWN_HOST_CODE,
                                                 exc
                        );
                    }
                    case "NoStackTraceTimeoutException": {
                        return new HttpException(REQUEST_TIMEOUT_CODE,
                                                 exc
                        );

                    }
                    default:
                        return new HttpException(UNKNOWN_RESPONSE_CODE,
                                                 exc
                        );
                }
            };

    HttpException(final int code,final String message) {
        super(RECIPIENT_FAILURE,
              code,
              message
             );
    }

    HttpException(final int code,final Throwable e) {
        //todo @ sobra si no hay stacktrace
        super(RECIPIENT_FAILURE,
              code,
              e.toString() + "@" + Arrays.toString(e.getStackTrace())
             );
    }



}
