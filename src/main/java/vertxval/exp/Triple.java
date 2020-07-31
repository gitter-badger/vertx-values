package vertxval.exp;

import io.vavr.Tuple3;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;


public final class Triple<A, B, C> extends AbstractVal<Tuple3<A, B, C>> {

    private final Val<A> _1;
    private final Val<B> _2;
    private final Val<C> _3;

    private Triple(final Val<A> _1,
                   final Val<B> _2,
                   final Val<C> _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public static <A, B, C> Triple<A, B, C> of(final Val<A> _1,
                                               final Val<B> _2,
                                               final Val<C> _3) {
        return new Triple<>(requireNonNull(_1),
                            requireNonNull(_2),
                            requireNonNull(_3)
        );
    }

    @Override
    public <P> Val<P> map(final Function<Tuple3<A, B, C>, P> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().map(fn));
    }


    @Override
    public Tuple3<A, B, C> result() {
        return new Tuple3<>(_1.get()
                              .result(),
                            _2.get()
                              .result(),
                            _3.get()
                              .result()
        );

    }


    @Override
    public Val<Tuple3<A, B, C>> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new Triple<>(_1.retry(attempts),
                            _2.retry(attempts),
                            _3.retry(attempts)
        );
    }

    @Override
    public Val<Tuple3<A, B, C>> retryIf(final Predicate<Throwable> predicate,
                                        final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
        return new Triple<>(_1.retryIf(predicate,
                                       attempts
                                      ),
                            _2.retryIf(predicate,
                                       attempts
                                      ),
                            _3.retryIf(predicate,
                                       attempts
                                      )
        );
    }

    @Override
    public Future<Tuple3<A, B, C>> get() {
        return CompositeFuture.all(_1.get(),
                                   _2.get(),
                                   _3.get()
                                  )
                              .map(it -> new Tuple3<>(it.resultAt(0),
                                                      it.resultAt(1),
                                                      it.resultAt(2)
                              ));
    }
}
