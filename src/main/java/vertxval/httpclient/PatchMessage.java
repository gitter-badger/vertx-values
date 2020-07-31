package vertxval.httpclient;


import java.util.Objects;

public class PatchMessage extends BodyReqMessage<PatchMessage> {
    public PatchMessage(final byte[] body) {
        super(Objects.requireNonNull(body));
        this.type = TYPE.PATCH;
    }







}