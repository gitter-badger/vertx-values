package actors.httpclient;



public class DeleteBuilder extends ReqBuilder<DeleteBuilder> {
    public DeleteBuilder() {
        this.type = TYPE.DELETE;
    }

}