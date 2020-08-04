package vertxval.exp;

import io.vavr.Tuple4;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;


public class Quadruple<A, B, C, D> extends AbstractVal<Tuple4<A, B,C,D>> {

    private final Val<A> _1;
    private final Val<B> _2;
    private final Val<C> _3;
    private final Val<D> _4;

    private Quadruple(final Val<A> _1,
                      final Val<B> _2,
                      final Val<C> _3,
                      final Val<D> _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    public static <A, B, C, D> Quadruple<A, B, C, D> of(final Val<A> _1,
                                                        final Val<B> _2,
                                                        final Val<C> _3,
                                                        final Val<D> _4) {
        return new Quadruple<>(requireNonNull(_1),
                               requireNonNull(_2),
                               requireNonNull(_3),
                               requireNonNull(_4)
        );
    }



    @Override
    public <P> Val<P> map(final Function<Tuple4<A, B, C, D>, P> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().map(fn));
    }



    @Override
    public Val<Tuple4<A, B, C, D>> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new Quadruple<>(_1.retry(attempts),
                               _2.retry(attempts),
                               _3.retry(attempts),
                               _4.retry(attempts)
        );
    }

    @Override
    public Val<Tuple4<A, B, C, D>> retryIf(final Predicate<Throwable> predicate,
                                           final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
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
