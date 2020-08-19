package vertxval.exp;

import io.vavr.Tuple3;
import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestTriple {


    @Test
    public void testRetries(VertxTestContext context) {

        final Supplier<Val<String>> val =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter: "+counter),
                                           counter -> counter == 1 || counter == 2,
                                           "a"
                );

        Triple.of(val.get(),
                  val.get(),
                  val.get()
                 )
              .retry(2)
              .get()
              .onComplete(it -> {
                  context.verify(() -> it.result()
                                         .equals(new Tuple3<>("a",
                                                              "a",
                                                              "a"
                                                 )
                                                )
                                );
                  context.completeNow();
              });

    }

}
