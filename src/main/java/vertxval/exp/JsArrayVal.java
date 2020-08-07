package vertxval.exp;

import io.vavr.collection.List;
import io.vertx.core.Future;
import jsonvalues.JsArray;
import jsonvalues.JsValue;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;


/**
 Represents a supplier of a completable future which result is a json array. It has the same
 recursive structure as a json array. Each index of the array is a completable future that it's
 executed asynchronously. When all the futures are completed, all the results are combined into
 a json array.
 {@code
 JsArrayFuture.of(
 () -> Future.succeededFuture(1),
 () -> Future.succeededFuture("a"),
 () -> Future.succeededFuture(true)
 ) = Future(JsArray(1,"a",true)
 )
 }
 */

public final class JsArrayVal extends AbstractVal<JsArray> {
    private List<Val<? extends JsValue>> seq = List.empty();

    private JsArrayVal(List<Val<? extends JsValue>> seq) {
        this.seq = seq;
    }

    private JsArrayVal(){}

    @SafeVarargs
    private JsArrayVal(final Val<? extends JsValue> fut,
                       final Val<? extends JsValue>... others
                      ) {
        seq = seq.append(fut);
        for (Val<? extends JsValue> other : others) {
            seq = seq.append(other);
        }
    }


    /**
     returns a JsArrayFuture from the given head and the tail

     @param head the head
     @param tail the tail
     @return a new JsArrayFuture
     */
    @SafeVarargs
    public static JsArrayVal tuple(final Val<? extends JsValue> head,
                                   final Val<? extends JsValue>... tail
                                  ) {
        return new JsArrayVal(requireNonNull(head),
                              requireNonNull(tail)
        );
    }


    /**
     it triggers the execution of all the completable futures, combining the results into a JsArray

     @return a CompletableFuture of a json array
     */
    @Override
    public Future<jsonvalues.JsArray> get() {
        Future<jsonvalues.JsArray> result = Future.succeededFuture(jsonvalues.JsArray.empty());

        for (final Val<? extends JsValue> future : seq) {
            result = result.flatMap(arr -> future.get()
                                                 .map(v -> arr.append(v)));
        }
        return result;
    }

    public JsArrayVal append(final Val<? extends JsValue> future) {

        final JsArrayVal arrayFuture = new JsArrayVal();
        arrayFuture.seq = arrayFuture.seq.append(future);
        return arrayFuture;
    }

    @Override
    public <P> Val<P> map(final Function<jsonvalues.JsArray, P> fn) {
        return Cons.of(() -> get().map(fn));
    }


    @Override
    public JsArrayVal retry(final int attempts) {
        return new JsArrayVal(seq.map(it->it.retry(attempts)));
    }

    @Override
    public JsArrayVal retryIf(final Predicate<Throwable> predicate,
                                final int attempts) {
        return new JsArrayVal(seq.map(it->it.retryIf(predicate, attempts)));

    }


}
