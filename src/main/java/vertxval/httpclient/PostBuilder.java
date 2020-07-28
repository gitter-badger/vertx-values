package vertxval.httpclient;


public class PostBuilder extends BodyReqBuilder<PostBuilder> {
    public PostBuilder(final byte[] body) {
        super(body);
        this.type = TYPE.POST;
    }







}