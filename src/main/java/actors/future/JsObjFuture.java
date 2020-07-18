package actors.future;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.TreeMap;
import io.vertx.core.Future;
import jsonvalues.JsObj;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 Represents a supplier of a vertx future which result is a json object. It has the same
 recursive structure as a json object. Each key has a future associated that it's
 executed asynchronously. When all the futures are completed, all the results are combined into
 a json object:
 {@code
 JsObjFuture.of(a -> () -> Future.succeededFuture(1),
 b -> () -> Future.succeededFuture("a"),
 c -> () -> Future.succeededFuture(true)
 ) =
 Future.succeededFuture(JsObj(a->1,
 b->"a",
 c->true
 )
 )
 }
 */
public class JsObjFuture implements JsFuture<JsObj> {

    private Map<String, JsFuture<?>> bindings = TreeMap.empty();


    private JsObjFuture(final Map<String, JsFuture<?>> bindings) {
        this.bindings = bindings;
    }

    private JsObjFuture() {
    }

    /**
     static factory method to create a JsObjFuture of one mapping

     @param key the key
     @param fut the mapping associated to the key
     @return a JsObjFuture
     */
    public static JsObjFuture of(final String key,
                                 final JsFuture<?> fut
                                ) {
        JsObjFuture future = new JsObjFuture();
        future.bindings = future.bindings.put(Objects.requireNonNull(key),
                                              Objects.requireNonNull(fut)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of one mapping

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @return a JsObjFuture
     */
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1
                                           );
        future.bindings = future.bindings.put(requireNonNull(key2),
                                              requireNonNull(fut2)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of three mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2
                                           );
        future.bindings = future.bindings.put(requireNonNull(key3),
                                              requireNonNull(fut3)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of four mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @param key4 the fourth key
     @param fut4 the mapping associated to the fourth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3
                                           );
        future.bindings = future.bindings.put(requireNonNull(key4),
                                              requireNonNull(fut4)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of five mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @param key4 the fourth key
     @param fut4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param fut5 the mapping associated to the fifth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4
                                           );
        future.bindings = future.bindings.put(requireNonNull(key5),
                                              requireNonNull(fut5)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of six mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @param key4 the fourth key
     @param fut4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param fut5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param fut6 the mapping associated to the sixth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5
                                           );
        future.bindings = future.bindings.put(requireNonNull(key6),
                                              requireNonNull(fut6)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of seven mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @param key4 the fourth key
     @param fut4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param fut5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param fut6 the mapping associated to the sixth key
     @param key7 the seventh key
     @param fut7 the mapping associated to the seventh key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6
                                           );
        future.bindings = future.bindings.put(requireNonNull(key7),
                                              requireNonNull(fut7)
                                             );
        return future;
    }

