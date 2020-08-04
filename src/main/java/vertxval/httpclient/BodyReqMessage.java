package vertxval.httpclient;


import jsonvalues.JsObj;

import static java.util.Objects.requireNonNull;
import static vertxval.httpclient.Req.BODY_LENS;

abstract class BodyReqMessage<T extends BodyReqMessage<T>> extends ReqMessage<T> {
    public BodyReqMessage(final byte[] body) {
        this.body = requireNonNull(body);
    }

    private final byte[] body;


    @Override
    public JsObj createHttpReq() {
        return BODY_LENS.set.apply(body)
                            .apply(super.createHttpReq());
    }
}