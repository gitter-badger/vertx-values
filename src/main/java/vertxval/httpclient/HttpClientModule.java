package vertxval.httpclient;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.*;
import jsonvalues.JsObj;
import vertxval.Handlers;
import vertxval.Module;
import vertxval.exp.Exp;
import vertxval.exp.λ;

import java.util.function.Consumer;
import java.util.function.Function;

import static vertxval.httpclient.Req.BODY_LENS;


public abstract class HttpClientModule extends Module {

    private final HttpClientOptions httpOptions;

    private λ<JsObj, JsObj> httpClient;

    public Function<GetBuilder, Exp<JsObj>> get = builder -> httpClient.apply(builder.createHttpReq());

    public Function<PostBuilder, Exp<JsObj>> post = builder -> httpClient.apply(builder.createHttpReq());

    public Function<PutBuilder, Exp<JsObj>> put = builder -> httpClient.apply(builder.createHttpReq());

    public Function<DeleteBuilder, Exp<JsObj>> delete = builder -> httpClient.apply(builder.createHttpReq());

    public Function<HeadBuilder, Exp<JsObj>> head = builder -> httpClient.apply(builder.createHttpReq());

    public Function<OptionsBuilder, Exp<JsObj>> options = builder -> httpClient.apply(builder.createHttpReq());

    public Function<PatchBuilder, Exp<JsObj>> patch = builder -> httpClient.apply(builder.createHttpReq());

    public Function<TraceBuilder, Exp<JsObj>> trace = builder -> httpClient.apply(builder.createHttpReq());

    public Function<ConnectBuilder, Exp<JsObj>> connect = builder -> httpClient.apply(builder.createHttpReq());

    private static Consumer<Message<JsObj>> consumer(final HttpClient client) {
        return message -> {
            JsObj          body    = message.body();
            Integer        type    = Req.TYPE_LENS.get.apply(body);
            RequestOptions options = Req.toReqOptions.apply(body);
            switch (type) {
                case 0:
                    client.get(options,
                               getHandler(message)
                              );
                    break;
                case 1:
                    client.post(options,
                                Buffer.buffer(BODY_LENS.get.apply(body)),
                                getHandler(message)
                               );
                    break;
                case 2:
                    client.put(options,
                               Buffer.buffer(BODY_LENS.get.apply(body)),
                               getHandler(message)
                              );
                    break;
                case 3:
                    client.delete(options,
                                  getHandler(message)
                                 );
                    break;
                case 4:
                    client.options(options,
                                   getHandler(message)
                                  );
                    break;
                case 5:
                    client.head(options,
                                getHandler(message)
                               );
                    break;
                case 6:
                    client.send(options.setMethod(HttpMethod.TRACE),
                                getHandler(message)
                               );
                    break;
                case 7:
                    client.send(options.setMethod(HttpMethod.PATCH),
                                Buffer.buffer(BODY_LENS.get.apply(body)),
                                getHandler(message)
                               );
                    break;
                case 8:
                    client.send(options.setMethod(HttpMethod.CONNECT),
                                getHandler(message)
                               );
                    break;
                default:
                    message.reply(HttpExceptions.HTTP_METHOD_NOT_SUPPORTED.apply(type));
            }
        };
    }


    private static Handler<AsyncResult<HttpClientResponse>> getHandler(final Message<JsObj> m) {
        return r -> {
            if (r.succeeded()) {
                HttpClientResponse resp = r.result();
                resp.body()
                    .onComplete(
                            Handlers.pipeTo(m,
                                            buffer -> Resp.toJsObj.apply(buffer,
                                                                         resp
                                                                        ),
                                            cause -> HttpExceptions.EXCEPTION_RESPONSE.apply(cause)
                                           )

                               );

            }
            else {
                m.reply(HttpExceptions.EXCEPTION_RESPONSE.apply(r.cause()));
            }

        };
    }

    public HttpClientModule(final HttpClientOptions options) {
        this.httpOptions = options;
    }

    @Override
    protected void onComplete() {
        this.httpClient = this.<JsObj, JsObj>getDeployedVerticle("httpclient").ask();
        defineHttpActors();
    }

    protected abstract void defineHttpActors();


    @Override
    protected void deploy() {
        this.deployConsumer("httpclient",
                            consumer(vertx.createHttpClient(httpOptions))
                           );
    }
}
