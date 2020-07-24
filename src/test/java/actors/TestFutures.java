package actors;

import actors.exp.JsArrayExp;
import actors.exp.JsObjExp;
import actors.exp.Val;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsInt;
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

    JsObjExp future = JsObjExp.of("a",
                                  Val.of(() -> multiplyBy10.apply(10)
                                                        .map(JsInt::of)),
                                  "b",
                                  Val.of(() -> add10.apply(5)
                                               .map(JsInt::of)),
                                  "c",
                                  Val.of(() -> toUpper.apply("abc")
                                                 .map(JsStr::of)),
                                  "d",
                                  JsArrayExp.tuple(Val.of(() -> multiplyBy10.apply(1)
                                                                     .map(JsInt::of)),
                                                          Val.of(() -> multiplyBy10.apply(5)
                                                                     .map(JsInt::of))
                                                  )
                                 );

    future.get().onSuccess(o-> {
      if(o.equals(jsonvalues.JsObj.of("a", JsInt.of(100),
                                      "b", JsInt.of(15),
                                      "c", JsStr.of("ABC"),
                                      "d", jsonvalues.JsArray.of(10, 50)
                                     ))
      ) context.completeNow();

    });
  }

  @Override
  protected void defineActors(final List<Object> list) {
    toUpper = ((VerticleRef<String,String>) list.get(0)).ask();
    multiplyBy10 = ((VerticleRef<Integer,Integer>) list.get(1)).ask();
    add10 = ((VerticleRef<Integer,Integer>) list.get(2)).ask();

  }

  @Override
  protected List<Future> registerActors() {

    Function<String,String> keysToUpper = String::toUpperCase;
    Function<Integer,Function<Integer,Integer>> multiplyBy = i -> j -> i * j;
    Function<Integer,Function<Integer,Integer>> add = i -> j -> i + j;

    return Arrays.asList(actors.register(keysToUpper),
                         actors.register(multiplyBy.apply(10)),
                         actors.register(add.apply(10)));
  }
}
