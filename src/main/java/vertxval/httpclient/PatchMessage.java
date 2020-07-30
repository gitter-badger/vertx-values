package vertxval.httpclient;


public class PatchMessage extends BodyReqMessage<PatchMessage> {
    public PatchMessage(final byte[] body) {
        super(body);
        this.type = TYPE.PATCH;
    }







}