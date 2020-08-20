package vertxval.httpclient;


import io.vertx.core.CompositeFuture;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import jsonvalues.JsObj;
import jsonvalues.JsPath;
import jsonvalues.JsStr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import vertxval.TestFns;
import vertxval.codecs.RegisterJsValuesCodecs;

@ExtendWith(VertxExtension.class)
public class HttpClientMethodsTests {

    private static final int PORT = 1234;
    static HttpClientModule httpClient;

    @BeforeAll
    public static void prepare(final Vertx vertx,
                               final VertxTestContext testContext
                              ) {

        httpClient = new HttpExampleModule(new HttpClientOptions());

        CompositeFuture.all(vertx.deployVerticle(new RegisterJsValuesCodecs()),
                            vertx.createHttpServer(new HttpServerOptions())
                                 .requestHandler(req ->
                                                         req.bodyHandler(body -> createResponse(req,
                                                                                                body.toString()
                                                                                               )
                                                                        )
                                                )
                                 .listen(PORT),
                            vertx.deployVerticle(httpClient)
                           )
                       .onComplete(TestFns.pipeTo(testContext));


    }

    private static void createResponse(final HttpServerRequest req,
                                       final String body) {


        HttpServerResponse resp = req.response();
        JsObj objResp = JsObj.of("req_method",
                                 JsStr.of(req.method()
                                             .name()),
                                 "req_body",
                                 JsStr.of(body),
                                 "req_uri",
                                 JsStr.of(req.uri()),
                                 "req_headers",
                                 Resp.headers2JsObj(req.headers())
                                );
        resp.setStatusCode(200)
            .end(objResp.toString());


    }


    @Test
    public void testGet(VertxTestContext testContext) {
        httpClient.get.apply(new GetMessage().port(PORT)
                                             .uri("example")
                            )
                      .onComplete(TestFns.pipeTo(System.out::println,
                                                 testContext
                                                )
                                 )
                      .get();
    }

    @Test
    public void testPost(VertxTestContext testContext) {
        httpClient.post.apply(new PostMessage("hi".getBytes())
                                      .port(PORT)
                                      .uri("example"))
                       .onComplete(it -> {
                           System.out.println(it.result());
                           testContext.completeNow();
                       })
                       .get();
    }

    @Test
    public void testPut(VertxTestContext testContext) {
        httpClient.put.apply(new PutMessage("hi".getBytes())
                                     .port(PORT)
                                     .uri("example"))
                      .onComplete(it -> {
                          System.out.println(it.result());

                          testContext.completeNow();
                      })
                      .get();
    }

    @Test
    public void testPatch(VertxTestContext testContext) {
        httpClient.patch.apply(new PatchMessage("hi".getBytes())
                                       .port(PORT)
                                       .uri("example"))
                        .onComplete(it -> {
                            System.out.println(it.result());

                            testContext.completeNow();
                        })
                        .get();
    }

    @Test
    public void testDelete(VertxTestContext testContext) {
        httpClient.delete.apply(new DeleteMessage().port(PORT)
                                                   .uri("example")
                               ).map(Resp.mapBody2Json)
                         .onComplete(TestFns.pipeTo(it -> testContext.verify(() -> {
                                                        System.out.println(it);
                                                        Assertions.assertEquals("DELETE",
                                                                                it.getStr(JsPath.path("/body/req_method")));
                                                    }),
                                                    testContext
                                                   )
                                    )
                         .get();
    }
}
