package vertxval.exp;

import io.vavr.collection.List;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class SeqVal<O> extends AbstractVal<List<O>> {

    @SuppressWarnings("rawtypes")
    public final static SeqVal EMPTY = new SeqVal<>(List.empty());

    List<Val<O>> seq;

    @SuppressWarnings("unchecked")
    public static <O> SeqVal<O> empty() {
        return EMPTY;
    }

    public SeqVal(final List<Val<O>> seq) {
        this.seq = requireNonNull(seq);
    }

    @Override
    public <P> Val<P> map(final Function<List<O>, P> fn) {
        requireNonNull(fn);
        return Cons.of(() -> get().map(fn));
    }


    @Override
    public SeqVal<O> retry(final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        return new SeqVal<>(seq.map(it -> it.retry(attempts)));
    }

    @Override
    public SeqVal<O> retryIf(final Predicate<Throwable> predicate,
                             final int attempts) {
        if (attempts < 1) throw new IllegalArgumentException("attempts < 1");
        requireNonNull(predicate);
        return new SeqVal<>(seq.map(it -> it.retryIf(predicate,
                                                     attempts
                                                    )));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Future<List<O>> get() {
        java.util.List futures = seq.map(Supplier::get)
                                    .toJavaList();
        return CompositeFuture.all(futures)
                              .map(c -> {
                                  List<O>                result    = List.empty();
                                  java.util.List<Object> completed = c.list();
                                  for (Object o : completed) {
                                      O element = (O) o;
                                      result = result.append(element);
                                  }
                                  return result;
                              });

    }

    public SeqVal<O> append(final Val<O> exp) {
        return new SeqVal<>(seq.append(requireNonNull(exp)));
    }

    public SeqVal<O> prepend(final Val<O> exp) {
        return new SeqVal<>(seq.prepend(requireNonNull(exp)));
    }

    public Val<O> head() {
        return seq.head();
    }

    public SeqVal<O> tail() {
        return new SeqVal<>(seq.tail());
    }

    public int size() {
        return seq.size();
    }

    public boolean isEmpty() {
        return seq.isEmpty();
    }
}
