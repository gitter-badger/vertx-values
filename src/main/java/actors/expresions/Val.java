package actors.expresions;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Supplier;

public class Val<O> implements Exp<O> {

    Supplier<Future<O>> fut;

    public static <O> Val<O> of(Supplier<Future<O>> supplier) {
        return new Val<>(supplier);
    }

    public static <O> Val<O> cons(O o) {
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
                               .map(fn));
    }


}
