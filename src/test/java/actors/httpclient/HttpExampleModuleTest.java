package actors.httpclient;

import actors.Actors;
import actors.codecs.RegisterJsValuesCodecs;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ExtendWith(VertxExtension.class)
public class HttpExampleModuleTest  {

    static Actors actors;
    static HttpExampleModule httpModule;
    @BeforeAll
    public static void prepare(Vertx vertx,
                               VertxTestContext testContext
                              )
    {
        actors = new Actors(vertx);

        httpModule = new HttpExampleModule(new HttpClientOptions());

        CompositeFuture.all(
                Arrays.asList(actors.deploy(new RegisterJsValuesCodecs()),
                              actors.deploy(httpModule)
                             )
                           ).onComplete(it -> {
                               if(it.succeeded())testContext.completeNow();
                               else testContext.failNow(it.cause());
                           }
                               );

    }

    @Test
    public void testSearchGoogle( VertxTestContext context){
        Future<JsObj> vertx = httpModule.search.get()
                                               .apply("vertx");

        vertx.onComplete(it -> {
           if(it.succeeded()){
               System.out.println(it.result());
               context.completeNow();
           }
           else {
               it.cause().printStackTrace();
               context.failNow(it.cause());
           }
        });
    }
}
