package vertxval.functions;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.ReplyException;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.GET_EXECUTING_VERTICLE_EXCEPTION;

public class Handlers {

    /**
     returns a handler of an {@link AsyncResult} instance that pipe the result into a {@link Message}
     instance to reply to it.
     If the result succeeded, it's mapped with the mapSuccess function and then sent to the even bus as a reply to the Message
     If the result failed, the exceptions it's mapped with the mapError function and then sent to the even bus as a reply to the Message
     @param message the message that will be answered with the result
     @param mapSuccess the map function to be applied to the result if it succeeds
     @param mapError the map function to be applied to the result if it fails
     @param <T> the type of the result
     @param <O> the type of the message sent to the even bus as a reply
     @return a new handler of an AsyncResult instance
     */
    public static <T, O> Handler<AsyncResult<T>> pipeTo(final Message<?> message,
                                                        final Function<T, O> mapSuccess,
                                                        final Function<Throwable, ReplyException> mapError) {
        requireNonNull(message);
        requireNonNull(mapSuccess);
        requireNonNull(mapError);

        return event -> {
            if (event.succeeded()) {
                try {
                    message.reply(mapSuccess.apply(event.result()));
                } catch (Throwable e) {
                    message.reply(GET_EXECUTING_VERTICLE_EXCEPTION.apply(e));
                }
            }
            else {
                try {
                    message.reply(mapError.apply(event.cause()));
                } catch (Throwable e) {
                    message.reply(GET_EXECUTING_VERTICLE_EXCEPTION.apply(e));
                }
            }
        };
    }

    /**

     @param message
     @param <T>
     @return
     */
    public static <T> Handler<AsyncResult<T>> pipeTo(final Message<?> message) {
        requireNonNull(message);
        return event -> {
            if (event.succeeded()) message.reply(event.result());
            else message.reply(event.cause());

        };
    }

    public static <T, O> Handler<AsyncResult<T>> pipeTo(final Message<?> message,
                                                        final Function<T, O> mapSuccess) {
        requireNonNull(message);
        requireNonNull(mapSuccess);

        return event -> {
            if (event.succeeded()) {
                try {
                    message.reply(mapSuccess.apply(event.result()));
                } catch (Throwable e) {
                    message.reply(GET_EXECUTING_VERTICLE_EXCEPTION.apply(e));
                }
            }
            else {
                message.reply(event.cause());
            }
        };
    }


}
