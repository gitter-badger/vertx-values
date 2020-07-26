package actors.exp;

import io.vavr.Tuple2;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Pair<A, B> extends AbstractExp<Tuple2<A, B>> {

    private final Exp<A> _1;
    private final Exp<B> _2;

    private Pair(final Exp<A> _1,
                 final Exp<B> _2) {
        this._1 = _1;
        this._2 = _2;
    }


    public static <A, B> Pair<A, B> of(final Exp<A> _1,
                                       final Exp<B> _2) {
        return new Pair<>(_1,
                          _2
        );
    }

    public static <A, B> Pair<A, B> of(final Supplier<Future<A>> _1,
                                       final Supplier<Future<B>> _2) {
        return new Pair<>(Val.of(_1),
                          Val.of(_2)
        );
    }

    @Override
    public <P> Exp<P> map(final Function<Tuple2<A, B>, P> fn) {
        return Val.of(() -> get().map(fn));
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
    public Exp<Tuple2<A, B>> retry(final int attempts) {
        return new Pair<>(_1.retry(attempts),
                          _2.retry(attempts)
        );
    }

    @Override
    public Exp<Tuple2<A, B>> retryIf(final Predicate<Throwable> predicate,
                                     final int attempts) {
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
