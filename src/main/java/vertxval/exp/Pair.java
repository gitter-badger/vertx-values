package vertxval.exp;

import io.vavr.Tuple2;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Pair<A, B> extends AbstractVal<Tuple2<A, B>> {

    private final Val<A> _1;
    private final Val<B> _2;

    private Pair(final Val<A> _1,
                 final Val<B> _2) {
        this._1 = Objects.requireNonNull(_1);
        this._2 = Objects.requireNonNull(_2);
    }


    public static <A, B> Pair<A, B> of(final Val<A> _1,
                                       final Val<B> _2) {
        return new Pair<>(_1,
                          _2
        );
    }



    @Override
    public <P> Val<P> map(final Function<Tuple2<A, B>, P> fn) {
        Objects.requireNonNull(fn);
        return Cons.of(() -> get().map(fn));
    }



    @Override
    public Tuple2<A, B> result() {
        return new Tuple2<>(_1.get()
                              .result(),
                            _2.get()
                              .result()
        );
    }

    @Override
    public Val<Tuple2<A, B>> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new Pair<>(_1.retry(attempts),
                          _2.retry(attempts)
        );
    }

    @Override
    public Val<Tuple2<A, B>> retryIf(final Predicate<Throwable> predicate,
                                     final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        Objects.requireNonNull(predicate);
        return new Pair<>(_1.retryIf(predicate,
                                     attempts
                                    ),
                          _2.retryIf(predicate,
                                     attempts
                                    )
        );
    }


    @Override
    public Future<Tuple2<A, B>> get() {
        return CompositeFuture.all(_1.get(),
                                   _2.get()
                                  )
                              .map(it -> new Tuple2<>(it.resultAt(0),
                                                      it.resultAt(1)
                              ));
    }
}
