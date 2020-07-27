package actors.exp;

import io.vavr.Tuple5;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;


public final class Quintuple<A, B, C, D, E> extends AbstractExp<Tuple5<A, B, C,D,E>> {

    private final Exp<A> _1;
    private final Exp<B> _2;
    private final Exp<C> _3;
    private final Exp<D> _4;
    private final Exp<E> _5;


    private Quintuple(final Exp<A> _1,
                      final Exp<B> _2,
                      final Exp<C> _3,
                      final Exp<D> _4,
                      final Exp<E> _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    public static <A, B, C, D, E> Quintuple<A, B, C, D, E> of(Exp<A> _1,
                                                              Exp<B> _2,
                                                              Exp<C> _3,
                                                              Exp<D> _4,
                                                              Exp<E> _5) {
        return new Quintuple<>(_1,
                               _2,
                               _3,
                               _4,
                               _5
        );
    }

    @Override
    public <P> Exp<P> map(final Function<Tuple5<A, B, C, D, E>, P> fn) {
        return Val.of(() -> get().map(fn));
    }



    @Override
    public Tuple5<A, B, C, D, E> result() {
        return new Tuple5<>(_1.get()
                              .result(),
                            _2.get()
                              .result(),
                            _3.get()
                              .result(),
                            _4.get()
                              .result(),
                            _5.get()
                              .result()
        );
    }

    @Override
    public Exp<Tuple5<A, B, C, D, E>> retry(final int attempts) {
        return new Quintuple<>(_1.retry(attempts),
                               _2.retry(attempts),
                               _3.retry(attempts),
                               _4.retry(attempts),
                               _5.retry(attempts)
        );
    }

    @Override
    public Exp<Tuple5<A, B, C, D, E>> retryIf(final Predicate<Throwable> predicate,
                                              final int attempts) {
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
