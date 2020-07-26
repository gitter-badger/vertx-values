package actors.exp;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Val<O> extends AbstractExp<O> {

    Supplier<Future<O>> fut;

    public static <O> Val<O> of(Supplier<Future<O>> supplier) {
        return new Val<>(supplier);
    }

    public static <O> Val<O> of(Throwable failure){
        return Val.of(()->Future.failedFuture(failure));
    }

    public static <O> Val<O> of(O o) {
        return new Val<>(() -> Future.succeededFuture(o));
    }

    Val(final Supplier<Future<O>> fut) {
        this.fut = fut;
    }

    @Override
    public Future<O> get() {
        return fut.get();
    }

    @Override
    public <P> Exp<P> map(final Function<O, P> fn) {
        return Val.of(() -> fut.get()
                               .map(fn)
                     );
    }



    @Override
    public O result() {
        return get().result();
    }

    @Override
    public Exp<O> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("tries < 0");
        return retry(this,
                     attempts
                    );
    }

    @Override
    public Exp<O> retryIf(final Predicate<Throwable> predicate,
                          final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("tries < 0");
        return retry(this,
                     attempts,
                     predicate
                    );
    }

    private Exp<O> retry(final Exp<O> exp,
                         final int attempts) {
        if (attempts == 0) return exp;
        return Val.of(() -> exp.get()
                               .compose(Future::succeededFuture,
                                        e -> retry(exp,
                                                   attempts - 1
                                                  ).get()
                                       )
                     );
    }

    private Exp<O> retry(final Exp<O> exp,
                         final int attempts,
                         final Predicate<Throwable> predicate) {
        if (attempts == 0) return exp;
        return Val.of(() -> exp.get()
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
