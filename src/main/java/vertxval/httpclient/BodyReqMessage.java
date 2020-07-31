package vertxval.httpclient;


import jsonvalues.JsObj;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

abstract class BodyReqMessage<T extends BodyReqMessage<T>> extends ReqMessage<T> {
    public BodyReqMessage(final byte[] body) {
        this.body = requireNonNull(body);
    }

    private final byte[] body;


    @Override
    public JsObj createHttpReq() {
        return Req.BODY_LENS.set.apply(body).apply(super.createHttpReq());
    }
}