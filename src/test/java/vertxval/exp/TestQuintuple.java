package vertxval.exp;

import io.vavr.Tuple5;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestQuintuple {


    @Test
    public void testRetries(VertxTestContext context) {

        final Supplier<Val<String>> val =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter: " + counter),
                                           counter -> counter == 1 || counter == 2,
                                           "a"
                );

        Quintuple.of(val.get(),
                     val.get(),
                     val.get(),
                     val.get(),
                     val.get()
                    )
                 .retry(2)
                 .get()
                 .onComplete(it -> {
                     context.verify(() -> it.result()
                                            .equals(new Tuple5<>("a",
                                                                 "a",
                                                                 "a",
                                                                 "a",
                                                                 "a"
                                                    )
                                                   )
                                   );
                     context.completeNow();
                 });

    }
}
