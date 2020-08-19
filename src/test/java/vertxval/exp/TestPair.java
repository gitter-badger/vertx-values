package vertxval.exp;

import io.vavr.Tuple2;
import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestPair {


    @Test
    public void testRetries(VertxTestContext context) {

        Val<String> val = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<String> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                i = 0;
                return Future.succeededFuture("a");
            }
        });
        Pair.of(val,
                val
               )
            .retry(2)
            .get()
            .onComplete(it -> {
                context.verify(() -> Assertions.assertEquals(new Tuple2<>("a",
                                                                          "a"
                                                             ),
                                                             it.result()
                                                            )
                              );
                context.completeNow();
            });

    }

}
