package actors.exp;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Exp<O> extends Supplier<Future<O>> {

    <P> Exp<P> map(Function<O, P> fn);

    O result();

    Exp<O> retry(int attempts);

    Exp<O> retryIf(Predicate<Throwable> predicate, int attempts);

    Exp<O> recover(Function<Throwable, O> fn);

    Exp<O> recoverWith(Function<Throwable, Exp<O>> fn);

    Exp<O> fallbackTo(Function<Throwable, Exp<O>> fn);


}
