package actors.exp;

import io.vertx.core.Future;

import java.util.function.Function;

abstract class AbstractExp<O> implements Exp<O>{
    @Override
    public  Exp<O> recover(final Function<Throwable, O> fn) {
        return Val.of(()->get().compose(Future::succeededFuture,
                                        e -> Future.succeededFuture(fn.apply(e))
                                       )
                     );
    }

    @Override
    public Exp<O> recoverWith(final Function<Throwable, Exp<O>> fn) {
        return Val.of(()->get().compose(Future::succeededFuture,
                                        e -> fn.apply(e).get()
                                       )
                     );
    }

    @Override
    public Exp<O> fallbackTo(final Function<Throwable, Exp<O>> fn) {
        return Val.of(()->get().compose(Future::succeededFuture,
                                        e -> fn.apply(e).get().compose(Future::succeededFuture,
                                                                       e1->Future.failedFuture(e))
                                       )
                     );

    }
}
