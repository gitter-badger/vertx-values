package actors;

import io.vertx.core.Future;

import java.util.function.Function;

public interface Actor<I,O> extends Function<I, Future<O>> {


}
