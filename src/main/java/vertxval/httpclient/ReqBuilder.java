package vertxval.httpclient;

import jsonvalues.JsArray;
import jsonvalues.JsObj;
import jsonvalues.JsStr;
import jsonvalues.JsValue;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

abstract class ReqBuilder<T extends ReqBuilder<T>> {
    protected TYPE type;


    public enum TYPE {
        GET(0), POST(1), PUT(2), DELETE(3), OPTIONS(4), HEAD(5), TRACE(6), PATCH(7), CONNECT(8);
        public final int n;

        TYPE(final int n) {
            this.n = n;
        }
    }

    private JsObj headers = JsObj.empty();
    private Long timeout;
    private Boolean followRedirects;
    private Integer port;
    private String host;
    private String uri;
    private Boolean ssl;


    public T header(String key,
                             String value) {
        JsValue values = headers.get(key);
        if (values.isNothing()) headers = headers.set(key,
                                                      JsArray.of(value)
                                                     );
        else headers = headers.set(key,values.toJsArray().append(JsStr.of(value)));
        return (T) this;
    }


    public T timeout(final int timeout,
                              final TimeUnit unit) {
        if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
        this.timeout = requireNonNull(unit).toMillis(timeout);
        return (T) this;
    }


    public T ssl(final boolean ssl) {
        this.ssl = ssl;
        return (T) this;
    }

    public T followRedirects(final boolean followRedirects) {
        this.followRedirects = followRedirects;
        return (T) this;
    }

    public T port(final int port) {
        if (port < 0) throw new IllegalArgumentException("port < 0");
        this.port = port;
        return (T) this;
    }

    public T host(final String host) {
        if (requireNonNull(host).isEmpty()) throw new IllegalArgumentException("host is empty");
        this.host = host;
        return (T) this;
    }

    public T uri(final String uri) {
        if (requireNonNull(uri).isEmpty()) throw new IllegalArgumentException("uri is empty");
        this.uri = uri;
        return (T) this;
    }

    public JsObj createHttpReq() {
        return Req.TYPE_LENS.set.apply(type.n)
                                .andThen(!headers.isEmpty() ? Req.HEADERS_OPT.set.apply(headers) : Function.identity())
                                .andThen(timeout != null ? Req.TIMEOUT_OPT.set.apply(timeout) : Function.identity())
                                .andThen(followRedirects != null ? Req.FOLLOW_REDIRECT_OPT.set.apply(followRedirects) : Function.identity())
                                .andThen(port != null ? Req.PORT_OPT.set.apply(port) : Function.identity())
                                .andThen(host != null ? Req.HOST_OPT.set.apply(host) : Function.identity())
                                .andThen(ssl != null ? Req.SSL_OPT.set.apply(ssl) : Function.identity())
                                .andThen(uri != null ? Req.URI_OPT.set.apply(uri) : Function.identity())
                                .apply(JsObj.empty());
    }


}