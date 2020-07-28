package vertxval.httpclient;


import jsonvalues.JsObj;

import java.util.Objects;

abstract class BodyReqBuilder<T extends BodyReqBuilder<T>> extends ReqBuilder<T> {
    public BodyReqBuilder(final byte[] body) {
        this.body = Objects.requireNonNull(body);
    }

    private final byte[] body;


    @Override
    public JsObj createHttpReq() {
        return Req.BODY_LENS.set.apply(body).apply(super.createHttpReq());
    }
}