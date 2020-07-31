package vertxval.exp;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public final class Or extends AbstractVal<Boolean> {

    Or(final List<Val<Boolean>> exps) {
        this.exps = requireNonNull(exps);
    }

    final List<Val<Boolean>> exps;

    @SafeVarargs
    public static Val<Boolean> of(final Val<Boolean> a,
                                  final Val<Boolean>... others) {
        List<Val<Boolean>> exps = new ArrayList<>();
        exps.add(requireNonNull(a));
        for (final Val<Boolean> other : others) {
            exps.add(requireNonNull(other));
        }
        return new Or(exps);
    }

    @Override
    public <P> Val<P> map(final Function<Boolean, P> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().map(fn));
    }

    @Override
    public Boolean result() {
        return exps.stream()
                        .map(s -> s.get()
                                   .result())
                        .reduce(false,
                                (a, b) -> a || b);
    }

    @Override
    public Val<Boolean> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new Or(exps.stream().map(it->it.retry(attempts))
                           .collect(Collectors.toList()));
    }

    @Override
    public Val<Boolean> retryIf(final Predicate<Throwable> predicate,
                                final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
        return new Or(exps.stream().map(it->it.retryIf(predicate,attempts))
                          .collect(Collectors.toList()));
    }



    @Override
    public Future<Boolean> get() {
        return CompositeFuture.all(exps.stream()
                                            .map(Supplier::get)
                                            .collect(Collectors.toList())
                                  )
                              .map(l -> l.result()
                                         .list()
                                         .stream()
                                         .anyMatch(it -> it instanceof Boolean && ((Boolean) it))
                                  );
    }
}
