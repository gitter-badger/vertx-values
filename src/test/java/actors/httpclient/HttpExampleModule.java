package actors.httpclient;

import actors.Fn;
import actors.Handlers;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClientOptions;
import jsonvalues.JsObj;
import java.util.function.Consumer;

public class HttpExampleModule extends HttpClientModule {

    public Fn<String,JsObj> search;

    public HttpExampleModule(final HttpClientOptions options) {
        super(options);
    }

    @Override
    protected void defineHttpActors() {
        Fn<String,JsObj> search =
                term -> get.apply(new GetBuilder().host("www.google.com")
                                                  .uri("/search?q=" + term));
        Consumer<Message<String>> consumer =
                message -> search.apply(message.body())
                                 .onComplete(Handlers.pipeTo(message)).get();
        this.search = actors.spawn(consumer);

    }


}
