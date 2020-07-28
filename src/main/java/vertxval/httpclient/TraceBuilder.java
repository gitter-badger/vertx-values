package vertxval.httpclient;



public class TraceBuilder extends ReqBuilder<TraceBuilder> {
    public TraceBuilder() {
        this.type = TYPE.TRACE;
    }
}