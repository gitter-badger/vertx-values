package actors.bankaccount;

import actors.ActorRef;
import actors.Actors;
import actors.TestFns;
import actors.codecs.RegisterJsValuesCodecs;
import actors.exp.Exp;
import actors.exp.Pair;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static actors.TestFns.pipeTo;
import static actors.bankaccount.Account.creditLens;
import static actors.bankaccount.Account.nameLens;

@ExtendWith(VertxExtension.class)
public class TestBankAccount {

    private static Actors actors;
    private static BankAccountModule module;

    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              ) {

        actors = new Actors(vertx);

        module = new BankAccountModule();

        Pair.of(actors.register(new RegisterJsValuesCodecs()),
                actors.register(module)
               )
            .onComplete(pipeTo(testContext))
            .get();

    }


    @Test
    public void testCreateAndStopPersons(VertxTestContext context) {


        Exp<ActorRef<JsObj, Integer>> futRafaRef =
                module.registerAccount.apply(nameLens.set.apply("Rafa")
                                                         .andThen(creditLens.set.apply(10000))
                                                         .apply(JsObj.empty()));

        Exp<ActorRef<JsObj, Integer>> futPhilipRef =
                module.registerAccount.apply(nameLens.set.apply("Philip")
                                                         .andThen(creditLens.set.apply(1000))
                                                         .apply(JsObj.empty()));


        Pair.of(futRafaRef,
                futPhilipRef
               )
            .onComplete(pair -> {
                            ActorRef<JsObj, Integer> rafaRef   = pair._1;
                            ActorRef<JsObj, Integer> carmenRef = pair._2;
                            System.out.println("Rafa address=" + rafaRef.address + " and id=" + rafaRef.ids);
                            System.out.println("Philip address=" + carmenRef.address + " and id=" + carmenRef.ids);
                            rafaRef.unregister();
                            carmenRef.unregister();
                            context.completeNow();
                        },
                        context::failNow
                       )
            .get();
    }


    @Test
    public void testMakeTx(VertxTestContext context) {


        Exp<ActorRef<JsObj, Integer>> futRafaRef =
                module.registerAccount.apply(nameLens.set.apply("Rafa")
                                                         .andThen(creditLens.set.apply(10000))
                                                         .apply(JsObj.empty()));

        Exp<ActorRef<JsObj, Integer>> futPhilipRef =
                module.registerAccount.apply(nameLens.set.apply("Philip")
                                                         .andThen(creditLens.set.apply(1000))
                                                         .apply(JsObj.empty()));

        Pair.of(futRafaRef,
                futRafaRef
               )
            .flatMap(
                    pair ->
                            module.makeTx.apply(pair._1.ask(),
                                                pair._2.ask()
                                               )
                                         .apply(20)
                                         .onComplete(it -> {
                                             pair._1.unregister();
                                             pair._2.unregister();
                                             context.completeNow();
                                         })
                    )
            .get();
    }


}
