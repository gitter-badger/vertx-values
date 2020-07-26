package actors.httpclient;

import actors.Actors;
import actors.TestFns;
import actors.codecs.RegisterJsValuesCodecs;
import actors.exp.Exp;
import actors.exp.Pair;
import io.vavr.Tuple2;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(VertxExtension.class)
public class HttpExampleModuleTest {

    static Actors actors;
    static HttpExampleModule httpModule;

    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              ) {
        actors = new Actors(vertx);

        httpModule = new HttpExampleModule(new HttpClientOptions());

        Pair.of(actors.register(new RegisterJsValuesCodecs()),
                actors.register(httpModule)
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
