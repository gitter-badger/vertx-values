package actors.exp;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.TreeMap;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import jsonvalues.JsValue;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
public final class JsObjExp extends AbstractExp<JsObj> {

    private Map<String, Exp<? extends JsValue>> bindings = TreeMap.empty();

    private JsObjExp() {
    }

    private JsObjExp(final Map<String, Exp<? extends JsValue>> bindings) {
        this.bindings = bindings;
    }

    /**
     static factory method to create a JsObjFuture of one mapping

     @param key the key
     @param fut the mapping associated to the key
     @return a JsObjFuture
     */
    public static JsObjExp of(final String key,
                              final Exp<? extends JsValue> fut
                             ) {
        JsObjExp obj = new JsObjExp();
        obj.bindings = obj.bindings.put(key,
                                        fut
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9,
                              final String key10,
                              final Exp<? extends JsValue> fut10
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9,
                              final String key10,
                              final Exp<? extends JsValue> fut10,
                              final String key11,
                              final Exp<? extends JsValue> fut11
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9,
                              final String key10,
                              final Exp<? extends JsValue> fut10,
                              final String key11,
                              final Exp<? extends JsValue> fut11,
                              final String key12,
                              final Exp<? extends JsValue> fut12
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9,
                              final String key10,
                              final Exp<? extends JsValue> fut10,
                              final String key11,
                              final Exp<? extends JsValue> fut11,
                              final String key12,
                              final Exp<? extends JsValue> fut12,
                              final String key13,
                              final Exp<? extends JsValue> fut13
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9,
                              final String key10,
                              final Exp<? extends JsValue> fut10,
                              final String key11,
                              final Exp<? extends JsValue> fut11,
                              final String key12,
                              final Exp<? extends JsValue> fut12,
                              final String key13,
                              final Exp<? extends JsValue> fut13,
                              final String key14,
                              final Exp<? extends JsValue> fut14
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public static JsObjExp of(final String key1,
                              final Exp<? extends JsValue> fut1,
                              final String key2,
                              final Exp<? extends JsValue> fut2,
                              final String key3,
                              final Exp<? extends JsValue> fut3,
                              final String key4,
                              final Exp<? extends JsValue> fut4,
                              final String key5,
                              final Exp<? extends JsValue> fut5,
                              final String key6,
                              final Exp<? extends JsValue> fut6,
                              final String key7,
                              final Exp<? extends JsValue> fut7,
                              final String key8,
                              final Exp<? extends JsValue> fut8,
                              final String key9,
                              final Exp<? extends JsValue> fut9,
                              final String key10,
                              final Exp<? extends JsValue> fut10,
                              final String key11,
                              final Exp<? extends JsValue> fut11,
                              final String key12,
                              final Exp<? extends JsValue> fut12,
                              final String key13,
                              final Exp<? extends JsValue> fut13,
                              final String key14,
                              final Exp<? extends JsValue> fut14,
                              final String key15,
                              final Exp<? extends JsValue> fut15
                             ) {
        JsObjExp obj = JsObjExp.of(key1,
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
    public JsObjExp set(final String key,
                        final Exp<? extends JsValue> future
                       ) {
        final Map<String, Exp<? extends JsValue>> a = bindings.put(requireNonNull(key),
                                                                   requireNonNull(future)
                                                                  );
        return new JsObjExp(a);
    }


    /**
     it triggers the execution of all the completable futures, combining the results into a JsObj

     @return a Future of a json object
     */
    @Override
    public Future<jsonvalues.JsObj> get() {

        List<String> keys = bindings.keysIterator()
                                    .toList();

        java.util.List futures = bindings.values()
                                          .map(Supplier::get)
                                          .toJavaList();
        return CompositeFuture.all(futures)
                              .map(r -> {
                                  JsObj result = JsObj.empty();
                                  java.util.List<?> list = r.result()
                                                         .list();
                                  for (int i = 0; i < list.size(); i++) {
                                      result = result.set(keys.get(i),
                                                          ((JsValue) list.get(i))
                                                         );
                                  }

                                  return result;

                              });



       /* Future<jsonvalues.JsObj> result = Future.succeededFuture(jsonvalues.JsObj.empty());

        for (final Tuple2<String, Exp<? extends JsValue>> tuple : bindings.iterator()) {
            result = result.flatMap(obj -> tuple._2.get()
                                                   .map(v -> obj.set(tuple._1,
                                                                     v
                                                                    )));
        }


        return result;*/
    }


    @Override
    public <P> Exp<P> map(final Function<JsObj, P> fn) {
        return Val.of(() -> get().map(fn));
    }

    @Override
    public jsonvalues.JsObj result() {
        jsonvalues.JsObj result = jsonvalues.JsObj.empty();
        for (final Tuple2<String, Exp<? extends JsValue>> tuple : bindings.iterator()) {
            result = result.set(tuple._1,
                                tuple._2.get()
                                        .result()
                               );
        }
        return result;
    }

    @Override
    public Exp<JsObj> retry(final int attempts) {
        return new JsObjExp(bindings.mapValues(it -> it.retry(attempts)));
    }

    @Override
    public Exp<JsObj> retryIf(final Predicate<Throwable> predicate,
                              final int attempts) {
        return new JsObjExp(bindings.mapValues(it -> it.retryIf(predicate,
                                                                attempts
                                                               )));

    }


}
