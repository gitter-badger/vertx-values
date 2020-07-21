package actors.httpclient;


import actors.ActorRef;
import actors.ActorsModule;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.*;
import jsonvalues.JsObj;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static actors.httpclient.HttpExceptions.EXCEPTION_READING_BODY_RESPONSE;
import static actors.httpclient.HttpExceptions.EXCEPTION_RESPONSE;
import static actors.httpclient.Req.BODY_LENS;


public abstract class HttpClientModule extends ActorsModule {

    private final HttpClientOptions httpOptions;

    private Function<JsObj, Future<JsObj>> httpClient;

    public Function<GetBuilder, Future<JsObj>> get = builder -> httpClient.apply(builder.createHttpReq());

    public Function<PostBuilder, Future<JsObj>> post = builder -> httpClient.apply(builder.createHttpReq());

    public Function<PutBuilder, Future<JsObj>> put = builder -> httpClient.apply(builder.createHttpReq());

    public Function<DeleteBuilder, Future<JsObj>> delete = builder -> httpClient.apply(builder.createHttpReq());

    public Function<HeadBuilder, Future<JsObj>> head = builder -> httpClient.apply(builder.createHttpReq());

    public Function<OptionsBuilder, Future<JsObj>> options = builder -> httpClient.apply(builder.createHttpReq());

    public Function<PatchBuilder, Future<JsObj>> patch = builder -> httpClient.apply(builder.createHttpReq());

    public Function<TraceBuilder, Future<JsObj>> trace = builder -> httpClient.apply(builder.createHttpReq());

    public Function<ConnectBuilder, Future<JsObj>> connect = builder -> httpClient.apply(builder.createHttpReq());

    private static Consumer<Message<JsObj>> consumer(final HttpClient client) {
        return message -> {
            JsObj body = message.body();
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
                            it -> {
                                if (it.succeeded()) {
                                    JsObj apply = Resp.toJsObj.apply(it.result(),resp);
                                    m.reply(apply);
                                }
                                else m.reply(EXCEPTION_READING_BODY_RESPONSE.apply(it.cause()));
                            }
                               );

            }
            else m.reply(EXCEPTION_RESPONSE.apply(r.cause()));

        };
    }

    public HttpClientModule(final HttpClientOptions options) {
        this.httpOptions = options;
    }

    @Override
    protected void defineActors(final List<Object> futures) {
        this.httpClient = req -> ((ActorRef<JsObj, JsObj>) futures.get(0)).ask()
                                                                          .apply(req);
        defineHttpActors();
    }

    protected abstract void defineHttpActors();

    @Override
    protected List<Future> deployActors() {
        return Arrays.asList(actors.deploy(consumer(vertx.createHttpClient(httpOptions))));

    }
}
