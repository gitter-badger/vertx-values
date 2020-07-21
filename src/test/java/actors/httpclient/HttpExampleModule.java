package actors.httpclient;

import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpClientOptions;
import jsonvalues.JsObj;
import jsonvalues.JsStr;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class HttpExampleModule extends HttpClientModule {

    public Supplier<Function<String, Future<JsObj>>> search;

    public HttpExampleModule(final HttpClientOptions options) {
        super(options);

    }

    @Override
    protected void defineHttpActors() {
        Function<String, Future<JsObj>> search =
                term -> get.apply(new GetBuilder().host("google.com")
                                                  .uri("q=" + term));
        Consumer<Message<String>> consumer =
                m -> search.apply(m.body()).onComplete(result -> {
                   if(result.succeeded()){
                       JsObj apply = Resp.mapBody.apply(bytes -> JsStr.of(new String(bytes)))
                                                 .apply(result.result());
                       m.reply(apply);
                   }
                   else m.reply(result.cause());
                });
        this.search = actors.spawn(consumer);
    }


}
