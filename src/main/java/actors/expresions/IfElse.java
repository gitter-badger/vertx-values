package actors.expresions;

import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Supplier;

public class IfElse<O> implements Exp<O> {

    private final Exp<Boolean> predicate;
    private Exp<O> consequence;
    private Exp<O> alternative;

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
        if (futureCon.failed()) return Future.failedFuture(futureCon.cause());
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
}
