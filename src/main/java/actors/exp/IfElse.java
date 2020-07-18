package actors.exp;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class IfElse<O> extends AbstractExp<O> {

    final Exp<Boolean> predicate;
    Exp<O> consequence;
    Exp<O> alternative;

    public static <O> IfElse<O> predicate(Exp<Boolean> predicate) {
        return new IfElse<>(predicate);
    }

    public static <O> IfElse<O> predicate(Supplier<Future<Boolean>> predicate) {
        return new IfElse<>(Val.of(predicate));
    }

    IfElse(final Exp<Boolean> predicate) {
        this.predicate = predicate;
    }

    public IfElse<O> consequence(final Exp<O> consequence) {
        this.consequence = consequence;
        return this;
    }

    public IfElse<O> consequence(final Supplier<Future<O>> consequence) {
        this.consequence = Val.of(consequence);
        return this;
    }

    public IfElse<O> alternative(final Exp<O> alternative) {
        this.alternative = alternative;
        return this;
    }

    public IfElse<O> alternative(final Supplier<Future<O>> alternative) {
        this.alternative = Val.of(alternative);
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
    public <P> Exp<P> map(final Function<O, P> fn) {
        return new IfElse<P>(predicate).alternative(alternative.map(fn))
                                       .consequence(consequence.map(fn));
    }

    @Override
    public O result() {
        return get().result();
    }

    @Override
    public Exp<O> retry(final int attempts) {
        return new IfElse<O>(predicate.retry(attempts))
                .consequence(consequence.retry(attempts))
                .alternative(alternative.retry(attempts));
    }

    @Override
    public Exp<O> retryIf(final Predicate<Throwable> predicate,
                          final int attempts) {
        return new IfElse<O>(this.predicate.retryIf(predicate,attempts))
                .consequence(consequence.retryIf(predicate,attempts))
                .alternative(alternative.retryIf(predicate,attempts));
    }


}
