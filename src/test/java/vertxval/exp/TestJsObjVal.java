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
        Val<JsStr> val = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<JsStr> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                i = 0;
                return Future.succeededFuture(a);
            }
        });

        JsObjVal.of("a",
                    val,
                    "b",
                    val,
                    "c",
                    val,
                    "d",
                    JsArrayVal.tuple(val,
                                     val,
                                     JsObjVal.of("a",
                                                 val
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