    /**
     static factory method to create a JsObjFuture of eight mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @param key4 the fourth key
     @param fut4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param fut5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param fut6 the mapping associated to the sixth key
     @param key7 the seventh key
     @param fut7 the mapping associated to the seventh key
     @param key8 the eighth key
     @param fut8 the mapping associated to the eighth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8
                                ) {

        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7
                                           );
        future.bindings = future.bindings.put(requireNonNull(key8),
                                              requireNonNull(fut8)
                                             );
        return future;

    }

    /**
     static factory method to create a JsObjFuture of nine mappings

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @param key3 the third key
     @param fut3 the mapping associated to the third key
     @param key4 the fourth key
     @param fut4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param fut5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param fut6 the mapping associated to the sixth key
     @param key7 the seventh key
     @param fut7 the mapping associated to the seventh key
     @param key8 the eighth key
     @param fut8 the mapping associated to the eighth key
     @param key9 the ninth key
     @param fut9 the mapping associated to the ninth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8
                                           );
        future.bindings = future.bindings.put(requireNonNull(key9),
                                              requireNonNull(fut9)
                                             );
        return future;


    }

    /**
     static factory method to create a JsObjFuture of ten mappings

     @param key1  the first key
     @param fut1  the mapping associated to the first key
     @param key2  the second key
     @param fut2  the mapping associated to the second key
     @param key3  the third key
     @param fut3  the mapping associated to the third key
     @param key4  the fourth key
     @param fut4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param fut5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param fut6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param fut7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param fut8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param fut9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param fut10 the mapping associated to the tenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9,
                                 final String key10,
                                 final JsFuture<?> fut10
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8,
                                            key9,
                                            fut9
                                           );
        future.bindings = future.bindings.put(requireNonNull(key10),
                                              requireNonNull(fut10)
                                             );
        return future;


    }

    /**
     static factory method to create a JsObjFuture of eleven mappings

     @param key1  the first key
     @param fut1  the mapping associated to the first key
     @param key2  the second key
     @param fut2  the mapping associated to the second key
     @param key3  the third key
     @param fut3  the mapping associated to the third key
     @param key4  the fourth key
     @param fut4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param fut5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param fut6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param fut7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param fut8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param fut9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param fut10 the mapping associated to the eleventh key
     @param key11 the tenth key
     @param fut11 the mapping associated to the eleventh key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9,
                                 final String key10,
                                 final JsFuture<?> fut10,
                                 final String key11,
                                 final JsFuture<?> fut11
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8,
                                            key9,
                                            fut9,
                                            key10,
                                            fut10
                                           );
        future.bindings = future.bindings.put(requireNonNull(key11),
                                              requireNonNull(fut11)
                                             );
        return future;

    }

    /**
     static factory method to create a JsObjFuture of twelve mappings

     @param key1  the first key
     @param fut1  the mapping associated to the first key
     @param key2  the second key
     @param fut2  the mapping associated to the second key
     @param key3  the third key
     @param fut3  the mapping associated to the third key
     @param key4  the fourth key
     @param fut4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param fut5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param fut6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param fut7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param fut8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param fut9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param fut10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param fut11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param fut12 the mapping associated to the twelfth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9,
                                 final String key10,
                                 final JsFuture<?> fut10,
                                 final String key11,
                                 final JsFuture<?> fut11,
                                 final String key12,
                                 final JsFuture<?> fut12
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8,
                                            key9,
                                            fut9,
                                            key10,
                                            fut10,
                                            key11,
                                            fut11
                                           );
        future.bindings = future.bindings.put(requireNonNull(key12),
                                              requireNonNull(fut12)
                                             );
        return future;


    }

    /**
     static factory method to create a JsObjFuture of thirteen mappings

     @param key1  the first key
     @param fut1  the mapping associated to the first key
     @param key2  the second key
     @param fut2  the mapping associated to the second key
     @param key3  the third key
     @param fut3  the mapping associated to the third key
     @param key4  the fourth key
     @param fut4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param fut5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param fut6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param fut7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param fut8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param fut9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param fut10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param fut11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param fut12 the mapping associated to the twelfth key,
     @param key13 the thirteenth key
     @param fut13 the mapping associated to the thirteenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9,
                                 final String key10,
                                 final JsFuture<?> fut10,
                                 final String key11,
                                 final JsFuture<?> fut11,
                                 final String key12,
                                 final JsFuture<?> fut12,
                                 final String key13,
                                 final JsFuture<?> fut13
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8,
                                            key9,
                                            fut9,
                                            key10,
                                            fut10,
                                            key11,
                                            fut11,
                                            key12,
                                            fut12
                                           );
        future.bindings = future.bindings.put(requireNonNull(key13),
                                              requireNonNull(fut13)
                                             );
        return future;


    }

    /**
     static factory method to create a JsObjFuture of fourteen mappings

     @param key1  the first key
     @param fut1  the mapping associated to the first key
     @param key2  the second key
     @param fut2  the mapping associated to the second key
     @param key3  the third key
     @param fut3  the mapping associated to the third key
     @param key4  the fourth key
     @param fut4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param fut5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param fut6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param fut7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param fut8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param fut9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param fut10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param fut11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param fut12 the mapping associated to the twelfth key,
     @param key13 the thirteenth key
     @param fut13 the mapping associated to the thirteenth key
     @param key14 the fourteenth key
     @param fut14 the mapping associated to the fourteenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9,
                                 final String key10,
                                 final JsFuture<?> fut10,
                                 final String key11,
                                 final JsFuture<?> fut11,
                                 final String key12,
                                 final JsFuture<?> fut12,
                                 final String key13,
                                 final JsFuture<?> fut13,
                                 final String key14,
                                 final JsFuture<?> fut14
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8,
                                            key9,
                                            fut9,
                                            key10,
                                            fut10,
                                            key11,
                                            fut11,
                                            key12,
                                            fut12,
                                            key13,
                                            fut13
                                           );
        future.bindings = future.bindings.put(requireNonNull(key14),
                                              requireNonNull(fut14)
                                             );
        return future;


    }

    /**
     static factory method to create a JsObjFuture of fifteen mappings

     @param key1  the first key
     @param fut1  the mapping associated to the first key
     @param key2  the second key
     @param fut2  the mapping associated to the second key
     @param key3  the third key
     @param fut3  the mapping associated to the third key
     @param key4  the fourth key
     @param fut4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param fut5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param fut6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param fut7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param fut8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param fut9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param fut10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param fut11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param fut12 the mapping associated to the twelfth key,
     @param key13 the thirteenth key
     @param fut13 the mapping associated to the thirteenth key
     @param key14 the fourteenth key
     @param fut14 the mapping associated to the fourteenth key
     @param key15 the fifteenth key
     @param fut15 the mapping associated to the fifteenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static JsObjFuture of(final String key1,
                                 final JsFuture<?> fut1,
                                 final String key2,
                                 final JsFuture<?> fut2,
                                 final String key3,
                                 final JsFuture<?> fut3,
                                 final String key4,
                                 final JsFuture<?> fut4,
                                 final String key5,
                                 final JsFuture<?> fut5,
                                 final String key6,
                                 final JsFuture<?> fut6,
                                 final String key7,
                                 final JsFuture<?> fut7,
                                 final String key8,
                                 final JsFuture<?> fut8,
                                 final String key9,
                                 final JsFuture<?> fut9,
                                 final String key10,
                                 final JsFuture<?> fut10,
                                 final String key11,
                                 final JsFuture<?> fut11,
                                 final String key12,
                                 final JsFuture<?> fut12,
                                 final String key13,
                                 final JsFuture<?> fut13,
                                 final String key14,
                                 final JsFuture<?> fut14,
                                 final String key15,
                                 final JsFuture<?> fut15
                                ) {
        JsObjFuture future = JsObjFuture.of(key1,
                                            fut1,
                                            key2,
                                            fut2,
                                            key3,
                                            fut3,
                                            key4,
                                            fut4,
                                            key5,
                                            fut5,
                                            key6,
                                            fut6,
                                            key7,
                                            fut7,
                                            key8,
                                            fut8,
                                            key9,
                                            fut9,
                                            key10,
                                            fut10,
                                            key11,
                                            fut11,
                                            key12,
                                            fut12,
                                            key13,
                                            fut13,
                                            key14,
                                            fut14
                                           );
        future.bindings = future.bindings.put(requireNonNull(key15),
                                              requireNonNull(fut15)
                                             );
        return future;



    }

    /**
     returns a JsObjFuture that is completed returning the empty Json object

     @return a JsObjFuture
     */
    public static JsObjFuture empty() {
        return new JsObjFuture(HashMap.empty());
    }


    /**
     returns a new object future inserting the given future at the given key

     @param key    the given key
     @param future the given future
     @return a new JsObjFuture
     */
    public JsObjFuture set(final String key,
                           final JsFuture<?> future
                          ) {
        final Map<String, JsFuture<?>> a = bindings.put(requireNonNull(key),
                                                        requireNonNull(future)
                                                       );
        return new JsObjFuture(a);
    }


    /**
     it triggers the execution of all the completable futures, combining the results into a JsObj

     @return a Future of a json object
     */
    @Override
    public Future<JsObj> get() {

        Future<JsObj> result = Future.succeededFuture(JsObj.empty());

        for (final Tuple2<String, JsFuture<?>> tuple : bindings.iterator()) {
            result = result.flatMap(obj -> tuple._2.get()
                                                   .map(v -> obj.set(tuple._1,
                                                                     v)));
        }


        return result;
    }


}
