package actors.codecs;

import actors.TestProperty;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import jsonvalues.JsArray;
import jsonvalues.JsObj;
import jsonvalues.JsValue;
import jsonvalues.gen.JsGens;
import jsonvalues.gen.JsObjGen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

public class TestCodecs {

    @Test
    public void testJsObjCodec() {

        JsObjGen gen = JsObjGen.of("a",
                                   JsGens.alphabetic.nullable()
                                                    .optional(),
                                   "b",
                                   JsGens.longInteger.nullable(),
                                   "c",
                                   JsGens.integer.nullable()
                                                 .optional(),
                                   "d",
                                   JsGens.bool.nullable(),
                                   "e",
                                   JsGens.dateBetween(0,
                                                      10_000
                                                     )
                                         .nullable()
                                         .optional(),
                                   "f",
                                   JsGens.array(JsGens.integer,
                                                10)
                                         .nullable(),
                                   "g",
                                   JsObjGen.of("a",
                                               JsGens.oneOf(JsObj.empty()))
                                           .nullable()
                                  );


        Predicate<JsValue> objEncondingProperty = o -> {
            Buffer buffer = Buffer.buffer();

            JsObjMessageCodec.INSTANCE.encodeToWire(buffer,
                                                    o.toJsObj()
                                                   );

            JsObj obj = JsObjMessageCodec.INSTANCE.decodeFromWire(0,
                                                                  buffer
                                                                 );


            return o.equals(obj);
        };

        Predicate<JsValue> arrEncondingProperty = o -> {
            Buffer buffer = Buffer.buffer();

            JsArrayMessageCodec.INSTANCE.encodeToWire(buffer,
                                                      o.toJsArray()
                                                     );

            JsArray obj = JsArrayMessageCodec.INSTANCE.decodeFromWire(0,
                                                                      buffer
                                                                     );


            return o.equals(obj);
        };

        TestProperty.test(gen,
                          objEncondingProperty,
                          100);
        TestProperty.test(JsGens.array(gen,
                                       10),
                          arrEncondingProperty,
                          100);
    }
}



