package vertxval.exp;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestAnd {

    @Test
    public void testRetries(VertxTestContext context) {


        Supplier<Val<Boolean>> trueVal =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter:+" + counter),
                                           counter -> counter == 1 || counter == 2,
                                           true
                );

        Supplier<Val<Boolean>> falseVal =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter:+" + counter),
                                           counter -> counter == 1 || counter == 2,
                                           false
                );

        And.of(trueVal.get(),
               falseVal.get()
              )
           .retry(2)
           .get()
           .onComplete(it -> {
               context.verify(() -> Assertions.assertEquals(false,
                                                            it.result()
                                                           ));
               context.completeNow();
           });

        And.of(falseVal.get(),
               trueVal.get()
              )
           .retry(2)
           .get()
           .onComplete(it -> {
               context.verify(() -> Assertions.assertEquals(false,
                                                            it.result()
                                                           ));
               context.completeNow();
           });

        And.of(falseVal.get(),
               falseVal.get()
              )
           .retry(2)
           .get()
           .onComplete(it -> {
               context.verify(() -> Assertions.assertEquals(false,
                                                            it.result()
                                                           ));
               context.completeNow();
           });

        And.of(trueVal.get(),
               trueVal.get()
              )
           .retry(2)
           .get()
           .onComplete(it -> {
               context.verify(() -> Assertions.assertEquals(true,
                                                            it.result()
                                                           ));
               context.completeNow();
           });
    }
}
