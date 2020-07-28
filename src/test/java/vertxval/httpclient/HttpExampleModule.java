package vertxval.httpclient;

import vertxval.exp.λ;
import io.vertx.core.http.HttpClientOptions;
import jsonvalues.JsObj;

public class HttpExampleModule extends HttpClientModule {

    public λ<String,JsObj> search;

    public HttpExampleModule(final HttpClientOptions options) {
        super(options);
    }

    @Override
    protected void defineHttpActors() {
        λ<String,JsObj> search =
                term -> get.apply(new GetBuilder().host("www.google.com")
                                                  .uri("/search?q=" + term));

        this.search = deployer.spawnλ(search);

    }


}
