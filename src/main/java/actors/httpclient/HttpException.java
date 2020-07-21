package actors.httpclient;


import io.vertx.core.eventbus.ReplyException;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;

public class HttpException extends ReplyException {


    HttpException(final byte code,final String message) {
        super(RECIPIENT_FAILURE,
              code,
              message
             );
    }

    HttpException(final byte code,final Throwable e) {
        super(RECIPIENT_FAILURE,
              code,
              e.toString()+" @ "+e.getStackTrace()[0]
             );
    }

}
