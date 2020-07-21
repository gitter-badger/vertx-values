package actors.httpclient;



public class HeadBuilder extends ReqBuilder<HeadBuilder> {
    public HeadBuilder() {
        this.type = TYPE.HEAD;
    }
}