package vertxval.httpclient;
import static java.util.Objects.requireNonNull;

public class PostMessage extends BodyReqMessage<PostMessage> {
    public PostMessage(final byte[] body) {
        super(requireNonNull(body));
        this.type = TYPE.POST;
    }







}