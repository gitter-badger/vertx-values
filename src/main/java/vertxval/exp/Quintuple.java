package vertxval.exp;

import io.vavr.Tuple5;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;


public final class Quintuple<A, B, C, D, E> extends AbstractVal<Tuple5<A, B, C,D,E>> {

    private final Val<A> _1;
    private final Val<B> _2;
    private final Val<C> _3;
    private final Val<D> _4;
    private final Val<E> _5;


    private Quintuple(final Val<A> _1,
                      final Val<B> _2,
                      final Val<C> _3,
                      final Val<D> _4,
                      final Val<E> _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    public static <A, B, C, D, E> Quintuple<A, B, C, D, E> of(Val<A> _1,
                                                              Val<B> _2,
                                                              Val<C> _3,
                                                              Val<D> _4,
                                                              Val<E> _5) {
        return new Quintuple<>(requireNonNull(_1),
                               requireNonNull(_2),
                               requireNonNull(_3),
                               requireNonNull(_4),
                               requireNonNull(_5)
        );
    }

    @Override
    public <P> Val<P> map(final Function<Tuple5<A, B, C, D, E>, P> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().map(fn));
    }



    @Override
    public Quintuple<A, B, C, D, E> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new Quintuple<>(_1.retry(attempts),
                               _2.retry(attempts),
                               _3.retry(attempts),
                               _4.retry(attempts),
                               _5.retry(attempts)
        );
    }

    @Override
    public Quintuple<A, B, C, D, E> retryIf(final Predicate<Throwable> predicate,
                                              final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
        return new Quintuple<>(_1.retryIf(predicate,
                                          attempts
                                         ),
                               _2.retryIf(predicate,
                                          attempts
                                         ),
                               _3.retryIf(predicate,
                                          attempts
                                         ),
                               _4.retryIf(predicate,
                                          attempts
                                         ),
                               _5.retryIf(predicate,
                                          attempts
                                         )
        );
    }


    @Override
    public Future<Tuple5<A, B, C, D, E>> get() {
        return CompositeFuture.all(_1.get(),
                                   _2.get(),
                                   _3.get(),
                                   _4.get(),
                                   _5.get()
                                  )
                              .map(it -> new Tuple5<>(it.resultAt(0),
                                                      it.resultAt(1),
                                                      it.resultAt(2),
                                                      it.resultAt(3),
                                                      it.resultAt(4)
                              ));
    }
}
