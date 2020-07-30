package vertxval.httpclient;


public class PostMessage extends BodyReqMessage<PostMessage> {
    public PostMessage(final byte[] body) {
        super(body);
        this.type = TYPE.POST;
    }







}