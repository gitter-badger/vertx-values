package vertxval.exp;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 A Val is just an alias for a lazy Vertx future. Laziness makes our code more functional.
 It allows us to describe programs before executing them. The get method triggers the execution
 of a val.
 @param <O> the type of the value produced by the future
 */
public interface Val<O> extends Supplier<Future<O>> {

    <P> Val<P> map(final Function<O, P> fn);

    <Q> Val<Q> flatMap(final Function<O, Val<Q>> fn);

    <Q> Val<Q> flatMap(final Function<O, Val<Q>> successMapper,
                       final Function<Throwable, Val<Q>> failureMapper);

    /**
     returns a new val tha will retry its execution if it fails
     @param attempts the number of attempts before returning the error
     @return a new Val
     */
    Val<O> retry(final int attempts);

    //Val<O> retry(final int attempts,final int time);

    /**
     returns a new val tha will retry its execution if it fails with an error that satisfies the given predicate
     @param predicate the predicate against which the returned error will be tested on
     @param attempts the number of attempts before returning the error
     @return a new Val
     */
    Val<O> retryIf(final Predicate<Throwable> predicate,
                   final int attempts);

   // Val<O> retryIf(final Predicate<Throwable> predicate,
   //                final int attempts
    //               final int time);

    Val<O> recover(final Function<Throwable, O> fn);

    Val<O> recoverWith(final Function<Throwable, Val<O>> fn);

    Val<O> fallbackTo(final Function<Throwable, Val<O>> fn);

    Val<O> onSuccess(final Consumer<O> success);

    Val<O> onComplete(final Consumer<O> success,
                      final Consumer<Throwable> failure);

    Val<O> onComplete(final Handler<AsyncResult<O>> handler);

}
