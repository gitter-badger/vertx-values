package actors.httpclient;

import io.vertx.core.http.RequestOptions;
import jsonvalues.JsObj;
import jsonvalues.Lens;
import jsonvalues.Option;
import jsonvalues.spec.JsObjSpec;

import java.util.Optional;
import java.util.function.Function;

import static jsonvalues.spec.JsSpecs.*;

public abstract class Req {

    private static final String TYPE_FIELD = "type";
    private static final String HEADERS_FIELD = "headers";
    private static final String TIMEOUT_FIELD = "timeout";
    private static final String FOLLOW_REDIRECTS_FIELD = "followRedirects";
    private static final String PORT_FIELD = "port";
    private static final String HOST_FIELD = "host";
    private static final String URI_FIELD = "uri";
    private static final String BODY_FIELD = "body";
    private static final String SSL_FIELD = "ssl";

    public static final Lens<JsObj, Integer> TYPE_LENS = JsObj.lens.intNum(TYPE_FIELD);
    public static final Option<JsObj, JsObj> HEADERS_OPT = JsObj.optional.obj(HEADERS_FIELD);
    public static final Option<JsObj, Long> TIMEOUT_OPT = JsObj.optional.longNum(TIMEOUT_FIELD);
    public static final Option<JsObj, Boolean> SSL_OPT = JsObj.optional.bool(SSL_FIELD);
    public static final Option<JsObj, Boolean> FOLLOW_REDIRECT_OPT = JsObj.optional.bool(FOLLOW_REDIRECTS_FIELD);
    public static final Option<JsObj, Integer> PORT_OPT = JsObj.optional.intNum(PORT_FIELD);
    public static final Option<JsObj, String> HOST_OPT = JsObj.optional.str(HOST_FIELD);
    public static final Option<JsObj, String> URI_OPT = JsObj.optional.str(URI_FIELD);
    public static final Lens<JsObj, byte[]> BODY_LENS = JsObj.lens.binary(BODY_FIELD);


    public static final JsObjSpec reqSpec =
            JsObjSpec.strict(TYPE_FIELD,
                             integer,
                             HEADERS_FIELD,
                             obj.optional(),
                             TIMEOUT_FIELD,
                             integer.optional(),
                             SSL_FIELD,
                             bool.optional(),
                             FOLLOW_REDIRECTS_FIELD,
                             bool.optional(),
                             PORT_FIELD,
                             integer.optional(),
                             HOST_FIELD,
                             str.optional(),
                             URI_FIELD,
                             str.optional()
                            );

    public static final JsObjSpec reqBodySpec = reqSpec.set(BODY_FIELD,
                                                            binary);


     static Function<JsObj, RequestOptions> toReqOptions =
            body -> {
                RequestOptions   options = new RequestOptions();
                Optional<String> host    = HOST_OPT.get.apply(body);
                host.ifPresent(options::setHost);

                Optional<Integer> port = PORT_OPT.get.apply(body);
                port.ifPresent(options::setPort);

                Optional<Boolean> followRedirect = FOLLOW_REDIRECT_OPT.get.apply(body);
                followRedirect.ifPresent(options::setFollowRedirects);

                Optional<Boolean> ssl = SSL_OPT.get.apply(body);
                ssl.ifPresent(options::setSsl);

                Optional<JsObj> headers = HEADERS_OPT.get.apply(body);
                headers.ifPresent(it -> it.keySet()
                                          .forEach(key -> it.getArray(key)
                                                            .forEach(value -> options.addHeader(key,
                                                                                                value.toJsStr().value
                                                                                               )))
                                 );
                Optional<Long> timeout = TIMEOUT_OPT.get.apply(body);
                timeout.ifPresent(options::setTimeout);

                Optional<String> uri = URI_OPT.get.apply(body);
                uri.ifPresent(options::setURI);

                return options;
            };
}


