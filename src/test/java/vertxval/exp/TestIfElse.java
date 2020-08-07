package vertxval.exp;

import io.vertx.core.Future;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsBool;
import jsonvalues.JsInt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.VertxValException;

import java.util.function.Supplier;

import static vertxval.VertxValException.BAD_MESSAGE_CODE;
import static vertxval.VertxValException.GET_BAD_MESSAGE_EXCEPTION;

@ExtendWith(VertxExtension.class)
public class TestIfElse {


    @Test
    public void testRetryIfElsePredicate(final VertxTestContext context) {

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


        IfElse.predicate(predicate)
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

        Val<Boolean> predicate = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<Boolean> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture(GET_BAD_MESSAGE_EXCEPTION.apply("bad message"));
                }
                System.out.println("returns true");
                return Future.succeededFuture(true);
            }
        });


        IfElse.predicate(predicate)
              .consequence(Cons.success("consequence"))
              .alternative(Cons.success("alternative"))
              .retryIf(VertxValException.prism.exists.apply(v->v.failureCode()==BAD_MESSAGE_CODE),
                       2)
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

        Val<String> consequence = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<String> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture(GET_BAD_MESSAGE_EXCEPTION.apply("bad message"));
                }
                System.out.println("returns consequence");
                return Future.succeededFuture("consequence");
            }
        });


        IfElse.<String>predicate(Cons.success(true))
                .consequence(consequence)
                .alternative(Cons.success("alternative"))
                .retryIf(VertxValException.prism.exists.apply(v->v.failureCode()==BAD_MESSAGE_CODE),
                         2)
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

        Val<String> alterantive = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<String> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture(GET_BAD_MESSAGE_EXCEPTION.apply("bad message"));
                }
                System.out.println("returns alternative");
                return Future.succeededFuture("alternative");
            }
        });


        IfElse.<String>predicate(Cons.success(false))
                .consequence(Cons.success("consequence"))
                .alternative(alterantive)
                .retryIf(VertxValException.prism.exists.apply(v->v.failureCode()==BAD_MESSAGE_CODE),
                         2)
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

        Val<String> consequence = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<String> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                return Future.succeededFuture("consequence");
            }
        });


        IfElse.<String>predicate(Cons.success(true))
                .consequence(consequence)
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

        Val<String> alternative = Cons.of(new Supplier<>() {
            int i = 0;

            @Override
            public Future<String> get() {
                if (i == 0 || i == 1) {
                    i += 1;
                    System.out.println("throw an exception");
                    return Future.failedFuture("i=" + i);
                }
                System.out.println("returns true");
                return Future.succeededFuture("alternative");
            }
        });


        IfElse.<String>predicate(Cons.success(false))
                .consequence(Cons.success("consequence"))
                .alternative(alternative)
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
