package actors.expresions;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Predicates {

    public static Exp<Boolean> or(final Exp<Boolean> a,
                                  final Exp<Boolean>... others) {

        return Val.of(() ->
                      {
                          List<Future> futures = new ArrayList<>();
                          futures.add(a.get());
                          for (Supplier<Future<Boolean>> sup : others) futures.add(sup.get());
                          return CompositeFuture.any(futures)
                                                .map(l -> l.result()
                                                           .list()
                                                           .stream()
                                                           .anyMatch(it -> it instanceof Boolean && ((Boolean) it))
                                                    );
                      });

    }


    public static Exp<Boolean> and(final Exp<Boolean> a,
                                   final Exp<Boolean>... others) {


        return Val.of(() ->
                      {
                          List<Future> futures = new ArrayList<>();
                          futures.add(a.get());
                          for (Supplier<Future<Boolean>> sup : others)
                              futures.add(sup.get());
                          return CompositeFuture.all(futures)
                                                .map(l -> l.result()
                                                           .list()
                                                           .stream()
                                                           .allMatch(it -> it instanceof Boolean && ((Boolean) it))
                                                    );
                      });
    }
}
