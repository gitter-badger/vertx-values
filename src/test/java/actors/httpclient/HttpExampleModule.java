package actors.httpclient;

import actors.Handlers;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClientOptions;
import jsonvalues.JsObj;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class HttpExampleModule extends HttpClientModule {

    public Function<String, Future<JsObj>> search;

    public HttpExampleModule(final HttpClientOptions options) {
        super(options);
    }

    @Override
    protected void defineHttpActors() {
        Function<String, Future<JsObj>> search =
                term -> get.apply(new GetBuilder().host("www.google.com")
                                                  .uri("/search?q=" + term));
        Consumer<Message<String>> consumer =
                message -> search.apply(message.body())
                                 .onComplete(Handlers.pipeTo(message,Resp.mapBody2Str));
       //TODO que el spaw retorne una functión no? porque un supplier de una function...
        this.search = term -> {
            Supplier<Function<String, Future<JsObj>>> spawn = actors.spawn(consumer);
            return spawn.get().apply(term);
        };
    }


}
