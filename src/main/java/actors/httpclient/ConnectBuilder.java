package actors.httpclient;



public class ConnectBuilder extends ReqBuilder<ConnectBuilder> {
    public ConnectBuilder() {
        this.type = TYPE.CONNECT;
    }

}