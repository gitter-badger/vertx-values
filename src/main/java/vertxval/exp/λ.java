package vertxval.exp;

import java.util.function.Function;

public interface λ<I,O> extends Function<I, Val<O>> {

    @Override
    default <V> Function<I, V> andThen(Function<? super Val<O>, ? extends V> after) {
        return null;
    }

    @Override
    default <V> Function<V, Val<O>> compose(Function<? super V, ? extends I> before) {
        return null;
    }
}
