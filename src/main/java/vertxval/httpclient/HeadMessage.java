package vertxval.httpclient;



public class HeadMessage extends ReqMessage<HeadMessage> {
    public HeadMessage() {
        this.type = TYPE.HEAD;
    }
}