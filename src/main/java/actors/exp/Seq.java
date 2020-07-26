package actors.exp;


import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vertx.core.Future;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class Seq<O> extends AbstractExp<List<O>> {

    private Array<Exp<O>> seq = Array.empty();

    private Seq(Array<Exp<O>> seq) {
        this.seq = seq;
    }

    private Seq() {
    }

    private Seq(final Exp<O> fut,
                final Exp<O>... others
               ) {
        seq = seq.append(fut)
                 .appendAll(Arrays.asList(others));
    }


    /**
     returns a JsArrayFuture from the given head and the tail

     @param head the head
     @param tail the tail
     @return a new JsArrayFuture
     */
    public static <O> Seq<O> of(final Exp<O> head,
                                final Exp<O> tail
                               ) {
        return new Seq<>(requireNonNull(head),
                         requireNonNull(tail)
        );
    }


    @Override
    public <P> Exp<P> map(final Function<List<O>, P> fn) {
        return null;
    }

    @Override
    public List<O> result() {
        return null;
    }

    @Override
    public Exp<List<O>> retry(final int attempts) {
        return null;
    }

    @Override
    public Exp<List<O>> retryIf(final Predicate<Throwable> predicate,
                                final int attempts) {
        return null;
    }

    @Override
    public Future<List<O>> get() {
        return null;
    }

    public Seq<O> append(Exp<O> exp) {
        return null;
    }

    public Seq<O> prepend(Exp<O> exp) {
        return null;
    }

    public Seq<O> tail(Exp<O> exp) {
        return null;
    }

    public Exp<O> head() {
        return null;
    }
}
