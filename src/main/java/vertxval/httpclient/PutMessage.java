package vertxval.httpclient;

import static java.util.Objects.requireNonNull;

public class PutMessage extends BodyReqMessage<PutMessage> {
    public PutMessage(final byte[] body) {
        super(requireNonNull(body));
        this.type = TYPE.PUT;
    }

}