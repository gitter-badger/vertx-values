package vertxval.httpclient;

import vertxval.Deployer;
import vertxval.TestFns;
import vertxval.codecs.RegisterJsValuesCodecs;
import vertxval.exp.Exp;
import vertxval.exp.Pair;
import io.vavr.Tuple2;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(VertxExtension.class)
public class HttpExampleModuleTest {

    static Deployer deployer;
    static HttpExampleModule httpModule;

    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              ) {
        deployer = new Deployer(vertx);

        httpModule = new HttpExampleModule(new HttpClientOptions());

        Pair.of(deployer.deployVerticle(new RegisterJsValuesCodecs()),
                deployer.deployVerticle(httpModule)
               )
            .onComplete(TestFns.pipeTo(testContext)).get();


    }

    //@Test
    public void testSearchGoogle(VertxTestContext context) {
        Exp<JsObj> search1 = httpModule.search.apply("vertx");
        Exp<JsObj> search2 = httpModule.search.apply("reactive");
        Pair.of(search1,
                search2
               )
            .map(pair -> pair.map((r1,r2) -> new Tuple2<>(r1.getInt("code"),
                                                          r2.getInt("code"))
                                 )
                )
            .onComplete(TestFns.pipeTo(context)).get();

    }
}
