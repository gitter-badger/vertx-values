package vertxval.exp;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class IfElse<O> extends AbstractVal<O> {

    private final Val<Boolean> predicate;
    private Val<O> consequence;
    private Val<O> alternative;

    public static <O> IfElse<O> predicate(Val<Boolean> predicate) {
        return new IfElse<>(requireNonNull(predicate));
    }

    public static <O> IfElse<O> predicate(Supplier<Future<Boolean>> predicate) {
        return new IfElse<>(Cons.of(requireNonNull(predicate)));
    }

    IfElse(final Val<Boolean> predicate) {
        this.predicate = requireNonNull(predicate);
    }

    public IfElse<O> consequence(final Val<O> consequence) {
        this.consequence = requireNonNull(consequence);
        return this;
    }

    public IfElse<O> alternative(final Val<O> alternative) {
        this.alternative = requireNonNull(alternative);
        return this;
    }


    @Override
    public Future<O> get() {
        final Future<Boolean> futureCon = predicate.get();
        return futureCon.flatMap(c -> {
            if (c) return consequence.get();
            else return alternative.get();
        });
    }

    @Override
    public <P> Val<P> map(final Function<O, P> fn) {
        requireNonNull(fn);
        return new IfElse<P>(predicate).alternative(alternative.map(fn))
                                       .consequence(consequence.map(fn));
    }

    @Override
    public IfElse<O> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new IfElse<O>(predicate.retry(attempts))
                .consequence(consequence.retry(attempts))
                .alternative(alternative.retry(attempts));
    }

    @Override
    public IfElse<O> retryIf(final Predicate<Throwable> predicate,
                          final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
        return new IfElse<O>(this.predicate.retryIf(predicate,
                                                    attempts))
                .consequence(consequence.retryIf(predicate,
                                                 attempts))
                .alternative(alternative.retryIf(predicate,
                                                 attempts));
    }


}
