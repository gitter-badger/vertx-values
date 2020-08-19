package vertxval.exp;

import io.vavr.Tuple2;
import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

import static vertxval.VertxValException.GET_BAD_MESSAGE_EXCEPTION;

@ExtendWith(VertxExtension.class)
public class TestPair {


    @Test
    public void testRetries(VertxTestContext context) {

        final Supplier<Val<String>> val =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter: "+counter),
                                           counter -> counter == 1 || counter == 2,
                                           "a"
                );

        Pair.of(val.get(),
                val.get()
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
