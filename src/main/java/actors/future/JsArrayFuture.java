package actors.future;

import io.vavr.collection.List;
import io.vertx.core.Future;
import jsonvalues.JsArray;
import jsonvalues.JsValue;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;


/**
 Represents a supplier of a completable future which result is a json array. It has the same
 recursive structure as a json array. Each index of the array is a completable future that it's
 executed asynchronously. When all the futures are completed, all the results are combined into
 a json array.
 {@code
 JsArrayFuture.of(() -> Future.succeededFuture(1),
 () ->  Future.succeededFuture("a"),
 () ->  Future.succeededFuture(true)
 ) = Future(JsArray(1,"a",true))
 }
 */

public class JsArrayFuture implements JsFuture<JsArray> {
    private List<JsFuture<?>> array = List.empty();

    private JsArrayFuture() {
    }

    private JsArrayFuture(final JsFuture<?> fut,
                          final JsFuture<?>... others
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
    public static JsArrayFuture tuple(final JsFuture<?> head,
                                      final JsFuture<?>... tail
                                     ) {
        return new JsArrayFuture(requireNonNull(head),
                                 requireNonNull(tail)
        );
    }

    /**
     returns a JsArrayFuture that is completed returning the empty Json array

     @return a JsArrayFuture
     */
    public static JsArrayFuture empty() {
        return new JsArrayFuture();
    }

    /**
     it triggers the execution of all the completable futures, combining the results into a JsArray

     @return a CompletableFuture of a json array
     */
    @Override
    public Future<JsArray> get() {
        Future<JsArray> result = Future.succeededFuture(JsArray.empty());

        for (final JsFuture<? extends JsValue> future : array) {
            result = result.flatMap(arr -> future.get()
                                                 .map(v -> arr.append(v)));
        }

        return result;
    }

    public JsArrayFuture append(final JsFuture<?> future) {

        final JsArrayFuture arrayFuture = new JsArrayFuture();
        arrayFuture.array = arrayFuture.array.append(future);
        return arrayFuture;
    }
}
