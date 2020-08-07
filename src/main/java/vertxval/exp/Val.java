package vertxval.exp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Val<O> extends Supplier<Future<O>> {

    <P> Val<P> map(final Function<O, P> fn);

    <Q> Val<Q> flatMap(final Function<O, Val<Q>> fn);

    Val<O> retry(final int attempts);

    Val<O> retryIf(final Predicate<Throwable> predicate,
                   final int attempts);

    Val<O> recover(final Function<Throwable, O> fn);

    Val<O> recoverWith(final Function<Throwable, Val<O>> fn);

    Val<O> fallbackTo(final Function<Throwable, Val<O>> fn);

    Val<O> onSuccess(final Consumer<O> success);

    Val<O> onComplete(final Consumer<O> success,
                      final Consumer<Throwable> failure);

    <U> Val<U> flatMap(final Function<O, Val<U>> successMapper,
                       final Function<Throwable, Val<U>> failureMapper);

    Val<O> onComplete(final Handler<AsyncResult<O>> handler);


}
