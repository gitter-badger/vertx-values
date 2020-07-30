package vertxval.exp;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class And extends AbstractVal<Boolean> {

    And(final List<Val<Boolean>> exps) {
        this.exps = exps;
    }

    final List<Val<Boolean>> exps;

    @SafeVarargs
    public static Val<Boolean> of(final Val<Boolean> a,
                                  final Val<Boolean>... others) {
        List<Val<Boolean>> exps = new ArrayList<>();
        exps.add(a);
        for (final Val<Boolean> other : others) {
            exps.add(other);
        }
        return new And(exps);
    }

    @Override
    public <P> Val<P> map(final Function<Boolean, P> fn) {
        return Cons.of(() -> get().map(fn));
    }

    @Override
    public Boolean result() {
        return exps.stream()
                   .map(s -> s.get()
                              .result()
                       )
                   .reduce(true,
                           (a, b) -> a && b
                          );
    }

    @Override
    public Val<Boolean> retry(final int attempts) {
        return new And(exps.stream()
                           .map(it -> it.retry(attempts))
                           .collect(Collectors.toList()));
    }

    @Override
    public Val<Boolean> retryIf(final Predicate<Throwable> predicate,
                                final int attempts) {
        return new And(exps.stream()
                           .map(it -> it.retryIf(predicate,attempts))
                           .collect(Collectors.toList()));
    }


    @Override
    public Future<Boolean> get() {
        return CompositeFuture.all(exps.stream()
                                       .map(Supplier::get)
                                       .collect(Collectors.toList()))
                              .map(l -> l.result()
                                         .list()
                                         .stream()
                                         .allMatch(it -> it instanceof Boolean && ((Boolean) it))
                                  );
    }
}
