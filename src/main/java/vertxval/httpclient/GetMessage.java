package vertxval.httpclient;



public class GetMessage extends ReqMessage<GetMessage> {
    public GetMessage() {
        this.type = TYPE.GET;
    }

}