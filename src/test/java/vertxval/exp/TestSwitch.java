package vertxval.exp;

import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class TestSwitch {
    @Test
    public void testTwoBranchesReturnsTheFirst(VertxTestContext context) {

        Switch.of(Cons.success(true),
                  Cons.success("hi"),
                  Cons.success(false),
                  Cons.success("bye")
                 )
              .onSuccess(it -> context.verify(() -> {
                  Assertions.assertEquals("hi",
                                          it
                                         );
                  context.completeNow();
              }))
              .get();

    }

    @Test
    public void testTwoBranchesReturnsTheFirstWithRetries(VertxTestContext context) {
        Val<Boolean> predicate = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<Boolean> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                return Future.succeededFuture(true);
            }
        });
        Switch.of(predicate,
                  Cons.success("hi"),
                  Cons.success(false),
                  Cons.success("bye")
                 )
              .retry(2)
              .onSuccess(it -> context.verify(() -> {
                  Assertions.assertEquals("hi",
                                          it
                                         );
                  context.completeNow();
              }))
              .get();

    }

    @Test
    public void testTwoBranchesReturnsTheSecond(VertxTestContext context) {

        Switch.of(Cons.success(false),
                  Cons.success("hi"),
                  Cons.success(true),
                  Cons.success("bye")
                 )
              .onSuccess(it -> context.verify(() -> {
                  Assertions.assertEquals("bye",
                                          it
                                         );
                  context.completeNow();
              }))
              .get();

    }


    @Test
    public void testTwoBranchesReturnsTheSecondWithRetries(VertxTestContext context) {
        Val<Boolean> predicateFalse = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<Boolean> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns false");
                return Future.succeededFuture(false);
            }
        });
        Val<Boolean> predicateTrue = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<Boolean> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                return Future.succeededFuture(true);
            }
        });
        Switch.of(predicateFalse,
                  Cons.success("hi"),
                  predicateTrue,
                  Cons.success("bye")
                 )
              .retry(2)
              .onSuccess(it -> context.verify(() -> {
                  Assertions.assertEquals("bye",
                                          it
                                         );
                  context.completeNow();
              }))
              .get();

    }
}
