package vertxval.httpclient;


public class PutBuilder extends BodyReqBuilder<PutBuilder> {
    public PutBuilder(final byte[] body) {
        super(body);
        this.type = TYPE.PUT;
    }

}