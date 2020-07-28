package vertxval.exp;

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
public final class MapExp<O> extends AbstractExp<Map<String, O>> {

    public final static MapExp<?> EMPTY = new MapExp<>();

    private Map<String, Exp<O>> bindings = LinkedHashMap.empty();

    private MapExp() {
    }

    private MapExp(final Map<String, Exp<O>> bindings) {
        this.bindings = bindings;
    }


    /**
     static factory method to create a JsObjFuture of one mapping

     @param key the key
     @param exp the mapping associated to the key
     @return a JsObjFuture
     */
    public static <O> MapExp<O> of(final String key,
                                   final Exp<O> exp
                                  ) {
        MapExp<O> obj = new MapExp<>();
        obj.bindings = obj.bindings.put(key,
                                        exp
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of one mapping

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @return a JsObjFuture
     */
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key2),
                                        requireNonNull(exp2)
                                       );
        return obj;
    }

    /**
     static factory method to create a JsObjexpure of three mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key3),
                                        requireNonNull(exp3)
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of four mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @param key4 the fourth key
     @param exp4 the mapping associated to the fourth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key4),
                                        requireNonNull(exp4)
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of five mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @param key4 the fourth key
     @param exp4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param exp5 the mapping associated to the fifth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key5),
                                        requireNonNull(exp5)
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of six mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @param key4 the fourth key
     @param exp4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param exp5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param exp6 the mapping associated to the sixth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key6),
                                        requireNonNull(exp6)
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of seven mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @param key4 the fourth key
     @param exp4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param exp5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param exp6 the mapping associated to the sixth key
     @param key7 the seventh key
     @param exp7 the mapping associated to the seventh key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key7),
                                        requireNonNull(exp7)
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of eight mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @param key4 the fourth key
     @param exp4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param exp5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param exp6 the mapping associated to the sixth key
     @param key7 the seventh key
     @param exp7 the mapping associated to the seventh key
     @param key8 the eighth key
     @param exp8 the mapping associated to the eighth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key8),
                                        requireNonNull(exp8)
                                       );
        return obj;

    }


    /**
     static factory method to create a JsObjFuture of nine mappings

     @param key1 the first key
     @param exp1 the mapping associated to the first key
     @param key2 the second key
     @param exp2 the mapping associated to the second key
     @param key3 the third key
     @param exp3 the mapping associated to the third key
     @param key4 the fourth key
     @param exp4 the mapping associated to the fourth key
     @param key5 the fifth key
     @param exp5 the mapping associated to the fifth key
     @param key6 the sixth key
     @param exp6 the mapping associated to the sixth key
     @param key7 the seventh key
     @param exp7 the mapping associated to the seventh key
     @param key8 the eighth key
     @param exp8 the mapping associated to the eighth key
     @param key9 the ninth key
     @param exp9 the mapping associated to the ninth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key9),
                                        requireNonNull(exp9)
                                       );
        return obj;


    }


    /**
     static factory method to create a JsObjFuture of ten mappings

     @param key1  the first key
     @param exp1  the mapping associated to the first key
     @param key2  the second key
     @param exp2  the mapping associated to the second key
     @param key3  the third key
     @param exp3  the mapping associated to the third key
     @param key4  the fourth key
     @param exp4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param exp5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param exp6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param exp7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param exp8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param exp9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param exp10 the mapping associated to the tenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9,
                                   final String key10,
                                   final Exp<O> exp10
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8,
                                  key9,
                                  exp9
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key10),
                                        requireNonNull(exp10)
                                       );
        return obj;

    }


    /**
     static factory method to create a JsObjFuture of eleven mappings

     @param key1  the first key
     @param exp1  the mapping associated to the first key
     @param key2  the second key
     @param exp2  the mapping associated to the second key
     @param key3  the third key
     @param exp3  the mapping associated to the third key
     @param key4  the fourth key
     @param exp4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param exp5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param exp6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param exp7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param exp8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param exp9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param exp10 the mapping associated to the eleventh key
     @param key11 the tenth key
     @param exp11 the mapping associated to the eleventh key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9,
                                   final String key10,
                                   final Exp<O> exp10,
                                   final String key11,
                                   final Exp<O> exp11
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8,
                                  key9,
                                  exp9,
                                  key10,
                                  exp10
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key11),
                                        requireNonNull(exp11)
                                       );
        return obj;

    }


    /**
     static factory method to create a JsObjFuture of twelve mappings

     @param key1  the first key
     @param exp1  the mapping associated to the first key
     @param key2  the second key
     @param exp2  the mapping associated to the second key
     @param key3  the third key
     @param exp3  the mapping associated to the third key
     @param key4  the fourth key
     @param exp4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param exp5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param exp6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param exp7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param exp8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param exp9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param exp10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param exp11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param exp12 the mapping associated to the twelfth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9,
                                   final String key10,
                                   final Exp<O> exp10,
                                   final String key11,
                                   final Exp<O> exp11,
                                   final String key12,
                                   final Exp<O> exp12
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8,
                                  key9,
                                  exp9,
                                  key10,
                                  exp10,
                                  key11,
                                  exp11
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key12),
                                        requireNonNull(exp12)
                                       );
        return obj;

    }

    /**
     static factory method to create a JsObjFuture of thirteen mappings

     @param key1  the first key
     @param exp1  the mapping associated to the first key
     @param key2  the second key
     @param exp2  the mapping associated to the second key
     @param key3  the third key
     @param exp3  the mapping associated to the third key
     @param key4  the fourth key
     @param exp4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param exp5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param exp6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param exp7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param exp8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param exp9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param exp10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param exp11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param exp12 the mapping associated to the twelfth key,
     @param key13 the thirteenth key
     @param exp13 the mapping associated to the thirteenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9,
                                   final String key10,
                                   final Exp<O> exp10,
                                   final String key11,
                                   final Exp<O> exp11,
                                   final String key12,
                                   final Exp<O> exp12,
                                   final String key13,
                                   final Exp<O> exp13
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8,
                                  key9,
                                  exp9,
                                  key10,
                                  exp10,
                                  key11,
                                  exp11,
                                  key12,
                                  exp12
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key13),
                                        requireNonNull(exp13)
                                       );
        return obj;
    }


    /**
     static factory method to create a JsObjFuture of fourteen mappings

     @param key1  the first key
     @param exp1  the mapping associated to the first key
     @param key2  the second key
     @param exp2  the mapping associated to the second key
     @param key3  the third key
     @param exp3  the mapping associated to the third key
     @param key4  the fourth key
     @param exp4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param exp5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param exp6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param exp7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param exp8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param exp9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param exp10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param exp11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param exp12 the mapping associated to the twelfth key,
     @param key13 the thirteenth key
     @param exp13 the mapping associated to the thirteenth key
     @param key14 the fourteenth key
     @param exp14 the mapping associated to the fourteenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9,
                                   final String key10,
                                   final Exp<O> exp10,
                                   final String key11,
                                   final Exp<O> exp11,
                                   final String key12,
                                   final Exp<O> exp12,
                                   final String key13,
                                   final Exp<O> exp13,
                                   final String key14,
                                   final Exp<O> exp14
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8,
                                  key9,
                                  exp9,
                                  key10,
                                  exp10,
                                  key11,
                                  exp11,
                                  key12,
                                  exp12,
                                  key13,
                                  exp13
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key14),
                                        requireNonNull(exp14)
                                       );
        return obj;

    }


    /**
     static factory method to create a JsObjFuture of fifteen mappings

     @param key1  the first key
     @param exp1  the mapping associated to the first key
     @param key2  the second key
     @param exp2  the mapping associated to the second key
     @param key3  the third key
     @param exp3  the mapping associated to the third key
     @param key4  the fourth key
     @param exp4  the mapping associated to the fourth key
     @param key5  the fifth key
     @param exp5  the mapping associated to the fifth key
     @param key6  the sixth key
     @param exp6  the mapping associated to the sixth key
     @param key7  the seventh key
     @param exp7  the mapping associated to the seventh key
     @param key8  the eighth key
     @param exp8  the mapping associated to the eighth key
     @param key9  the ninth key
     @param exp9  the mapping associated to the ninth key
     @param key10 the tenth key
     @param exp10 the mapping associated to the eleventh key
     @param key11 the eleventh key
     @param exp11 the mapping associated to the eleventh key
     @param key12 the twelfth key
     @param exp12 the mapping associated to the twelfth key,
     @param key13 the thirteenth key
     @param exp13 the mapping associated to the thirteenth key
     @param key14 the fourteenth key
     @param exp14 the mapping associated to the fourteenth key
     @param key15 the fifteenth key
     @param exp15 the mapping associated to the fifteenth key
     @return a JsObjFuture
     */
    @SuppressWarnings("squid:S00107")
    public static <O> MapExp<O> of(final String key1,
                                   final Exp<O> exp1,
                                   final String key2,
                                   final Exp<O> exp2,
                                   final String key3,
                                   final Exp<O> exp3,
                                   final String key4,
                                   final Exp<O> exp4,
                                   final String key5,
                                   final Exp<O> exp5,
                                   final String key6,
                                   final Exp<O> exp6,
                                   final String key7,
                                   final Exp<O> exp7,
                                   final String key8,
                                   final Exp<O> exp8,
                                   final String key9,
                                   final Exp<O> exp9,
                                   final String key10,
                                   final Exp<O> exp10,
                                   final String key11,
                                   final Exp<O> exp11,
                                   final String key12,
                                   final Exp<O> exp12,
                                   final String key13,
                                   final Exp<O> exp13,
                                   final String key14,
                                   final Exp<O> exp14,
                                   final String key15,
                                   final Exp<O> exp15
                                  ) {
        MapExp<O> obj = MapExp.of(key1,
                                  exp1,
                                  key2,
                                  exp2,
                                  key3,
                                  exp3,
                                  key4,
                                  exp4,
                                  key5,
                                  exp5,
                                  key6,
                                  exp6,
                                  key7,
                                  exp7,
                                  key8,
                                  exp8,
                                  key9,
                                  exp9,
                                  key10,
                                  exp10,
                                  key11,
                                  exp11,
                                  key12,
                                  exp12,
                                  key13,
                                  exp13,
                                  key14,
                                  exp14
                                 );
        obj.bindings = obj.bindings.put(requireNonNull(key15),
                                        requireNonNull(exp15)
                                       );
        return obj;

    }


    /**
     returns a new object future inserting the given future at the given key

     @param key the given key
     @param exp the given future
     @return a new JsObjFuture
     */
    public MapExp<O> set(final String key,
                         final Exp<O> exp
                        ) {
        return new MapExp<>(bindings.put(requireNonNull(key),
                                         requireNonNull(exp)
                                        ));
    }

    public boolean isEmpty() {
        return bindings.isEmpty();
    }


    @Override
    public <P> Exp<P> map(final Function<Map<String, O>, P> fn) {
        return Val.success(() -> get().map(fn));
    }

    @Override
    public Map<String, O> result() {
        return bindings.map((k, v) -> new Tuple2<>(k,
                                                   v.get()
                                                    .result()
                            )
                           );
    }

    @Override
    public Exp<Map<String, O>> retry(final int attempts) {
        return new MapExp<>(bindings.mapValues(it -> it.retry(attempts)));
    }

    @Override
    public Exp<Map<String, O>> retryIf(final Predicate<Throwable> predicate,
                                       final int attempts) {
        return new MapExp<>(bindings.mapValues(it -> it.retryIf(predicate,
                                                                attempts
                                                               )));

    }

    @Override
    public Future<Map<String, O>> get() {


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
                                  Map<String, O> result = LinkedHashMap.empty();
                                  java.util.List<O> list = r.result()
                                                            .list();
                                  for (int i = 0; i < list.size(); i++) {
                                      result = result.put(new Tuple2<>(keys.get(i),
                                                                       list.get(i)
                                                          )
                                                         );

                                  }
                                  return result;
                              });


    }
}
