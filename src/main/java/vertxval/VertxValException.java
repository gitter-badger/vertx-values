package vertxval;

import io.vertx.core.eventbus.ReplyException;

import java.util.Arrays;

import static io.vertx.core.eventbus.ReplyFailure.RECIPIENT_FAILURE;

public class VertxValException extends ReplyException {
    VertxValException(final int code, final String message) {
        super(RECIPIENT_FAILURE,
              code,
              message
             );
    }

    VertxValException(final int code, final Throwable e) {
        super(RECIPIENT_FAILURE,
              code,
              e.toString() + "@" + Arrays.toString(e.getStackTrace())
             );
    }




}
