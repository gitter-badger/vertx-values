package vertxval;

import vertxval.codecs.RegisterJsValuesCodecs;
import vertxval.exp.Exp;
import vertxval.exp.Pair;
import vertxval.exp.λ;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(VertxExtension.class)
public class ExampleModuleTest {

    static Deployer deployer;

    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              ) {
        deployer = new Deployer(vertx);
        vertx.deployVerticle(new RegisterJsValuesCodecs())
             .onComplete(TestFns.pipeTo(testContext));

    }


    @Test
    public void test_number_of_instances(VertxTestContext context) {
        int i = 100000;

        final Checkpoint               checkpoint = context.checkpoint(i);
        final Consumer<Message<JsObj>> consumer   = m -> m.reply(m.body());

        for (int i1 = 0; i1 < i; i1++) {
            deployer.deployConsumer(consumer,
                                     new DeploymentOptions().setWorker(true)
                            )
                    .onComplete(r -> checkpoint.flag()).get();
        }
    }

    @Test
    public void test_sending_messages(VertxTestContext context) {

        Exp<VerticleRef<Integer, Integer>> addOneExp = deployer.deployFn(i -> i + 1);
        Exp<VerticleRef<Integer, Integer>> tripleExp = deployer.deployFn(i -> i + 3);

        Pair.of(addOneExp,
                tripleExp
               )
            .onSuccess(pair -> {
                λ<Integer, Integer> addOne = pair._1.ask();
                λ<Integer, Integer> triple = pair._2.ask();
                addOne.apply(1)
                      .flatMap(triple::apply)
                      .onSuccess(result -> {
                          context.verify(() -> Assertions.assertEquals(5,
                                                                       result));
                          context.completeNow();
                          pair._1.undeploy();
                          pair._2.undeploy();
                      }).get();
            }).get();


    }

    @Test
    public void test_verticle_consumer(VertxTestContext context) {
        final Consumer<Message<JsObj>> consumer = m -> m.reply(m.body());
        deployer.deployConsumer(consumer)
                .onComplete(r -> r.result()
                                .ask()
                                .apply(JsObj.empty())
                                .onComplete(h -> context.verify(() ->
                                                                {
                                                                    assertEquals(h.result(),
                                                                                 JsObj.empty()
                                                                                );
                                                                    context.completeNow();
                                                                }
                                                               )
                                           ).get()
                         ).get();
    }

    @Test
    @DisplayName("")
    public void test_verticle_deployment(Vertx vertx,
                                         VertxTestContext context
                                        ) {
        final Consumer<Message<JsObj>> messageConsumer = m -> m.reply(m.body());
        deployer.deployConsumer(messageConsumer)
                .onComplete(r ->
                          {
                              context.verify(() -> assertTrue(r.succeeded()));
                              context.completeNow();
                          }
                         ).get();

    }


}
