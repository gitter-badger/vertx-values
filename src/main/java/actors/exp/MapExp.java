package actors.exp;

import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;

import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 Represents a supplier of a vertx future which result is a json object. It has the same
 recursive structure as a json object. Each key has a future associated that it's
 executed asynchronously. When all the futures are completed, all the results are combined into
 a json object:
 {@code
 JsObjFuture.of(
 a -> () -> Future.succeededFuture(1),
 b -> () -> Future.succeededFuture("a"),
 c -> () -> Future.succeededFuture(true)
 ) =
 Future.succeededFuture(JsObj(a->1,b->"a",c->true))}
 */
public final class MapExp extends AbstractExp<Map<String, ?>> {

    public final static MapExp EMPTY = new MapExp();

    private Map<String, Exp<?>> bindings = LinkedHashMap.empty();

    private MapExp() {}

    private MapExp(final Map<String, Exp<?>> bindings) {
        this.bindings = bindings;
    }


    /**
     static factory method to create a JsObjFuture of one mapping

     @param key the key
     @param exp the mapping associated to the key
     @return a JsObjFuture
     */
    public static MapExp of(final String key,
                            final Exp<?> exp
                           ) {
        MapExp obj = new MapExp();
        obj.bindings = obj.bindings.put(key,
                                        exp
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of one mapping

     @param key1 the first key
     @param fut1 the mapping associated to the first key
     @param key2 the second key
     @param fut2 the mapping associated to the second key
     @return a JsObjFuture
     */
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2
                           ) {
        MapExp obj = MapExp.of(key1,
                               fut1
                              );
        obj.bindings = obj.bindings.put(requireNonNull(key2),
                                        requireNonNull(fut2)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3
                           ) {
        MapExp obj = MapExp.of(key1,
                               fut1,
                               key2,
                               fut2
                              );
        obj.bindings = obj.bindings.put(requireNonNull(key3),
                                        requireNonNull(fut3)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4
                           ) {
        MapExp obj = MapExp.of(key1,
                               fut1,
                               key2,
                               fut2,
                               key3,
                               fut3
                              );
        obj.bindings = obj.bindings.put(requireNonNull(key4),
                                        requireNonNull(fut4)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5
                           ) {
        MapExp obj = MapExp.of(key1,
                               fut1,
                               key2,
                               fut2,
                               key3,
                               fut3,
                               key4,
                               fut4
                              );
        obj.bindings = obj.bindings.put(requireNonNull(key5),
                                        requireNonNull(fut5)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key6),
                                        requireNonNull(fut6)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key7),
                                        requireNonNull(fut7)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key8),
                                        requireNonNull(fut8)
                                       );
        return obj;

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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key9),
                                        requireNonNull(fut9)
                                       );
        return obj;


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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9,
                            final String key10,
                            final Exp<?> fut10
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key10),
                                        requireNonNull(fut10)
                                       );
        return obj;

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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9,
                            final String key10,
                            final Exp<?> fut10,
                            final String key11,
                            final Exp<?> fut11
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key11),
                                        requireNonNull(fut11)
                                       );
        return obj;

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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9,
                            final String key10,
                            final Exp<?> fut10,
                            final String key11,
                            final Exp<?> fut11,
                            final String key12,
                            final Exp<?> fut12
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key12),
                                        requireNonNull(fut12)
                                       );
        return obj;

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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9,
                            final String key10,
                            final Exp<?> fut10,
                            final String key11,
                            final Exp<?> fut11,
                            final String key12,
                            final Exp<?> fut12,
                            final String key13,
                            final Exp<?> fut13
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key13),
                                        requireNonNull(fut13)
                                       );
        return obj;
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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9,
                            final String key10,
                            final Exp<?> fut10,
                            final String key11,
                            final Exp<?> fut11,
                            final String key12,
                            final Exp<?> fut12,
                            final String key13,
                            final Exp<?> fut13,
                            final String key14,
                            final Exp<?> fut14
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key14),
                                        requireNonNull(fut14)
                                       );
        return obj;

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
    public static MapExp of(final String key1,
                            final Exp<?> fut1,
                            final String key2,
                            final Exp<?> fut2,
                            final String key3,
                            final Exp<?> fut3,
                            final String key4,
                            final Exp<?> fut4,
                            final String key5,
                            final Exp<?> fut5,
                            final String key6,
                            final Exp<?> fut6,
                            final String key7,
                            final Exp<?> fut7,
                            final String key8,
                            final Exp<?> fut8,
                            final String key9,
                            final Exp<?> fut9,
                            final String key10,
                            final Exp<?> fut10,
                            final String key11,
                            final Exp<?> fut11,
                            final String key12,
                            final Exp<?> fut12,
                            final String key13,
                            final Exp<?> fut13,
                            final String key14,
                            final Exp<?> fut14,
                            final String key15,
                            final Exp<?> fut15
                           ) {
        MapExp obj = MapExp.of(key1,
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
        obj.bindings = obj.bindings.put(requireNonNull(key15),
                                        requireNonNull(fut15)
                                       );
        return obj;

    }


    /**
     returns a new object future inserting the given future at the given key

     @param key    the given key
     @param future the given future
     @return a new JsObjFuture
     */
    public MapExp set(final String key,
                      final Exp<?> future
                     ) {
        return new MapExp(bindings.put(requireNonNull(key),
                                       requireNonNull(future)
                                      ));
    }

    public boolean isEmpty() {
        return bindings.isEmpty();
    }


    @Override
    public <P> Exp<P> map(final Function<Map<String, ?>, P> fn) {
        return Val.of(() -> get().map(fn));
    }

    @Override
    public Map<String, ?> result() {
        return bindings.map((k, v) -> new Tuple2<>(k,
                                                   v.get()
                                                    .result()
                            )
                           );
    }

    @Override
    public Exp<Map<String, ?>> retry(final int attempts) {
        return new MapExp(bindings.mapValues(it -> it.retry(attempts)));
    }

    @Override
    public Exp<Map<String, ?>> retryIf(final Predicate<Throwable> predicate,
                                       final int attempts) {
        return new MapExp(bindings.mapValues(it -> it.retryIf(predicate,
                                                              attempts
                                                             )));

    }

    @Override
    public Future<Map<String, ?>> get() {


        List<String> keys = bindings.keysIterator()
                                    .toList();

        Map<String, Future> futures = bindings.map((k, v) -> new Tuple2<>(k,
                                                                          v.get()
                                                   )
                                                  );

        return CompositeFuture.all(futures.values()
                                          .toJavaList()
                                  )
                              .map(r -> {
                                  Map<String, ?> result = LinkedHashMap.empty();
                                  java.util.List<Object> list = r.result()
                                                                 .list();
                                  for (int i = 0; i < list.size(); i++
                                  ) {
                                      result = result.put(new Tuple2(keys.get(i),
                                                                       list.get(i))
                                                         );

                                  }
                                  return result;

                              });


    }
}
