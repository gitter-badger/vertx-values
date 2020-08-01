package vertxval.httpclient;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.*;
import jsonvalues.JsObj;
import vertxval.VerticleRef;
import vertxval.functions.Handlers;
import vertxval.VertxModule;
import vertxval.exp.λ;

import java.util.function.Consumer;

import static io.vertx.core.http.HttpMethod.*;
import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.*;
import static vertxval.httpclient.HttpException.GET_HTTP_METHOD_NOT_SUPPORTED_EXCEPTION;
import static vertxval.httpclient.HttpException.GET_RESPONSE_EXCEPTION;
import static vertxval.httpclient.Req.BODY_LENS;


public abstract class HttpClientModule extends VertxModule {

    private final HttpClientOptions httpOptions;
    private final static String HTTPCLIENT_ADDRESS = "vertxval.httpclient";

    private λ<JsObj, JsObj> httpClient;

    public λ<GetMessage, JsObj> get = builder -> httpClient.apply(builder.createHttpReq());

    public λ<PostMessage, JsObj> post = builder -> httpClient.apply(builder.createHttpReq());

    public λ<PutMessage, JsObj> put = builder -> httpClient.apply(builder.createHttpReq());

    public λ<DeleteMessage, JsObj> delete = builder -> httpClient.apply(builder.createHttpReq());

    public λ<HeadMessage,JsObj> head = builder -> httpClient.apply(builder.createHttpReq());

    public λ<OptionsMessage, JsObj> options = builder -> httpClient.apply(builder.createHttpReq());

    public λ<PatchMessage, JsObj> patch = builder -> httpClient.apply(builder.createHttpReq());

    public λ<TraceMessage, JsObj> trace = builder -> httpClient.apply(builder.createHttpReq());

    public λ<ConnectMessage, JsObj> connect = builder -> httpClient.apply(builder.createHttpReq());

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
                    client.send(options.setMethod(TRACE),
                                getHandler(message)
                               );
                    break;
                case 7:
                    client.send(options.setMethod(PATCH),
                                Buffer.buffer(BODY_LENS.get.apply(body)),
                                getHandler(message)
                               );
                    break;
                case 8:
                    client.send(options.setMethod(CONNECT),
                                getHandler(message)
                               );
                    break;
                default:
                    message.reply(GET_HTTP_METHOD_NOT_SUPPORTED_EXCEPTION.apply(type));
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
                                            cause -> GET_RESPONSE_EXCEPTION.apply(cause)
                                           )

                               );

            }
            else {
                m.reply(GET_RESPONSE_EXCEPTION.apply(r.cause()));
            }

        };
    }

    public HttpClientModule(final HttpClientOptions options) {
        this.httpOptions = requireNonNull(options);
    }

    @Override
    protected void define() {
        VerticleRef<JsObj, JsObj> verticleRef = this.getDeployedVerticle(HTTPCLIENT_ADDRESS);
        if(verticleRef==null)throw GET_DEPLOYING_MODULE_EXCEPTION.apply(new NullPointerException("httpclient is null"));
        this.httpClient = verticleRef.ask();
        defineOperations();
    }

    protected abstract void defineOperations();


    @Override
    protected void deploy() {
        this.deployConsumer(HTTPCLIENT_ADDRESS,
                            consumer(vertx.createHttpClient(httpOptions))
                           );
        deployOperations();
    }

    protected abstract void deployOperations();
}
