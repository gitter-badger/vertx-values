package actors.expresions;

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.TreeMap;
import io.vertx.core.Future;
import jsonvalues.JsObj;
import jsonvalues.JsValue;

import java.util.function.Function;

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
public class JsObjExp implements Exp<JsObj> {

    private Map<String, Exp<? extends JsValue>> bindings = TreeMap.empty();

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key14,
                                fut14
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key13,
                                fut13
                               );
    }


    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key12,
                                fut12
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key11,
                                fut11
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key10,
                                fut10
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key9,
                                fut9
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key8,
                                fut8
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key7,
                                fut7
                               );
    }


    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
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
        bindings = bindings.put(key6,
                                fut6
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
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
        this(key,
             fut,
             key1,
             fut1,
             key2,
             fut2,
             key3,
             fut3,
             key4,
             fut4
            );
        bindings = bindings.put(key5,
                                fut5
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
                     final Exp<? extends JsValue> fut1,
                     final String key2,
                     final Exp<? extends JsValue> fut2,
                     final String key3,
                     final Exp<? extends JsValue> fut3,
                     final String key4,
                     final Exp<? extends JsValue> fut4
                    ) {
        this(key,
             fut,
             key1,
             fut1,
             key2,
             fut2,
             key3,
             fut3
            );
        bindings = bindings.put(key4,
                                fut4
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
                     final Exp<? extends JsValue> fut1,
                     final String key2,
                     final Exp<? extends JsValue> fut2,
                     final String key3,
                     final Exp<? extends JsValue> fut3
                    ) {
        this(key,
             fut,
             key1,
             fut1,
             key2,
             fut2
            );
        bindings = bindings.put(key3,
                                fut3
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
                     final Exp<? extends JsValue> fut1,
                     final String key2,
                     final Exp<? extends JsValue> fut2
                    ) {
        this(key,
             fut,
             key1,
             fut1
            );
        bindings = bindings.put(key2,
                                fut2
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut,
                     final String key1,
                     final Exp<? extends JsValue> fut1
                    ) {
        this(key,
             fut
            );
        bindings = bindings.put(key1,
                                fut1
                               );
    }

    private JsObjExp(final String key,
                     final Exp<? extends JsValue> fut
                    ) {
        bindings = bindings.put(key,
                                fut
                               );

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
        return new JsObjExp(requireNonNull(key),
                            requireNonNull(fut)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7)
        );
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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8)
        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9)
        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9),
                            requireNonNull(key10),
                            requireNonNull(fut10)
        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9),
                            requireNonNull(key10),
                            requireNonNull(fut10),
                            requireNonNull(key11),
                            requireNonNull(fut11)
        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9),
                            requireNonNull(key10),
                            requireNonNull(fut10),
                            requireNonNull(key11),
                            requireNonNull(fut11),
                            requireNonNull(key12),
                            requireNonNull(fut12)
        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9),
                            requireNonNull(key10),
                            requireNonNull(fut10),
                            requireNonNull(key11),
                            requireNonNull(fut11),
                            requireNonNull(key12),
                            requireNonNull(fut12),
                            requireNonNull(key13),
                            requireNonNull(fut13)

        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9),
                            requireNonNull(key10),
                            requireNonNull(fut10),
                            requireNonNull(key11),
                            requireNonNull(fut11),
                            requireNonNull(key12),
                            requireNonNull(fut12),
                            requireNonNull(key13),
                            requireNonNull(fut13),
                            requireNonNull(key14),
                            requireNonNull(fut14)

        );

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
        return new JsObjExp(requireNonNull(key1),
                            requireNonNull(fut1),
                            requireNonNull(key2),
                            requireNonNull(fut2),
                            requireNonNull(key3),
                            requireNonNull(fut3),
                            requireNonNull(key4),
                            requireNonNull(fut4),
                            requireNonNull(key5),
                            requireNonNull(fut5),
                            requireNonNull(key6),
                            requireNonNull(fut6),
                            requireNonNull(key7),
                            requireNonNull(fut7),
                            requireNonNull(key8),
                            requireNonNull(fut8),
                            requireNonNull(key9),
                            requireNonNull(fut9),
                            requireNonNull(key10),
                            requireNonNull(fut10),
                            requireNonNull(key11),
                            requireNonNull(fut11),
                            requireNonNull(key12),
                            requireNonNull(fut12),
                            requireNonNull(key13),
                            requireNonNull(fut13),
                            requireNonNull(key14),
                            requireNonNull(fut14),
                            requireNonNull(key15),
                            requireNonNull(fut15)

        );

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
    public Future<JsObj> get() {

        Future<JsObj> result = Future.succeededFuture(JsObj.empty());

        for (final Tuple2<String, Exp<? extends JsValue>> tuple : bindings.iterator()) {
            result = result.flatMap(obj -> tuple._2.get()
                                                   .map(v -> obj.set(tuple._1,
                                                                     v)));
        }


        return result;
    }

    @Override
    public <P> Exp<P> map(final Function<JsObj, P> fn) {
        return Val.of(() -> get().map(fn));
    }
}
