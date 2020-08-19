package vertxval.exp;

import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.VertxValException;

import java.util.function.Supplier;

import static vertxval.VertxValException.BAD_MESSAGE_CODE;
import static vertxval.VertxValException.GET_BAD_MESSAGE_EXCEPTION;

@ExtendWith(VertxExtension.class)
public class TestIfElse {
    final Supplier<Val<Boolean>> trueVal =
            new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter:+" + counter),
                                       counter -> counter == 1 || counter == 2,
                                       true
            );

    @Test
    public void testRetryIfElsePredicate(final VertxTestContext context) {
        IfElse.predicate(trueVal.get())
              .consequence(Cons.success("consequence"))
              .alternative(Cons.success("alternative"))
              .retry(2)
              .onComplete(r -> {
                  if (r.succeeded()) {
                      System.out.println(r.result());
                      context.verify(() -> {
                          Assertions.assertEquals("consequence",
                                                  r.result()
                                                 );
                          context.completeNow();

                      });
                  }
              })
              .get();
    }

    @Test
    public void testRetryPredicateWhenBadMessage(final VertxTestContext context) {
        final Supplier<Val<Boolean>> trueVal =
                new ReturnsConsOrFailure<>(counter -> GET_BAD_MESSAGE_EXCEPTION.apply("bad message"),
                                           counter -> counter == 1 || counter == 2,
                                           true
                );
        IfElse.predicate(trueVal.get())
              .consequence(Cons.success("consequence"))
              .alternative(Cons.success("alternative"))
              .retryIf(VertxValException.prism.exists.apply(v -> v.failureCode() == BAD_MESSAGE_CODE),
                       2
                      )
              .onComplete(r -> {
                  if (r.succeeded()) {
                      System.out.println(r.result());
                      context.verify(() -> {
                          Assertions.assertEquals("consequence",
                                                  r.result()
                                                 );
                          context.completeNow();

                      });
                  }
              })
              .get();
    }

    @Test
    public void testRetryIfIfElseConsequence(final VertxTestContext context) {
        final Supplier<Val<String>> consequence =
                new ReturnsConsOrFailure<>(counter -> GET_BAD_MESSAGE_EXCEPTION.apply("bad message"),
                                           counter -> counter == 1 || counter == 2,
                                           "consequence"
                );


        IfElse.<String>predicate(Cons.success(true))
                .consequence(consequence.get())
                .alternative(Cons.success("alternative"))
                .retryIf(VertxValException.prism.exists.apply(v -> v.failureCode() == BAD_MESSAGE_CODE),
                         2
                        )
                .onComplete(r -> {
                    if (r.succeeded()) {
                        System.out.println(r.result());
                        context.verify(() -> {
                            Assertions.assertEquals("consequence",
                                                    r.result()
                                                   );
                            context.completeNow();

                        });
                    }
                })
                .get();
    }

    @Test
    public void testRetryIfIfElseAlternative(final VertxTestContext context) {

        final Supplier<Val<String>> alternative =
                new ReturnsConsOrFailure<>(counter -> GET_BAD_MESSAGE_EXCEPTION.apply("bad message"),
                                           counter -> counter == 1 || counter == 2,
                                           "alternative"
                );


        IfElse.<String>predicate(Cons.success(false))
                .consequence(Cons.success("consequence"))
                .alternative(alternative.get())
                .retryIf(VertxValException.prism.exists.apply(v -> v.failureCode() == BAD_MESSAGE_CODE),
                         2
                        )
                .onComplete(r -> {
                    if (r.succeeded()) {
                        System.out.println(r.result());
                        context.verify(() -> {
                            Assertions.assertEquals("alternative",
                                                    r.result()
                                                   );
                            context.completeNow();

                        });
                    }
                })
                .get();
    }


    @Test
    public void testRetryIfElseConsequence(final VertxTestContext context) {
        final Supplier<Val<String>> consequence =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter: " + counter),
                                           counter -> counter == 1 || counter == 2,
                                           "consequence"
                );


        IfElse.<String>predicate(Cons.success(true))
                .consequence(consequence.get())
                .alternative(Cons.success("alternative"))
                .retry(2)
                .onComplete(r -> {
                    if (r.succeeded()) {
                        System.out.println(r.result());
                        context.verify(() -> {
                            Assertions.assertEquals("consequence",
                                                    r.result()
                                                   );
                            context.completeNow();

                        });
                    }
                })
                .get();
    }

    @Test
    public void testRetryIfElseAlternative(final VertxTestContext context) {

        final Supplier<Val<String>> alternative =
                new ReturnsConsOrFailure<>(counter -> new RuntimeException("counter: " + counter),
                                           counter -> counter == 1 || counter == 2,
                                           "alternative"
                );


        IfElse.<String>predicate(Cons.success(false))
                .consequence(Cons.success("consequence"))
                .alternative(alternative.get())
                .retry(2)
                .onComplete(r -> {
                    if (r.succeeded()) {
                        System.out.println(r.result());
                        context.verify(() -> {
                            Assertions.assertEquals("alternative",
                                                    r.result()
                                                   );
                            context.completeNow();

                        });
                    }
                })
                .get();
    }

}
