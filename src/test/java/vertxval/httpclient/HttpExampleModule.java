package vertxval.httpclient;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.http.HttpClientOptions;
import jsonvalues.JsObj;
import vertxval.exp.λ;

public class HttpExampleModule extends HttpClientModule {

    public λ<String, JsObj> search;
    private λ<JsObj, JsObj> create_client;

    public HttpExampleModule(final HttpClientOptions options) {
        super(options);
    }

    @Override
    protected void defineRequests() {
        λ<String, JsObj> search =
                term -> get.apply(new GetMessage().host("www.google.com")
                                                  .uri("/search?q=" + term)
                                 );

        this.search = deployer.spawnλ(search);

        this.create_client = this.<JsObj, JsObj>getDeployedVerticle("create_client").ask();

    }

    @Override
    protected void deployRequests() {
        λ<JsObj, JsObj> post_customer = body -> post.apply(new PostMessage(body.serialize()));
        this.deployFn("create_client",
                      post_customer,
                      new DeploymentOptions().setInstances(4)
                     );
    }


}
