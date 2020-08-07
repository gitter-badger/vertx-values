package vertxval.bankaccount;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.Deployer;
import vertxval.TestFns;
import vertxval.VerticleRef;
import vertxval.codecs.RegisterJsValuesCodecs;
import vertxval.exp.Pair;
import vertxval.exp.Val;

import static vertxval.TestFns.pipeTo;
import static vertxval.bankaccount.Account.creditLens;
import static vertxval.bankaccount.Account.nameLens;

@ExtendWith(VertxExtension.class)
public class TestBankAccount {

    private static Deployer deployer;
    private static BankAccountModule module;

    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              ) {

        deployer = new Deployer(vertx);

        module = new BankAccountModule();

        Pair.of(deployer.deployVerticle(new RegisterJsValuesCodecs()),
                deployer.deployVerticle(module)
               )
            .onComplete(pipeTo(testContext))
            .get();

    }


    @Test
    public void testCreateAndStopPersons(VertxTestContext context) {


        Val<VerticleRef<JsObj, Integer>> futRafaRef =
                module.registerAccount.apply(nameLens.set.apply("Rafa")
                                                         .andThen(creditLens.set.apply(10000))
                                                         .apply(JsObj.empty())
                                            );

        Val<VerticleRef<JsObj, Integer>> futPhilipRef =
                module.registerAccount.apply(nameLens.set.apply("Philip")
                                                         .andThen(creditLens.set.apply(1000))
                                                         .apply(JsObj.empty())
                                            );


        Pair.of(futRafaRef,
                futPhilipRef
               )
            .onComplete(pair -> {
                            VerticleRef<JsObj, Integer> rafaRef   = pair._1;
                            VerticleRef<JsObj, Integer> carmenRef = pair._2;
                            System.out.println("Rafa address=" + rafaRef.address + " and id=" + rafaRef.ids);
                            System.out.println("Philip address=" + carmenRef.address + " and id=" + carmenRef.ids);
                            rafaRef.undeploy();
                            carmenRef.undeploy();
                            context.completeNow();
                        },
                        context::failNow
                       )
            .get();
    }


    @Test
    public void testMakeTx(VertxTestContext context) {


        Val<VerticleRef<JsObj, Integer>> futRafaRef =
                module.registerAccount.apply(nameLens.set.apply("Rafa")
                                                         .andThen(creditLens.set.apply(10000))
                                                         .apply(JsObj.empty())
                                            );

        Val<VerticleRef<JsObj, Integer>> futPhilipRef =
                module.registerAccount.apply(nameLens.set.apply("Philip")
                                                         .andThen(creditLens.set.apply(1000))
                                                         .apply(JsObj.empty())
                                            );

        Pair.of(futRafaRef,
                futPhilipRef
               )
            .flatMap(
                    pair ->
                            module.makeTx.apply(pair._1.ask(),
                                                pair._2.ask()
                                               )
                                         .apply(20)
                                         .onComplete(it -> {
                                             pair._1.undeploy();
                                             pair._2.undeploy();
                                         })


                    )
            .onComplete(TestFns.pipeTo(context))
            .get();
    }


}
