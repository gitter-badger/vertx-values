package actors.expresions;

import io.vavr.collection.List;
import io.vertx.core.Future;
import jsonvalues.JsArray;
import jsonvalues.JsValue;

import java.util.Arrays;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;


/**
 Represents a supplier of a completable future which result is a json array. It has the same
 recursive structure as a json array. Each index of the array is a completable future that it's
 executed asynchronously. When all the futures are completed, all the results are combined into
 a json array.
 {@code
 JsArrayFuture.of(() -> Future.succeededFuture(1),
 () -> Future.succeededFuture("a"),
 () -> Future.succeededFuture(true)
 ) = Future(JsArray(1,"a",true))
 }
 */

public class JsArrayExp implements Exp<JsArray> {
    private List<Exp<? extends JsValue>> array = List.empty();

    private JsArrayExp() {
    }

    private JsArrayExp(final Exp<? extends JsValue> fut,
                       final Exp<? extends JsValue>... others
                      ) {
        array = array.append(fut)
                     .appendAll(Arrays.asList(others));
    }


    /**
     returns a JsArrayFuture from the given head and the tail

     @param head the head
     @param tail the tail
     @return a new JsArrayFuture
     */
    public static JsArrayExp tuple(final Exp<? extends JsValue> head,
                                   final Exp<? extends JsValue>... tail
                                  ) {
        return new JsArrayExp(requireNonNull(head),
                              requireNonNull(tail)
        );
    }

    /**
     it triggers the execution of all the completable futures, combining the results into a JsArray

     @return a CompletableFuture of a json array
     */
    @Override
    public Future<JsArray> get() {
        Future<JsArray> result = Future.succeededFuture(JsArray.empty());

        for (final Exp<? extends JsValue> future : array) {
            result = result.flatMap(arr -> future.get()
                                                 .map(v -> arr.append(v)));
        }

        return result;
    }

    public JsArrayExp append(final Exp<? extends JsValue> future) {

        final JsArrayExp arrayFuture = new JsArrayExp();
        arrayFuture.array = arrayFuture.array.append(future);
        return arrayFuture;
    }

    @Override
    public <P> Exp<P> map(final Function<JsArray, P> fn) {
        return Val.of(() -> get().map(fn));
    }
}
