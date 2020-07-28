package vertxval.exp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

abstract class AbstractExp<O> implements Exp<O> {
    @Override
    public Exp<O> recover(final Function<Throwable, O> fn) {
        requireNonNull(fn);
        return Val.success(() -> get().compose(Future::succeededFuture,
                                          e -> Future.succeededFuture(fn.apply(e))
                                              )
                          );
    }

    @Override
    public Exp<O> recoverWith(final Function<Throwable, Exp<O>> fn) {
        requireNonNull(fn);
        return Val.success(() -> get().compose(Future::succeededFuture,
                                          e -> fn.apply(e)
                                                 .get()
                                              )
                          );
    }

    @Override
    public Exp<O> fallbackTo(final Function<Throwable, Exp<O>> fn) {
        requireNonNull(fn);
        return Val.success(() -> get().compose(Future::succeededFuture,
                                          e -> fn.apply(e)
                                                 .get()
                                                 .compose(Future::succeededFuture,
                                                          e1 -> Future.failedFuture(e)
                                                         )
                                              )
                          );

    }

    @Override
    public <Q> Exp<Q> flatMap(final Function<O, Exp<Q>> fn) {
        requireNonNull(fn);
        return Val.success(
                () ->
                        get().flatMap(o ->
                                              fn.apply(o)
                                                 .get()));
    }

    @Override
    public Exp<O> onSuccess(final Consumer<O> success) {
        requireNonNull(success);
        return Val.success(() -> get().onSuccess(success::accept));
    }

    @Override
    public Exp<O> onComplete(final Consumer<O> successConsumer,
                             final Consumer<Throwable> throwableConsumer) {
        requireNonNull(successConsumer);
        requireNonNull(throwableConsumer);
        return Val.success(() -> get().onComplete(event -> {
                          if (event.succeeded()) successConsumer.accept(event.result());
                          else throwableConsumer.accept(event.cause());
                      })
                          );
    }

    @Override
    public <U> Exp<U> flatMap(final Function<O, Exp<U>> successMapper,
                              final Function<Throwable, Exp<U>> failureMapper) {
        requireNonNull(successMapper);
        requireNonNull(failureMapper);
        return Val.success(() -> get().compose(result -> successMapper.apply(result)
                                                                      .get(),
                                          failure -> failureMapper.apply(failure)
                                                                  .get()
                                              )
                          );

    }

    @Override
    public Exp<O> onComplete(final Handler<AsyncResult<O>> handler) {

        return Val.success(() -> get().onComplete(handler));

    }
}