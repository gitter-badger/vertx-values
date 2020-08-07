package vertxval;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsInt;
import jsonvalues.JsObj;
import jsonvalues.JsStr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.codecs.RegisterJsValuesCodecs;
import vertxval.exp.Pair;

@ExtendWith(VertxExtension.class)
public class UserAccountModuleTests {


    @BeforeAll
    public static void prepare(final Vertx vertx,
                               final VertxTestContext testContext
                              ) {

        Deployer deployer = new Deployer(vertx);
        Pair.of(deployer.deployVerticle(new UserAccountModule()),
                deployer.deployVerticle(new RegisterJsValuesCodecs())
               )
            .onComplete(TestFns.pipeTo(testContext))
            .get();


    }

    @Test
    public void _15_is_not_legal_age(final VertxTestContext context) {
        UserAccountModule.isLegalAge
                .apply(13)
                .onComplete(it -> context.verify(() ->
                                                 {
                                                     Assertions.assertFalse(it.result());
                                                     context.completeNow();
                                                 }
                                                )
                           )
                .get()
        ;
    }


    @Test
    public void _17_is_not_legal_age(final VertxTestContext context) {
        UserAccountModule.isLegalAge
                .apply(17)
                .onComplete(it -> context.verify(() ->
                                                 {
                                                     Assertions.assertTrue(it.result());
                                                     context.completeNow();
                                                 }
                                                )
                           )
                .get();


    }


    @Test
    public void _user_is_valid(final VertxTestContext context) {
        UserAccountModule.isValid
                .apply(JsObj.of("email",
                                JsStr.of("imrafaelmerino@gmail.com"),
                                "age",
                                JsInt.of(17),
                                "address",
                                JsObj.of("city",
                                         JsStr.of("Madrid")
                                        ),
                                "id",
                                JsStr.of("03886961F")
                               )
                      )
                .onComplete(it -> context.verify(() ->
                                                 {
                                                     Assertions.assertTrue(it.succeeded());
                                                     Assertions.assertTrue(it.result());
                                                     context.completeNow();
                                                 }
                                                )
                           )
                .get();


    }
}
