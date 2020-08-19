package vertxval.exp;

import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsArray;
import jsonvalues.JsObj;
import jsonvalues.JsStr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestJsObjVal {


    @Test
    public void testRetries(VertxTestContext context) {

        JsStr a = JsStr.of("a");

        final Supplier<Val<JsStr>> val =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter: " + counter),
                                           counter -> counter == 1 || counter == 2,
                                           a
                );


        JsObjVal.of("a",
                    val.get(),
                    "b",
                    val.get(),
                    "c",
                    val.get(),
                    "d",
                    JsArrayVal.tuple(val.get(),
                                     val.get(),
                                     JsObjVal.of("a",
                                                 val.get()
                                                )
                                    )
                   )
                .retry(2)
                .onComplete(it -> {
                    context.verify(() -> {
                                       Assertions.assertEquals(it.result(),
                                                               JsObj.of("a",
                                                                        a,
                                                                        "b",
                                                                        a,
                                                                        "c",
                                                                        a,
                                                                        "d",
                                                                        JsArray.of(a,
                                                                                   a,
                                                                                   JsObj.of("a",
                                                                                            a
                                                                                           )
                                                                                  )
                                                                       )
                                                              );
                                       context.completeNow();
                                   }
                                  );
                })
                .get();


    }

}
