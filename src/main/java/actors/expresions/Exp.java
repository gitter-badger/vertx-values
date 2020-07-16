package actors.expresions;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Exp<O> extends Supplier<Future<O>> {

    <P> Exp<P> map(Function<O, P> fn);
}
