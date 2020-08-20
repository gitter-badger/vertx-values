package vertxval.exp;

import io.vertx.core.Future;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

public class ReturnsConsOrFailure<O> implements Supplier<Val<O>> {
    private int counter;
    private final IntFunction<Throwable> getError;
    private final IntPredicate testIfError;
    private final O value;


    public ReturnsConsOrFailure(final IntFunction<Throwable> getError,
                                final IntPredicate testIfError,
                                final O value) {
        this.getError = getError;
        this.testIfError = testIfError;
        this.value = value;
    }

    @Override
    public Val<O> get() {
        return Cons.of(() -> {
                           counter += 1;
                           return testIfError.test(counter) ?
                                  Future.failedFuture(getError.apply(counter)) :
                                  Future.succeededFuture(value);
                       }
                      );
    }
}
