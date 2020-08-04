package vertxval.exp;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class Cons<O> extends AbstractVal<O> {

    Supplier<Future<O>> fut;

    public static <O> Cons<O> of(Supplier<Future<O>> supplier) {

        return new Cons<>(requireNonNull(supplier));
    }

    public static <O> Cons<O> failure(Throwable failure) {
        requireNonNull(failure);
        return Cons.of(() -> Future.failedFuture(failure));
    }

    public static <O> Cons<O> success(O o) {
        return new Cons<>(() -> Future.succeededFuture(o));
    }

    Cons(final Supplier<Future<O>> fut) {
        this.fut = requireNonNull(fut);
    }

    @Override
    public Future<O> get() {
        return fut.get();
    }

    @Override
    public <P> Val<P> map(final Function<O, P> fn) {
        requireNonNull(fn);
        return Cons.of(() -> fut.get()
                                .map(fn)
                      );
    }

    @Override
    public Val<O> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return retry(this,
                     attempts
                    );
    }

    @Override
    public Val<O> retryIf(final Predicate<Throwable> predicate,
                          final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
        return retry(this,
                     attempts,
                     predicate
                    );
    }

    private Val<O> retry(final Val<O> exp,
                         final int attempts) {
        if (attempts == 0) return exp;
        return Cons.of(() -> exp.get()
                                .compose(Future::succeededFuture,
                                         e -> retry(exp,
                                                    attempts - 1
                                                   ).get()
                                        )
                      );
    }

    private Val<O> retry(final Val<O> exp,
                         final int attempts,
                         final Predicate<Throwable> predicate) {
        if (attempts == 0) return exp;
        return Cons.of(() -> exp.get()
                                .compose(Future::succeededFuture,
                                         e -> (predicate.test(e)) ?
                                              retry(exp,
                                                    attempts - 1
                                                   ).get() :
                                              Future.failedFuture(e)
                                        )
                      );
    }

}
