package actors;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.ReplyException;


import java.util.function.Function;

import static java.util.Objects.*;

public class Handlers {


    public static <T, O> Handler<AsyncResult<T>> pipeTo(final Message<?> message,
                                                        final Function<T, O> mapSuccess,
                                                        final Function<Throwable, ReplyException> mapError) {
        requireNonNull(message);
        requireNonNull(mapSuccess);
        requireNonNull(mapError);
        return  event -> {
            if(event.succeeded()) {
                try {
                    message.reply(mapSuccess.apply(event.result()));
                } catch (Throwable e) {
                    message.reply(ActorExceptions.GET_ERROR_HANDLING_SUCCESSFUL_EVENT_EXCEPTION.apply(e));
                }
            }
            else {
                try {
                    message.reply(mapError.apply(event.cause()));
                } catch (Throwable e) {
                    message.reply(ActorExceptions.GET_ERROR_HANDLING_FAILURE_EVENT_EXCEPTION.apply(e));
                }
            }
        };
    }




    public static <T>  Handler<AsyncResult<T>> pipeTo(final Message<?> message) {
        requireNonNull(message);
        return  event -> {
            if(event.succeeded())message.reply(event.result());
            else message.reply(event.cause());

        };
    }

    public static <T, O> Handler<AsyncResult<T>> pipeTo(final Message<?> message,
                                                        final Function<T, O> mapSuccess) {
        requireNonNull(message);
        requireNonNull(mapSuccess);

        return  event -> {
            if(event.succeeded()) {
                try {
                    message.reply(mapSuccess.apply(event.result()));
                } catch (Throwable e) {
                    message.reply(ActorExceptions.GET_ERROR_HANDLING_SUCCESSFUL_EVENT_EXCEPTION.apply(e));
                }
            }
            else {
                message.reply(event.cause());
            }
        };
    }


}
