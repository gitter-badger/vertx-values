package actors.httpclient;

import actors.Actors;
import actors.TestFns;
import actors.codecs.RegisterJsValuesCodecs;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.stream.Collectors;


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

        CompositeFuture.all(
                Arrays.asList(actors.deploy(new RegisterJsValuesCodecs()),
                              actors.deploy(httpModule)
                             )
                           )
                       .onComplete(TestFns.pipeTo(testContext));

    }

    @Test
    public void testSearchGoogle(VertxTestContext context) {
        Future<JsObj> search1 = httpModule.search.apply("vertx");
        Future<JsObj> search2 = httpModule.search.apply("reactive");

        CompositeFuture.all(search1,
                            search2)
                       .onComplete(
                               TestFns.pipeTo(list -> System.out.println(list.stream()
                                                                               .map(o -> ((JsObj) o).getInt("code"))
                                                                               .collect(Collectors.toList())
                                                                        ),
                                              context
                                             )
                                  );

    }
}
