package actors.exp;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Or  extends AbstractExp<Boolean>{

    Or(final List<Exp<Boolean>> exps) {
        this.exps = exps;
    }

    final List<Exp<Boolean>> exps;

    public static Exp<Boolean> of(final Exp<Boolean> a,
                                  final Exp<Boolean>... others) {
        List<Exp<Boolean>> exps = new ArrayList<>();
        exps.add(a);
        exps.addAll(Arrays.asList(others));
        return new Or(exps);
    }

    public static Exp<Boolean> of(final Supplier<Future<Boolean>> a,
                                  final Supplier<Future<Boolean>>... others) {
        List<Exp<Boolean>> suppliers = new ArrayList<>();
        suppliers.add(Val.of(a));
        suppliers.addAll(Arrays.stream(others)
                               .map(Val::of).collect(Collectors.toList()));
        return new Or(suppliers);
    }


    @Override
    public <P> Exp<P> map(final Function<Boolean, P> fn) {
        return Val.of(() -> get().map(fn));
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
    public Exp<Boolean> retry(final int attempts) {
        return new Or(exps.stream().map(it->it.retry(attempts))
                           .collect(Collectors.toList()));
    }

    @Override
    public Exp<Boolean> retryIf(final Predicate<Throwable> predicate,
                                final int attempts) {
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
