package vertxval;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsInt;
import jsonvalues.JsStr;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.exp.Exp;
import vertxval.exp.JsArrayExp;
import vertxval.exp.JsObjExp;
import java.util.function.Function;

@ExtendWith(VertxExtension.class)
public class TestFutures extends Module {

    public static Function<Integer, Exp<Integer>> multiplyBy10;
    public static Function<Integer, Exp<Integer>> add10;
    public static Function<String, Exp<String>> toUpper;


    @BeforeAll
    public static void prepare(final Vertx vertx,
                               final VertxTestContext testContext
                              ) {

        final Future<String> future = vertx.deployVerticle(new TestFutures());
        future.onSuccess(it -> testContext.completeNow());


    }

    @Test
    public void testJsObjFuture(final VertxTestContext context) {

        JsObjExp.of("a",
                    multiplyBy10.apply(10)
                                .map(JsInt::of),
                    "b",
                    add10.apply(5)
                         .map(JsInt::of),
                    "c",
                    toUpper.apply("abc")
                           .map(JsStr::of),
                    "d",
                    JsArrayExp.tuple(multiplyBy10.apply(1)
                                                 .map(JsInt::of),
                                     multiplyBy10.apply(5)
                                                 .map(JsInt::of)
                                    )
                   )
                .get()
                .onSuccess(o -> {
                    if (o.equals(jsonvalues.JsObj.of("a",
                                                     JsInt.of(100),
                                                     "b",
                                                     JsInt.of(15),
                                                     "c",
                                                     JsStr.of("ABC"),
                                                     "d",
                                                     jsonvalues.JsArray.of(10,
                                                                           50
                                                                          )
                                                    ))
                    ) context.completeNow();

                });
    }

    @Override
    protected void onComplete() {
        toUpper = this.<String, String>getDeployedVerticle("toUpper").ask();
        multiplyBy10 = this.<Integer, Integer>getDeployedVerticle("multiplyByTen").ask();
        add10 = this.<Integer, Integer>getDeployedVerticle("addTen").ask();

    }

    @Override
    protected void deploy() {
        Function<String, String>                      keysToUpper = String::toUpperCase;
        Function<Integer, Function<Integer, Integer>> multiplyBy  = i -> j -> i * j;
        Function<Integer, Function<Integer, Integer>> add         = i -> j -> i + j;
        this.deployFn("toUpper",
                      keysToUpper
                     );
        this.deployFn("multiplyByTen",
                      multiplyBy.apply(10)
                     );
        this.deployFn("addTen",
                      add.apply(10)
                     );

    }
}
