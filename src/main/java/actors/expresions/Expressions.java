package actors.expresions;

import io.vertx.core.Future;

import java.util.function.Supplier;

public class Expressions
{
  public static <O> When<O> when(final Supplier<Future<Boolean>> predicate){
    return new When<>(predicate);
  }
}
