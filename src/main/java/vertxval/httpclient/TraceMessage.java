package vertxval.httpclient;



public class TraceMessage extends ReqMessage<TraceMessage> {
    public TraceMessage() {
        this.type = TYPE.TRACE;
    }
}