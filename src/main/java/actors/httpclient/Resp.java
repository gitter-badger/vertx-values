package actors.httpclient;

import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import jsonvalues.*;
import jsonvalues.spec.JsObjSpec;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static jsonvalues.spec.JsSpecs.*;

final public class Resp {

    private static final String CODE_FIELD = "code";
    private static final String MESSAGE_FIELD = "message";
    private static final String COOKIES_FIELD = "cookies";
    private static final String HEADERS_FIELD = "headers";
    private static final String BODY_FIELD = "body";

    public static final Lens<JsObj, Integer> CODE_LENS = JsObj.lens.intNum(CODE_FIELD);
    public static final Option<JsObj, String> MESSAGE_OPT = JsObj.optional.str(MESSAGE_FIELD);
    public static final Lens<JsObj, JsArray> COOKIES_LENS = JsObj.lens.array(COOKIES_FIELD);
    public static final Lens<JsObj, JsObj> HEADERS_OPT = JsObj.lens.obj(HEADERS_FIELD);
    public static final Lens<JsObj, String> BODY_LENS = JsObj.lens.str(BODY_FIELD);


    public static final JsObjSpec spec = JsObjSpec.strict(CODE_FIELD,
                                                          integer,
                                                          MESSAGE_FIELD,
                                                          str.optional(),
                                                          COOKIES_FIELD,
                                                          arrayOfStr,
                                                          HEADERS_FIELD,
                                                          obj,
                                                          BODY_FIELD,
                                                          str.optional()
                                                         );


    public static final Function<Function<String, JsValue>, Function<JsObj, JsObj>> mapBody =
            fn -> resp -> resp.set(BODY_FIELD,
                                   fn.apply(resp.getStr(BODY_FIELD))
                                  );



    public static final Function<JsObj, JsObj> mapBody2Json =
            mapBody.apply(bytes -> JsObj.parse(new String(bytes)));

    static BiFunction<Buffer, HttpClientResponse, JsObj> toJsObj =
            (buffer, httpResp) ->
                    CODE_LENS.set.apply(httpResp.statusCode())
                                               .andThen(MESSAGE_OPT.set.apply(httpResp.statusMessage()))
                                               .andThen(COOKIES_LENS.set.apply(cookies2JsArray(httpResp.cookies())))
                                               .andThen(HEADERS_OPT.set.apply(headers2JsObj(httpResp.headers())))
                                               .andThen(BODY_LENS.set.apply(new String(buffer.getBytes())))
                                               .apply(JsObj.empty());


    public static JsObj headers2JsObj(final MultiMap headers) {
        JsObj result = JsObj.empty();
        if (headers == null || headers.isEmpty()) return JsObj.empty();
        for (final Map.Entry<String, String> header : headers) {
            result = addHeader(header,
                               result
                              );
        }
        return result;
    }

    private static JsObj addHeader(final Map.Entry<String, String> header,
                                   final JsObj result) {
        if (!result.containsKey(header.getKey()))
            return result.set(header.getKey(),
                              JsArray.of(header.getValue())
                             );
        else {
            JsArray headerValues = result.getArray(header.getKey())
                                         .append(JsStr.of(header.getValue()));
            return result.set(header.getKey(),
                              headerValues);
        }
    }

    public static JsArray cookies2JsArray(final List<String> cookies) {
        if (cookies == null || cookies.isEmpty()) return JsArray.empty();
        return JsArray.ofIterable(cookies.stream()
                                         .map(JsStr::of)
                                         .collect(Collectors.toList()));
    }

}
