package actors;

import actors.future.JsArrayFuture;
import actors.future.JsObjFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsArray;
import jsonvalues.JsInt;
import jsonvalues.JsObj;
import jsonvalues.JsStr;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@ExtendWith(VertxExtension.class)
public class TestFutures extends ActorsModule{

  public static Function<Integer,Future<Integer>> multiplyBy10;
  public static Function<Integer,Future<Integer>> add10;
  public static Function<String, Future<String>> toUpper;


  @BeforeAll
  public static void prepare(final Vertx vertx,
                             final VertxTestContext testContext
                            )
  {

    final Future<String> future = vertx.deployVerticle(new TestFutures());
    future.onSuccess(it-> testContext.completeNow());


  }

  @Test
  public void testJsObjFuture(final Vertx vertx,
                              final VertxTestContext context){

    JsObjFuture future = JsObjFuture.of("a",
                                    () -> multiplyBy10.apply(10)
                                                      .map(JsInt::of),
                                    "b",
                                    () -> add10.apply(5)
                                               .map(JsInt::of),
                                    "c",
                                    () -> toUpper.apply("abc")
                                                 .map(JsStr::of),
                                    "d",
                                        JsArrayFuture.tuple(()->multiplyBy10.apply(1).map(JsInt::of),
                                                            ()->multiplyBy10.apply(5).map(JsInt::of))
                                   );

    future.get().onSuccess(o-> {
      if(o.equals(JsObj.of("a", JsInt.of(100),
                           "b", JsInt.of(15),
                           "c", JsStr.of("ABC"),
                           "d", JsArray.of(10,50)
                 ))
      ) context.completeNow();

    });
  }

  @Override
  protected void defineActors(final List<Object> list) {
    toUpper = ((ActorRef<String,String>) list.get(0)).ask();
    multiplyBy10 = ((ActorRef<Integer,Integer>) list.get(1)).ask();
    add10 = ((ActorRef<Integer,Integer>) list.get(2)).ask();

  }

  @Override
  protected List<Future> deployActors() {

    Function<String,String> keysToUpper = String::toUpperCase;
    Function<Integer,Function<Integer,Integer>> multiplyBy = i -> j -> i * j;
    Function<Integer,Function<Integer,Integer>> add = i -> j -> i + j;

    return Arrays.asList(actors.deploy(keysToUpper),
                         actors.deploy(multiplyBy.apply(10)),
                         actors.deploy(add.apply(10)));
  }
}
