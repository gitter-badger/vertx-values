package actors.httpclient;



public class GetBuilder extends ReqBuilder<GetBuilder> {
    public GetBuilder() {
        this.type = TYPE.GET;
    }

}