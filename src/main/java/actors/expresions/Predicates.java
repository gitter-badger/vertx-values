package actors.expresions;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Predicates
{

  public static Supplier<Future<Boolean>> and(final Supplier<Future<Boolean>> a,
                                              final Supplier<Future<Boolean>>... others) {


    return ()->
    {
      List<Future> futures = new ArrayList<>();
      futures.add(a.get());
      for(Supplier<Future<Boolean>> sup :others )
        futures.add(sup.get());
      return CompositeFuture.all(futures)
                            .map(l -> l.result()
                                       .list()
                                       .stream()
                                       .allMatch(it -> it instanceof Boolean && ((Boolean) it))
                                );
    };
  }


  public static Supplier<Future<Boolean>> or(final Supplier<Future<Boolean>> a,
                                             final Supplier<Future<Boolean>>... others) {

    return ()->
    {
      List<Future> futures = new ArrayList<>();
      futures.add(a.get());
      for(Supplier<Future<Boolean>> sup :others )futures.add(sup.get());
      return CompositeFuture.any(futures)
                            .map(l -> l.result()
                                       .list()
                                       .stream()
                                       .anyMatch(it -> it instanceof Boolean && ((Boolean) it))
                                );
    };

  }

}
