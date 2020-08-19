package vertxval.exp;

import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestOr {

    @Test
    public void testRetries(VertxTestContext context) {

        Supplier<Val<Boolean>> trueVal = ()->Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<Boolean> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                i = 0;
                return Future.succeededFuture(true);
            }
        });

        Supplier<Val<Boolean>> falseVal = ()->Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<Boolean> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns false");
                i = 0;
                return Future.succeededFuture(false);
            }
        });
        Or.of(trueVal.get(),
              falseVal.get())
          .retry(2)
          .get()
          .onComplete(it -> {
              context.verify(() -> Assertions.assertEquals(true,
                                                           it.result()));
              context.completeNow();
          });

        Or.of(falseVal.get(),
              falseVal.get())
          .retry(2)
          .get()
          .onComplete(it -> {
              context.verify(() -> Assertions.assertEquals(false,
                                                           it.result()));
              context.completeNow();
          });

        Or.of(trueVal.get(),
              trueVal.get())
          .retry(2)
          .get()
          .onComplete(it -> {
              context.verify(() -> Assertions.assertEquals(true,
                                                           it.result()));
              context.completeNow();
          });
    }
}
