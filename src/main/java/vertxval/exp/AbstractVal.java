package vertxval.exp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

abstract class AbstractVal<O> implements Val<O> {
    @Override
    public Val<O> recover(final Function<Throwable, O> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().compose(Future::succeededFuture,
                                          e -> Future.succeededFuture(fn.apply(e))
                                          )
                      );
    }

    @Override
    public Val<O> recoverWith(final Function<Throwable, Val<O>> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().compose(Future::succeededFuture,
                                          e -> fn.apply(e)
                                                 .get()
                                          )
                      );
    }

    @Override
    public Val<O> fallbackTo(final Function<Throwable, Val<O>> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().compose(Future::succeededFuture,
                                          e -> fn.apply(e)
                                                 .get()
                                                 .compose(Future::succeededFuture,
                                                          e1 -> Future.failedFuture(e)
                                                         )
                                          )
                      );

    }

    @Override
    public <Q> Val<Q> flatMap(final Function<O, Val<Q>> fn) {
        requireNonNull(fn);
        return Cons.of(
                () ->
                        get().flatMap(o ->
                                              fn.apply(o)
                                                 .get()));
    }

    @Override
    public Val<O> onSuccess(final Consumer<O> success) {
        requireNonNull(success);
        return Cons.of(() -> get().onSuccess(success::accept));
    }

    @Override
    public Val<O> onComplete(final Consumer<O> successConsumer,
                             final Consumer<Throwable> throwableConsumer) {
        requireNonNull(successConsumer);
        requireNonNull(throwableConsumer);
        return Cons.of(() -> get().onComplete(event -> {
                          if (event.succeeded()) successConsumer.accept(event.result());
                          else throwableConsumer.accept(event.cause());
                      })
                      );
    }

    @Override
    public <U> Val<U> flatMap(final Function<O, Val<U>> successMapper,
                              final Function<Throwable, Val<U>> failureMapper) {
        requireNonNull(successMapper);
        requireNonNull(failureMapper);
        return Cons.of(() -> get().compose(result -> successMapper.apply(result)
                                                                  .get(),
                                          failure -> failureMapper.apply(failure)
                                                                  .get()
                                          )
                      );

    }

    @Override
    public Val<O> onComplete(final Handler<AsyncResult<O>> handler) {

        return Cons.of(() -> get().onComplete(handler));

    }
}