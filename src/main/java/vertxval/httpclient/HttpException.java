package vertxval.httpclient;


import io.vertx.core.eventbus.ReplyException;

import java.util.Arrays;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;

@SuppressWarnings("serial")
public class HttpException extends ReplyException {


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
