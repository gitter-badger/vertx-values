package vertxval.httpclient;


public class PatchBuilder extends BodyReqBuilder<PatchBuilder> {
    public PatchBuilder(final byte[] body) {
        super(body);
        this.type = TYPE.PATCH;
    }







}