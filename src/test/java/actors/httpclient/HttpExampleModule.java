package actors.httpclient;

import actors.Actor;
import actors.Handlers;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClientOptions;
import jsonvalues.JsObj;
import java.util.function.Consumer;
import java.util.function.Function;

public class HttpExampleModule extends HttpClientModule {

    public Actor<String,JsObj> search;

    public HttpExampleModule(final HttpClientOptions options) {
        super(options);
    }

    @Override
    protected void defineHttpActors() {
        Actor<String,JsObj> search =
                term -> get.apply(new GetBuilder().host("www.google.com")
                                                  .uri("/search?q=" + term));
        Consumer<Message<String>> consumer =
                message -> search.apply(message.body())
                                 .onComplete(Handlers.pipeTo(message));
        this.search = actors.spawn(consumer);

    }


}
