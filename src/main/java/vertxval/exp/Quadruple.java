package vertxval.exp;

import io.vavr.Tuple4;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;


public class Quadruple<A, B, C, D> extends AbstractExp<Tuple4<A, B,C,D>> {

    private final Exp<A> _1;
    private final Exp<B> _2;
    private final Exp<C> _3;
    private final Exp<D> _4;

    private Quadruple(final Exp<A> _1,
                      final Exp<B> _2,
                      final Exp<C> _3,
                      final Exp<D> _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    public static <A, B, C, D> Quadruple<A, B, C, D> of(final Exp<A> _1,
                                                        final Exp<B> _2,
                                                        final Exp<C> _3,
                                                        final Exp<D> _4) {
        return new Quadruple<>(_1,
                               _2,
                               _3,
                               _4
        );
    }



    @Override
    public <P> Exp<P> map(final Function<Tuple4<A, B, C, D>, P> fn) {
        return Val.success(() -> get().map(fn));
    }


    @Override
    public Tuple4<A, B, C, D> result() {
        return new Tuple4<>(_1.get()
                              .result(),
                            _2.get()
                              .result(),
                            _3.get()
                              .result(),
                            _4.get()
                              .result()
        );
    }

    @Override
    public Exp<Tuple4<A, B, C, D>> retry(final int attempts) {
        return new Quadruple<>(_1.retry(attempts),
                               _2.retry(attempts),
                               _3.retry(attempts),
                               _4.retry(attempts)
        );
    }

    @Override
    public Exp<Tuple4<A, B, C, D>> retryIf(final Predicate<Throwable> predicate,
                                           final int attempts) {
        return new Quadruple<>(_1.retryIf(predicate,
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
                                         )
        );
    }

    @Override
    public Future<Tuple4<A, B, C, D>> get() {
        return CompositeFuture.all(_1.get(),
                                   _2.get(),
                                   _3.get(),
                                   _4.get()
                                  )
                              .map(it -> new Tuple4<>(it.resultAt(0),
                                                      it.resultAt(1),
                                                      it.resultAt(2),
                                                      it.resultAt(3)
                              ));
    }
}
