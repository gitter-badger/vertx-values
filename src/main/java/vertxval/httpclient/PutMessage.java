package vertxval.httpclient;


public class PutMessage extends BodyReqMessage<PutMessage> {
    public PutMessage(final byte[] body) {
        super(body);
        this.type = TYPE.PUT;
    }

}