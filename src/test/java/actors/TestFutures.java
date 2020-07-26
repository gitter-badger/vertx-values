package actors;

import actors.exp.Exp;
import actors.exp.JsArrayExp;
import actors.exp.JsObjExp;
import actors.exp.MapExp;
import io.vavr.collection.Map;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsInt;
import jsonvalues.JsStr;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Function;

@ExtendWith(VertxExtension.class)
public class TestFutures extends ActorsModule {

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
    public void testJsObjFuture(final Vertx vertx,
                                final VertxTestContext context) {

        JsObjExp future = JsObjExp.of("a",
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
                                     );

        future.get()
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
    protected void onComplete(final Map<String, ActorRef<?,?>> map) {
        toUpper = this.<String,String>getActorRef("toUpper").ask();
        multiplyBy10 = this.<Integer,Integer>getActorRef("multiplyByTen").ask();
        add10 = this.<Integer,Integer>getActorRef("addTen").ask();

    }

    @Override
    protected MapExp defineActors() {
        Function<String, String>                      keysToUpper = String::toUpperCase;
        Function<Integer, Function<Integer, Integer>> multiplyBy  = i -> j -> i * j;
        Function<Integer, Function<Integer, Integer>> add         = i -> j -> i + j;
        return MapExp.of("toUpper",
                         actors.register(keysToUpper),
                         "multiplyByTen",
                         actors.register(multiplyBy.apply(10)),
                         "addTen",
                         actors.register(add.apply(10))
                        );

    }
}
