package vertxval.httpclient;


import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.RequestOptions;
import jsonvalues.JsObj;
import vertxval.VerticleRef;
import vertxval.VertxModule;
import vertxval.exp.λ;
import vertxval.functions.Handlers;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import static io.vertx.core.http.HttpMethod.*;
import static java.util.Objects.requireNonNull;
import static vertxval.VertxValException.*;
import static vertxval.httpclient.Req.BODY_LENS;

/**
 Module that exposes a set of functions to send different requests to a server.
 It's created from a {@link HttpClientOptions} instance.
 It's just another verticle that needs to be deployed. You can create as many as you want,
 with different configurations:
 <pre>
 {@code
      HttpClientOptions server1Options = new HttpClientOptions();
      HttpClientOptions server2Options = new HttpClientOptions();

      HttpClientModule  httpServer1Module = new HttpClientModule(server1Options);
      HttpClientModule  httpServer2Module = new HttpClientModule(server2Options);

      vertx.deployVerticle(httpServer1Module);
      vertx.deployVerticle(httpServer2Module);
 }
 </pre>
 Once deployed, you can use the defined functions {@link HttpClientModule#get get}, {@link HttpClientModule#post post},
 {@link HttpClientModule#put put}, {@link HttpClientModule#delete delete} and so on. You can create new verticles and
 expose them as functions of this module implementing the methods {@link HttpClientModule#deployRequests()} and {@link HttpClientModule#defineRequests()}
 @see HttpClientModule#deployRequests()
 @see HttpClientModule#defineRequests()
 */
public abstract class HttpClientModule extends VertxModule {

    private static final AtomicInteger modulesCounter = new AtomicInteger(1);

    public HttpClientModule(final HttpClientOptions options) {
        this.httpClientAddress = HTTPCLIENT_ADDRESS + modulesCounter.getAndIncrement();
        this.httpOptions = requireNonNull(options);
    }

    private final HttpClientOptions httpOptions;

    private final String httpClientAddress;

    private final static String HTTPCLIENT_ADDRESS = "vertxval.httpclient.";

    private λ<JsObj, JsObj> httpClient;

    /**
     represents a GET request. It takes as input a {@link GetMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<GetMessage, JsObj> get = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a POST request. It takes as input a {@link PostMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<PostMessage, JsObj> post = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a PUT request. It takes as input a {@link PutMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<PutMessage, JsObj> put = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a DELETE request. It takes as input a {@link DeleteMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<DeleteMessage, JsObj> delete = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a HEAD request. It takes as input a {@link HeadMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<HeadMessage, JsObj> head = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a OPTIONS request. It takes as input a {@link OptionsMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<OptionsMessage, JsObj> options = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a PATCH request. It takes as input a {@link PatchMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<PatchMessage, JsObj> patch = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a TRACE request. It takes as input a {@link TraceMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
    public λ<TraceMessage, JsObj> trace = builder -> httpClient.apply(builder.createHttpReq());

    /**
     represents a CONNECT request. It takes as input a {@link ConnectMessage} instance and returns a response in a JsObj.
     The class {@link Resp} contains all the lenses and functions to get info from the response
     and manipulate it
     */
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
                    message.reply(GET_HTTP_METHOD_NOT_IMPLEMENTED_EXCEPTION.apply(type));
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

    @Override
    protected void define() {
        VerticleRef<JsObj, JsObj> verticleRef = this.getDeployedVerticle(httpClientAddress);
        if (verticleRef == null)
            throw GET_DEPLOYING_MODULE_EXCEPTION.apply(new NullPointerException("httpclient is null"));
        this.httpClient = verticleRef.ask();
        defineRequests();
    }

    /**
     method to initialize from the deployed verticles in {@link HttpClientModule#deployRequests()} the functions that will
     be exposed by this module. You can create new functions spawning verticles as well with the method
     {@link vertxval.Deployer#spawnλ(λ)} like in the following example:
     <pre>
     {@code

     public λ<JsObj, JsObj> post_client;
     public λ<JsObj, JsObj> get_client;
     public λ<JsObj, JsObj> search;

     protected void defineRequests(){

         // "create_client" was deployed in deployRequests method
         this.post_client = this.<JsObj, JsObj>getDeployedVerticle("create_client").ask();

        // "get_client" was deployed in deployRequests method
        this.get_client = this.<String, JsObj>getDeployedVerticle("get_client").ask();

        // define a λ that will spawn a new verticle every call
        this.search = deployer.spawnλ(term -> get.apply(new GetMessage().host("www.google.com")
                                                                        .uri("/search?q=" + term)
                                                       )
                                     );
     }

     }
     </pre>

     @see HttpClientModule#deployRequests()
     */
    protected abstract void defineRequests();


    @Override
    protected void deploy() {
        this.deployConsumer(httpClientAddress,
                            consumer(vertx.createHttpClient(httpOptions))
                           );
        deployRequests();
    }

    /**
     method to deploy new verticles. You can just use the provided functions get, post, put etc exposed by
     the module and leave this method implementation empty. On the other hand you may be interested in creating
     new ones out of them. For example, to deploy two verticles listening on the addresses post_client and search:
     <pre>{@code
     protected void deployRequests(){

          λ<JsObj, JsObj> post_client = body -> post.apply(new PostMessage(body.serialize()));

          this.deployFn("post_client",
                         post_customer
                       );

          λ<String, JsObj> get_client = id -> get.apply(new GetMessage().uri("/" + id));

          this.deployFn("get_client",
                         get_client
                        );
     }
     }
     </pre>

     @see HttpClientModule#defineRequests()
     */
    protected abstract void deployRequests();
}
