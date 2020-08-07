package vertxval.httpclient;



public class DeleteMessage extends ReqMessage<DeleteMessage> {
    public DeleteMessage() {
        this.type = TYPE.DELETE;
    }

}