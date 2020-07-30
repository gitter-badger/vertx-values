package vertxval.exp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Val<O> extends Supplier<Future<O>> {

    <P> Val<P> map(Function<O, P> fn);

    <Q> Val<Q> flatMap(Function<O, Val<Q>> fn);

    O result();

    Val<O> retry(int attempts);

    Val<O> retryIf(Predicate<Throwable> predicate,
                   int attempts);

    Val<O> recover(Function<Throwable, O> fn);

    Val<O> recoverWith(Function<Throwable, Val<O>> fn);

    Val<O> fallbackTo(Function<Throwable, Val<O>> fn);

    Val<O> onSuccess(Consumer<O> success);

    Val<O> onComplete(Consumer<O> success,
                      Consumer<Throwable> thowable);

    <U> Val<U> flatMap(Function<O, Val<U>> successMapper,
                       Function<Throwable, Val<U>> failureMapper);

    Val<O> onComplete(Handler<AsyncResult<O>> handler);


}
