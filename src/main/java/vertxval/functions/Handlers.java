package vertxval.functions;


import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.ReplyException;

import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.GET_EXECUTING_VERTIClE_EXCEPTION;

public class Handlers {

    public static <T> Function<AsyncResult<T>, Future<T>> asyncResult2Future() {
        return a -> a.succeeded() ?
                    Future.succeededFuture(a.result()) :
                    Future.failedFuture(a.cause());
    }

    public static  Function<CompositeFuture, Future<List>> composite2Future() {
        return a -> a.succeeded() ?
                    Future.succeededFuture(a.result().list()) :
                    Future.failedFuture(a.cause());
    }

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
                    message.reply(GET_EXECUTING_VERTIClE_EXCEPTION.apply(e));
                }
            }
            else {
                try {
                    message.reply(mapError.apply(event.cause()));
                } catch (Throwable e) {
                    message.reply(GET_EXECUTING_VERTIClE_EXCEPTION.apply(e));
                }
            }
        };
    }


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
                    message.reply(GET_EXECUTING_VERTIClE_EXCEPTION.apply(e));
                }
            }
            else {
                message.reply(event.cause());
            }
        };
    }


}
